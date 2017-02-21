package com.alavpa.spendify.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;

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

        db.execSQL(CategoryDb.CREATE_TABLE);
        db.execSQL(AmountDb.CREATE_TABLE);

        db.insert(CategoryDb.TABLE_NAME,null,new CategoryDb.Builder()
                .name("Ocio")
                .income(false)
                .build());

        db.insert(CategoryDb.TABLE_NAME,null,new CategoryDb.Builder()
                .name("Hogar")
                .income(false)
                .build());

        db.insert(CategoryDb.TABLE_NAME,null,new CategoryDb.Builder()
                .name("Nomina")
                .income(true)
                .build());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
