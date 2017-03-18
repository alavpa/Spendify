package com.alavpa.spendify.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;

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
    public DbDatasource(DbOpenHelper dbOpenHelper){
        this.dbOpenHelper = dbOpenHelper;
    }

    public synchronized List<CategoryDb> getCategories(boolean income){
        List<CategoryDb> categories;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = getCategories(db,income);
        categories = getCategoryList(cursor);
        cursor.close();
        db.close();

        return categories;
    }

    private Cursor getCategories(SQLiteDatabase db, boolean income){
        return db.query(CategoryDb.TABLE_NAME,
                null,
                DbUtils.operatorEqual(CategoryDb.COL_INCOME,income),
                null,null,null,null);
    }

    private List<CategoryDb> getCategoryList(Cursor cursor){
        List<CategoryDb> categories = new ArrayList<>();
        while (cursor.moveToNext()){
            categories.add(new CategoryDb().fromCursor(cursor));
        }
        return categories;
    }

    private Map<Long, CategoryDb> getCategoryMap(Cursor cursor){
        Map<Long, CategoryDb> categories = new HashMap<>();
        while (cursor.moveToNext()){
            CategoryDb categoryDb = new CategoryDb().fromCursor(cursor);
            categories.put(categoryDb.getId(),categoryDb);
        }
        return categories;
    }

    @Override
    public synchronized AmountDb insertAmount(AmountDb amountDb){

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
        long id = db.insert(AmountDb.TABLE_NAME,null,contentValues);
        db.close();
        amountDb.setId(id);

        return amountDb;
    }

    @Override
    public synchronized CategoryDb insertCategory(CategoryDb categoryDb){

        ContentValues contentValues = new CategoryDb.Builder()
                .name(categoryDb.getName())
                .income(categoryDb.isIncome())
                .color(categoryDb.getColor())
                .build();

        SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
        long id = db.insert(CategoryDb.TABLE_NAME,null,contentValues);
        db.close();
        categoryDb.setId(id);

        return categoryDb;
    }

    @Override
    public synchronized double getSumBy(boolean income, long from, long to){

        double total = 0;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(" + AmountDb.COL_AMOUNT + ") " +
                        "FROM " + AmountDb.TABLE_NAME + " " +
                        "WHERE " + AmountDb.COL_INCOME + "=? AND " +
                        AmountDb.COL_DATE + ">=? AND " +
                        AmountDb.COL_DATE + "<=?",

                new String[]{DbUtils.getParam(income),
                        DbUtils.getParam(from),
                        DbUtils.getParam(to)},
                null);

        if (cursor.moveToFirst()){
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
            Cursor cursorCat = getCategories(db,income);
            Map<Long,CategoryDb> categories = getCategoryMap(cursorCat);
            cursorCat.close();

            Cursor cursor = getAmountBy(db,income,from,to);

            list = getAmountList(cursor,categories);

            cursor.close();
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }

        db.close();

        return list;
    }

    private Cursor getAmountBy(SQLiteDatabase db, boolean income, long from, long to){
        return db.query(AmountDb.TABLE_NAME,
                null,
                AmountDb.COL_DATE + ">=? AND " +
                        AmountDb.COL_DATE + "<=? AND " +
                        AmountDb.COL_INCOME + "=?",
                new String[]{DbUtils.getParam(from),DbUtils.getParam(to),DbUtils.getParam(income)},
                AmountDb.COL_CATID,
                null,
                null);
    }

    private List<AmountDb> getAmountList(Cursor cursor, Map<Long,CategoryDb> categoryDbMap){
        List<AmountDb> amounts = new ArrayList<>();
        while (cursor.moveToNext()){
            AmountDb amountDb = new AmountDb().fromCursor(cursor);
            amounts.add(amountDb);

            long catId = DbUtils.getLong(cursor,AmountDb.COL_CATID);
            amountDb.setCategoryDb(categoryDbMap.get(catId));
        }
        return amounts;
    }



}
