package com.alavpa.spendify.data.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;

import java.util.ArrayList;
import java.util.List;

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
        List<CategoryDb> categories = new ArrayList<>();

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(CategoryDb.TABLE_NAME,
                null,
                DbUtils.operatorEqual(CategoryDb.COL_INCOME,income),
                null,null,null,null);
        while (cursor.moveToNext()){
            categories.add(CategoryDb.MAPPER(cursor));
        }
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
}
