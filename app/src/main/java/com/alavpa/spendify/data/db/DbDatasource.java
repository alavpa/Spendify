package com.alavpa.spendify.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.data.db.model.SectorDb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alavpa on 19/02/17.
 */

@Singleton
public class DbDatasource implements Datasource {

    DbOpenHelper dbOpenHelper;

    @Inject
    public DbDatasource(DbOpenHelper dbOpenHelper) {
        this.dbOpenHelper = dbOpenHelper;
    }

    public synchronized List<CategoryDb> getCategories(boolean income) {
        List<CategoryDb> categories;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(CategoryDb.TABLE_NAME,
                null,
                DbUtils.operatorEqual(CategoryDb.COL_INCOME, income) + " AND " +
                        DbUtils.operatorEqual(CategoryDb.COL_DELETED, false),
                null, null, null, null);
        categories = getCategoryList(cursor);
        cursor.close();
        db.close();

        return categories;
    }

    private Cursor getCategories(SQLiteDatabase db, boolean income) {
        return db.query(CategoryDb.TABLE_NAME,
                null,
                DbUtils.operatorEqual(CategoryDb.COL_INCOME, income),
                null, null, null, null);
    }

    private List<CategoryDb> getCategoryList(Cursor cursor) {
        List<CategoryDb> categories = new ArrayList<>();
        while (cursor.moveToNext()) {
            categories.add(new CategoryDb().fromCursor(cursor));
        }
        return categories;
    }

    private Map<Long, CategoryDb> getCategoryMap(Cursor cursor) {
        Map<Long, CategoryDb> categories = new HashMap<>();
        while (cursor.moveToNext()) {
            CategoryDb categoryDb = new CategoryDb().fromCursor(cursor);
            categories.put(categoryDb.getId(), categoryDb);
        }
        return categories;
    }

    @Override
    public synchronized AmountDb insertAmount(AmountDb amountDb) {

        ContentValues contentValues = new AmountDb.Builder()
                .amount(amountDb.getAmount())
                .categoryId(amountDb.getCategoryDb().getId())
                .date(amountDb.getDate())
                .description(amountDb.getDescription())
                .income(amountDb.isIncome())
                .period(amountDb.getPeriod())
                .times(amountDb.getTimes())
                .build();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long id = db.insert(AmountDb.TABLE_NAME, null, contentValues);
        db.close();
        amountDb.setId(id);

        return amountDb;
    }

    @Override
    public AmountDb updateAmount(AmountDb amountDb) {
        ContentValues contentValues = new AmountDb.Builder()
                .amount(amountDb.getAmount())
                .categoryId(amountDb.getCategoryDb().getId())
                .date(amountDb.getDate())
                .description(amountDb.getDescription())
                .income(amountDb.isIncome())
                .period(amountDb.getPeriod())
                .times(amountDb.getTimes())
                .deleted(amountDb.isDeleted())
                .build();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        db.update(AmountDb.TABLE_NAME,
                contentValues,
                AmountDb.COL_ID + "=?",
                new String[]{String.valueOf(amountDb.getId())});
        db.close();

        return amountDb;
    }

    @Override
    public synchronized CategoryDb insertCategory(CategoryDb categoryDb) {

        ContentValues contentValues = new CategoryDb.Builder()
                .name(categoryDb.getName())
                .income(categoryDb.isIncome())
                .color(categoryDb.getColor())
                .limit(categoryDb.getLimit())
                .build();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long id = db.insert(CategoryDb.TABLE_NAME, null, contentValues);
        db.close();
        categoryDb.setId(id);

        return categoryDb;
    }

    @Override
    public CategoryDb updateCategory(CategoryDb categoryDb) {
        ContentValues contentValues = new CategoryDb.Builder()
                .name(categoryDb.getName())
                .income(categoryDb.isIncome())
                .color(categoryDb.getColor())
                .limit(categoryDb.getLimit())
                .deleted(categoryDb.isDeleted())
                .build();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();

        db.update(CategoryDb.TABLE_NAME,
                contentValues,
                CategoryDb.COL_ID + "=?",
                new String[]{String.valueOf(categoryDb.getId())});

        db.close();

        return categoryDb;
    }

    @Override
    public synchronized double getSumBy(boolean income, long from, long to) {

        double total = 0;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(" + AmountDb.COL_AMOUNT + ") " +
                        "FROM " + AmountDb.TABLE_NAME + " " +
                        "WHERE " + AmountDb.COL_INCOME + "=? AND " +
                        AmountDb.COL_DATE + ">=? AND " +
                        AmountDb.COL_DATE + "<=? AND " +
                        DbUtils.operatorEqual(CategoryDb.COL_DELETED, false),

                new String[]{DbUtils.getParam(income),
                        DbUtils.getParam(from),
                        DbUtils.getParam(to)},
                null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();

        db.close();

        return total;
    }

    @Override
    public synchronized List<AmountDb> getAmountBy(boolean income, long from, long to) {

        List<AmountDb> list;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.beginTransaction();
        try {
            Cursor cursorCat = getCategories(db, income);
            Map<Long, CategoryDb> categories = getCategoryMap(cursorCat);
            cursorCat.close();

            Cursor cursor = getAmountBy(db, income, from, to);

            list = getAmountList(cursor, categories);

            cursor.close();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        db.close();

        return list;
    }

    private Cursor getAmountBy(SQLiteDatabase db, boolean income, long from, long to) {
        return db.query(AmountDb.TABLE_NAME,
                null,
                AmountDb.COL_DATE + ">=? AND " +
                        AmountDb.COL_DATE + "<=? AND " +
                        AmountDb.COL_INCOME + "=? AND " +
                        DbUtils.operatorEqual(AmountDb.COL_DELETED, false),
                new String[]{DbUtils.getParam(from), DbUtils.getParam(to), DbUtils.getParam(income)},
                AmountDb.COL_CATID,
                null,
                null);
    }

    private List<AmountDb> getAmountList(Cursor cursor, Map<Long, CategoryDb> categoryDbMap) {
        List<AmountDb> amounts = new ArrayList<>();
        while (cursor.moveToNext()) {
            AmountDb amountDb = new AmountDb().fromCursor(cursor);
            amounts.add(amountDb);

            if (categoryDbMap != null) {
                long catId = DbUtils.getLong(cursor, AmountDb.COL_CATID);
                amountDb.setCategoryDb(categoryDbMap.get(catId));
            }
        }
        return amounts;
    }

    private List<SectorDb> getSectorList(Cursor cursor, Map<Long, CategoryDb> categoryDbMap) {
        List<SectorDb> sectors = new ArrayList<>();
        while (cursor.moveToNext()) {
            SectorDb sectorDb = new SectorDb().fromCursor(cursor);
            sectors.add(sectorDb);

            long catId = DbUtils.getLong(cursor, AmountDb.COL_CATID);
            sectorDb.setCategoryDb(categoryDbMap.get(catId));
        }
        return sectors;
    }

    @Override
    public synchronized List<SectorDb> getSectors(boolean income, long from, long to) {

        List<SectorDb> sectorDbs = new ArrayList<>();
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        db.beginTransaction();
        try {
            Cursor cursorCat = getCategories(db, income);

            Map<Long, CategoryDb> categoryDbMap = getCategoryMap(cursorCat);

            Cursor cursor = db.rawQuery("SELECT SUM(" + AmountDb.COL_AMOUNT + ") , " + AmountDb.COL_CATID +
                            " FROM " + AmountDb.TABLE_NAME +
                            " WHERE " + AmountDb.COL_INCOME + "=? AND " +
                            AmountDb.COL_DATE + ">=? AND " +
                            AmountDb.COL_DATE + "<=? AND " +
                            DbUtils.operatorEqual(AmountDb.COL_DELETED, false) +
                            " GROUP BY " + AmountDb.COL_CATID,
                    new String[]{DbUtils.getParam(income), DbUtils.getParam(from), DbUtils.getParam(to)});

            sectorDbs = getSectorList(cursor, categoryDbMap);

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();

        return sectorDbs;

    }

    @Override
    public synchronized SectorDb getSector(long catId, long from, long to) {

        SectorDb sectorDb = null;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        db.beginTransaction();
        try {
            CategoryDb categoryDb = getCategory(db,catId);

            Cursor cursor = db.rawQuery("SELECT SUM(" + AmountDb.COL_AMOUNT + ") , " + AmountDb.COL_CATID +
                            " FROM " + AmountDb.TABLE_NAME +
                            " WHERE " + AmountDb.COL_DATE + ">=? AND " +
                            AmountDb.COL_DATE + "<=? AND " +
                            AmountDb.COL_CATID + "=? AND " +
                            DbUtils.operatorEqual(AmountDb.COL_DELETED, false) +
                            " GROUP BY " + AmountDb.COL_CATID,
                    new String[]{DbUtils.getParam(from), DbUtils.getParam(to),String.valueOf(catId)});

            if(cursor.moveToFirst()) {
                sectorDb = new SectorDb().fromCursor(cursor);
                sectorDb.setCategoryDb(categoryDb);
            }

            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();

        return sectorDb;

    }

    @Override
    public synchronized List<AmountDb> getAmountsByCategoryId(long catId, long from, long to) {
        List<AmountDb> list;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        db.beginTransaction();
        try {

            CategoryDb categoryDb = getCategory(db,catId);
            HashMap<Long,CategoryDb> categories = new HashMap<>();
            categories.put(categoryDb.getId(),categoryDb);

            Cursor cursor = getAmountByCategoryId(db, catId, from, to);
            list = getAmountList(cursor, categories);
            cursor.close();

            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
        db.close();

        return list;
    }

    private CategoryDb getCategory(SQLiteDatabase db, long id){
        CategoryDb categoryDb = null;
        Cursor cursor = db.query(CategoryDb.TABLE_NAME,
                null,
                CategoryDb.COL_ID+"=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null);

        if(cursor.moveToFirst()){
            categoryDb = new CategoryDb().fromCursor(cursor);
        }
        cursor.close();
        return categoryDb;
    }

    @Override
    public synchronized Long getMaxDate() {
        Long date = null;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + AmountDb.COL_DATE + ")" +
                        " FROM " + AmountDb.TABLE_NAME +
                        " WHERE " + DbUtils.operatorEqual(AmountDb.COL_DELETED, false),
                null);
        if (cursor.moveToFirst()) {
            date = cursor.getLong(0);
        }
        cursor.close();
        db.close();

        return date;
    }

    @Override
    public synchronized Long getMinDate() {
        Long date = null;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MIN(" + AmountDb.COL_DATE + ")" +
                        " FROM " + AmountDb.TABLE_NAME +
                        " WHERE " + DbUtils.operatorEqual(AmountDb.COL_DELETED, false),
                null);
        if (cursor.moveToFirst()) {
            date = cursor.getLong(0);
        }
        cursor.close();
        db.close();

        return date;
    }

    @Override
    public double getSumByCategoryId(long catId, long from, long to) {

        double total = 0;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(" + AmountDb.COL_AMOUNT + ") " +
                        "FROM " + AmountDb.TABLE_NAME + " " +
                        "WHERE " + AmountDb.COL_CATID + "=? AND " +
                        AmountDb.COL_DATE + ">=? AND " +
                        AmountDb.COL_DATE + "<=? AND " +
                        DbUtils.operatorEqual(AmountDb.COL_DELETED, false),

                new String[]{DbUtils.getParam(catId),
                        DbUtils.getParam(from),
                        DbUtils.getParam(to)},
                null);

        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }

        cursor.close();

        db.close();

        return total;

    }

    private Cursor getAmountByCategoryId(SQLiteDatabase db, long id, long from, long to) {
        return db.query(AmountDb.TABLE_NAME,
                null,
                AmountDb.COL_DATE + ">=? AND " +
                        AmountDb.COL_DATE + "<=? AND " +
                        AmountDb.COL_CATID + "=? AND " +
                        DbUtils.operatorEqual(AmountDb.COL_DELETED, false),
                new String[]{DbUtils.getParam(from), DbUtils.getParam(to), DbUtils.getParam(id)},
                null,
                null,
                null);
    }


}
