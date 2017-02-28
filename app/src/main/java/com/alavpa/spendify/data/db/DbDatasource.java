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

    public List<CategoryDb> getCategories(boolean income){
        List<CategoryDb> categories;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = getCursorCategories(db,income);
        categories = getCategoryList(cursor);
        cursor.close();
        db.close();

        return categories;
    }

    private Cursor getCursorCategories(SQLiteDatabase db, boolean income){
        return db.query(CategoryDb.TABLE_NAME,
                null,
                DbUtils.operatorEqual(CategoryDb.COL_INCOME,income),
                null,null,null,null);
    }

    private List<CategoryDb> getCategoryList(Cursor cursor){
        List<CategoryDb> categories = new ArrayList<>();
        while (cursor.moveToNext()){
            categories.add(CategoryDb.MAPPER(cursor));
        }
        return categories;
    }

    private Map<Long, CategoryDb> getCategoryMap(Cursor cursor){
        Map<Long, CategoryDb> categories = new HashMap<>();
        while (cursor.moveToNext()){
            CategoryDb categoryDb = CategoryDb.MAPPER(cursor);
            categories.put(categoryDb.getId(),categoryDb);
        }
        return categories;
    }

    @Override
    public Map<Long, CategoryDb> getHashCategories(boolean income) {
        Map<Long,CategoryDb> categories;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = getCursorCategories(db,income);
        categories = getCategoryMap(cursor);
        cursor.close();
        db.close();

        return categories;
    }

    @Override
    public AmountDb insertAmount(AmountDb amountDb){

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
    public CategoryDb insertCategory(CategoryDb categoryDb){

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
    public double getSumByCategory(long categoryId){

        double sum = 0;

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(" + AmountDb.COL_AMOUNT + ") " +
                        "FROM " + AmountDb.TABLE_NAME + " " +
                        "WHERE " + AmountDb.COL_CATID + " = ?",
                new String[]{String.valueOf(categoryId)},
                null);

        if (cursor.moveToFirst()){
            sum = cursor.getDouble(0);
        }

        cursor.close();
        db.close();

        return sum;
    }

    @Override
    public List<AmountDb> getAmountBy(boolean income, long from, long to) {

        List<AmountDb> list;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = getAmountBy(db,income,from,to);

        list = getAmountList(cursor);

        cursor.close();
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

    @Override
    public Map<Long, AmountDb> getHashAmountBy(boolean income, long from, long to) {
        Map<Long,AmountDb> list;
        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();

        Cursor cursor = getAmountBy(db,income,from,to);

        list = getAmountMap(cursor);

        cursor.close();
        db.close();

        return list;
    }

    private Map<Long, AmountDb> getAmountMap(Cursor cursor){
        Map<Long, AmountDb> amounts = new HashMap<>();
        while (cursor.moveToNext()){
            AmountDb amountDb = AmountDb.MAPPER(cursor);
            amounts.put(amountDb.getId(),amountDb);
        }
        return amounts;
    }

    private List<AmountDb> getAmountList(Cursor cursor){
        List<AmountDb> amounts = new ArrayList<>();
        while (cursor.moveToNext()){
            amounts.add(AmountDb.MAPPER(cursor));
        }
        return amounts;
    }

}
