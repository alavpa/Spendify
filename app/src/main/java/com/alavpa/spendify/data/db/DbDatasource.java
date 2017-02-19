package com.alavpa.spendify.data.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.Category;

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

    public List<Category> getCategories(boolean income){
        List<Category> categories = new ArrayList<>();

        SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
        Cursor cursor = db.query(Category.TABLE_NAME,
                null,
                DbUtils.operatorEqual(Category.COL_INCOME,income),
                null,null,null,null);
        while (cursor.moveToNext()){
            categories.add(Category.MAPPER(cursor));
        }
        cursor.close();
        db.close();

        return categories;
    }
}
