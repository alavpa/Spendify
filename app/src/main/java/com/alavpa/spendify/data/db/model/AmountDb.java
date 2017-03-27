package com.alavpa.spendify.data.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.alavpa.spendify.data.db.DbUtils;

/**
 * Created by alavpa on 19/02/17.
 */

public class AmountDb {

    public static final String TABLE_NAME = "amount";

    public static final String COL_ID = "_id";
    public static final String COL_INCOME = "income";
    public static final String COL_AMOUNT = "amount";
    public static final String COL_DESC = "description";
    public static final String COL_CATID = "categoryId";
    public static final String COL_PERIOD = "period";
    public static final String COL_TIMES = "period_times";
    public static final String COL_DATE = "amount_date";
    public static final String COL_DELETED = "deleted";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COL_INCOME + " INTEGER NOT NULL DEFAULT 0,"
            + COL_AMOUNT + " REAL NOT NULL DEFAULT 0,"
            + COL_DESC + " TEXT,"
            + COL_CATID + " INTEGER NOT NULL,"
            + COL_PERIOD + " INTEGER NOT NULL DEFAULT -1,"
            + COL_TIMES + " INTEGER NOT NULL DEFAULT 0,"
            + COL_DELETED + " INTEGER NOT NULL DEFAULT 0,"
            + COL_DATE + " INTEGER NOT NULL"
            + ")";

    private long id;
    private boolean income;
    private double amount;
    private String description;
    private CategoryDb categoryDb;
    private int period;
    private int times;
    private long date;
    private boolean deleted;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryDb getCategoryDb() {
        return categoryDb;
    }

    public void setCategoryDb(CategoryDb categoryDb) {
        this.categoryDb = categoryDb;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public AmountDb fromCursor(Cursor cursor){
        this.setId(DbUtils.getLong(cursor,COL_ID));
        this.setIncome(DbUtils.getBoolean(cursor,COL_INCOME));
        this.setAmount(DbUtils.getDouble(cursor,COL_AMOUNT));
        this.setDescription(DbUtils.getString(cursor,COL_DESC));
        this.setPeriod(DbUtils.getInt(cursor,COL_PERIOD));
        this.setTimes(DbUtils.getInt(cursor,COL_TIMES));
        this.setDate(DbUtils.getLong(cursor,COL_DATE));
        this.setDeleted(DbUtils.getBoolean(cursor,COL_DELETED));
        return this;
    }

    public static final class Builder{
        private final ContentValues contentValues = new ContentValues();

        public Builder id(long id){
            contentValues.put(COL_ID,id);
            return this;
        }

        public Builder income(boolean income){
            contentValues.put(COL_INCOME,income);
            return this;
        }

        public Builder amount(double amount){
            contentValues.put(COL_AMOUNT,amount);
            return this;
        }

        public Builder description(String description){
            contentValues.put(COL_DESC,description);
            return this;
        }

        public Builder categoryId(long categoryId){
            contentValues.put(COL_CATID,categoryId);
            return this;
        }

        public Builder period(int period){
            contentValues.put(COL_PERIOD,period);
            return this;
        }

        public Builder times(int times){
            contentValues.put(COL_TIMES,times);
            return this;
        }

        public Builder date(long date){
            contentValues.put(COL_DATE,date);
            return this;
        }

        public ContentValues build(){
            return contentValues;
        }
    }
}
