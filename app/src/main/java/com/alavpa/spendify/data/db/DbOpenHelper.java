package com.alavpa.spendify.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alavpa.spendify.data.db.model.Amount;
import com.alavpa.spendify.data.db.model.Category;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alavpa on 19/02/17.
 */

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "spendify.db";
    private static final int DB_VERSION = 1;

    @Inject
    public DbOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Category.CREATE_TABLE);
        db.execSQL(Amount.CREATE_TABLE);

        db.insert(Category.TABLE_NAME,null,new Category.Builder()
                .description("Ocio")
                .income(false)
                .build());

        db.insert(Category.TABLE_NAME,null,new Category.Builder()
                .description("Hogar")
                .income(false)
                .build());

        db.insert(Category.TABLE_NAME,null,new Category.Builder()
                .description("Nomina")
                .income(true)
                .build());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
