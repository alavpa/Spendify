package com.alavpa.spendify.data.db;

import android.database.Cursor;

/**
 * Created by alavpa on 19/02/17.
 */

public class DbUtils {

    public static long getLong(Cursor cursor, String columnName){
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static String getString(Cursor cursor, String columnName){
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getInt(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static boolean getBoolean(Cursor cursor, String columnName){
        return cursor.getInt(cursor.getColumnIndex(columnName))==1;
    }

    public static double getDouble(Cursor cursor, String columnName){
        return cursor.getDouble(cursor.getColumnIndex(columnName));
    }

    public static String operatorEqual(String field, boolean value){
        return (value)?field+"=1":field+"=0";
    }

}
