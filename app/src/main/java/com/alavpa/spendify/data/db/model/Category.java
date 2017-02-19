package com.alavpa.spendify.data.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.alavpa.spendify.data.db.DbUtils;

/**
 * Created by alavpa on 19/02/17.
 */

public class Category {

    public static final String TABLE_NAME = "category";

    public static final String COL_ID = "_id";
    public static final String COL_INCOME = "income";
    public static final String COL_NAME = "name";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COL_INCOME + " INTEGER NOT NULL DEFAULT 0,"
            + COL_NAME + " TEXT NOT NULL"
            + ")";

    public static Category MAPPER(Cursor cursor){
        Category amount = new Category();
        amount.setId(DbUtils.getLong(cursor,COL_ID));
        amount.setIncome(DbUtils.getBoolean(cursor,COL_INCOME));
        amount.setName(DbUtils.getString(cursor,COL_NAME));
        return amount;
    }

    private long id;
    private String name;
    private boolean income;

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

    public static final class Builder{
        private final ContentValues contentValues = new ContentValues();

        public Builder id(long id){
            contentValues.put(COL_ID,id);
            return this;
        }

        public Builder description(String description){
            contentValues.put(COL_NAME,description);
            return this;
        }

        public Builder income(boolean income){
            contentValues.put(COL_INCOME,income);
            return this;
        }

        public ContentValues build(){
            return contentValues;
        }
    }
}
