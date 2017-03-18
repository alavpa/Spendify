package com.alavpa.spendify.data.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.alavpa.spendify.data.db.DbUtils;

/**
 * Created by alavpa on 19/02/17.
 */

public class CategoryDb {

    public static final String TABLE_NAME = "category";

    public static final String COL_ID = "_id";
    public static final String COL_INCOME = "income";
    public static final String COL_NAME = "name";
    public static final String COL_COLOR = "color";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COL_INCOME + " INTEGER NOT NULL DEFAULT 0,"
            + COL_COLOR + " INTEGER NOT NULL DEFAULT 0,"
            + COL_NAME + " TEXT NOT NULL"
            + ")";

    private long id;
    private String name;
    private boolean income;
    private int color;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public CategoryDb fromCursor(Cursor cursor){
        this.setId(DbUtils.getLong(cursor,COL_ID));
        this.setIncome(DbUtils.getBoolean(cursor,COL_INCOME));
        this.setName(DbUtils.getString(cursor,COL_NAME));
        this.setColor(DbUtils.getInt(cursor,COL_COLOR));
        return this;
    }

    public static final class Builder{
        private final ContentValues contentValues = new ContentValues();

        public Builder id(long id){
            contentValues.put(COL_ID,id);
            return this;
        }

        public Builder name(String description){
            contentValues.put(COL_NAME,description);
            return this;
        }

        public Builder income(boolean income){
            contentValues.put(COL_INCOME,income);
            return this;
        }

        public Builder color(int color){
            contentValues.put(COL_COLOR,color);
            return this;
        }

        public ContentValues build(){
            return contentValues;
        }
    }
}
