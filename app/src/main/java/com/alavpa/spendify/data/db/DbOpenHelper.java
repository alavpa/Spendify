package com.alavpa.spendify.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alavpa.spendify.data.db.model.AlarmDb;
import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.di.qualifiers.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alavpa on 19/02/17.
 */

@Singleton
public class DbOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "spendify.db";
    private static final int DB_VERSION = 2;

    @Inject
    public DbOpenHelper(@ApplicationContext Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CategoryDb.CREATE_TABLE);
        db.execSQL(AmountDb.CREATE_TABLE);
        db.execSQL(AlarmDb.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion == 2) {
            db.execSQL(AlarmDb.CREATE_TABLE);
        }
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }
}
