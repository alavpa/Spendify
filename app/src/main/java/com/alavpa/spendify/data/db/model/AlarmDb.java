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
    public static final String COL_ACTION = "action";
    public static final String COL_REFID = "refId";
    public static final String COL_PERIOD = "period";
    public static final String COL_TIMES = "period_times";
    public static final String COL_DATE = "alarm_date";
    public static final String COL_ACTIVE = "active";

    public static final String CREATE_TABLE = ""
            + "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,"
            + COL_ACTION + " TEXT,"
            + COL_REFID + " INTEGER NOT NULL DEFAULT 0,"
            + COL_PERIOD + " INTEGER NOT NULL DEFAULT -1,"
            + COL_TIMES + " INTEGER NOT NULL DEFAULT 0,"
            + COL_ACTIVE + " INTEGER NOT NULL DEFAULT 1,"
            + COL_DATE + " INTEGER NOT NULL"
            + ")";

    private long id;
    private long refId;
    private String action;
    private int period;
    private int times;
    private long date;
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public AlarmDb fromCursor(Cursor cursor) {
        this.setId(DbUtils.getLong(cursor, COL_ID));
        this.setRefId(DbUtils.getLong(cursor,COL_REFID));
        this.setAction(DbUtils.getString(cursor,COL_ACTION));
        this.setPeriod(DbUtils.getInt(cursor, COL_PERIOD));
        this.setTimes(DbUtils.getInt(cursor, COL_TIMES));
        this.setActive(DbUtils.getBoolean(cursor,COL_ACTIVE));
        this.setDate(DbUtils.getLong(cursor, COL_DATE));
        return this;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public static final class Builder {
        private final ContentValues contentValues = new ContentValues();

        public Builder id(long id) {
            contentValues.put(COL_ID, id);
            return this;
        }

        public Builder action(String action){
            contentValues.put(COL_ACTION,action);
            return this;
        }

        public Builder refId(long refId) {
            contentValues.put(COL_REFID, refId);
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

        public Builder active(boolean active){
            contentValues.put(COL_ACTIVE,active);
            return this;
        }

        public ContentValues build() {
            return contentValues;
        }
    }
}
