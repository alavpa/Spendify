package com.alavpa.spendify.data.db.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.alavpa.spendify.data.db.DbUtils;

/**
 * Created by alavpa on 19/02/17.
 */

public class AlarmDb {

    public static final String TABLE_NAME = "alarm";

    public static final String COL_ID = "_id";
    public static final String COL_AMOUNTID = "amountId";
    public static final String COL_CATID = "categoryId";
    public static final String COL_PERIOD = "period";
    public static final String COL_TIMES = "period_times";
    public static final String COL_DATE = "alarm_date";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COL_AMOUNTID + " INTEGER NOT NULL DEFAULT 0,"
            + COL_CATID + " INTEGER NOT NULL,"
            + COL_PERIOD + " INTEGER NOT NULL DEFAULT -1,"
            + COL_TIMES + " INTEGER NOT NULL DEFAULT 0,"
            + COL_DATE + " INTEGER NOT NULL"
            + ")";

    private long id;
    private AmountDb amountDb;
    private CategoryDb categoryDb;
    private int period;
    private int times;
    private long date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public AlarmDb fromCursor(Cursor cursor) {
        this.setId(DbUtils.getLong(cursor, COL_ID));
        this.setPeriod(DbUtils.getInt(cursor, COL_PERIOD));
        this.setTimes(DbUtils.getInt(cursor, COL_TIMES));
        this.setDate(DbUtils.getLong(cursor, COL_DATE));
        return this;
    }

    public AmountDb getAmountDb() {
        return amountDb;
    }

    public void setAmountDb(AmountDb amountDb) {
        this.amountDb = amountDb;
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

    public static final class Builder {
        private final ContentValues contentValues = new ContentValues();

        public Builder id(long id) {
            contentValues.put(COL_ID, id);
            return this;
        }

        public Builder amountId(long amountId) {
            contentValues.put(COL_AMOUNTID, amountId);
            return this;
        }

        public Builder categoryId(long categoryId) {
            contentValues.put(COL_CATID, categoryId);
            return this;
        }

        public Builder period(int period) {
            contentValues.put(COL_PERIOD, period);
            return this;
        }

        public Builder times(int times) {
            contentValues.put(COL_TIMES, times);
            return this;
        }

        public Builder date(long date) {
            contentValues.put(COL_DATE, date);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }
}
