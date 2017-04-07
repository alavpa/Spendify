package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Calendar;

/**
 * Created by alavpa on 14/02/17.
 */

public class Period implements Parcelable {

    @IntDef({NO_PERIOD, PER_DAY, PER_WEEK, PER_MONTH, PER_YEAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PeriodMode{}

    public static final int NO_PERIOD = -1;
    public static final int PER_DAY = 0;
    public static final int PER_WEEK = 1;
    public static final int PER_MONTH = 2;
    public static final int PER_YEAR = 3;

    private
    int period;

    private
    long date;

    private int times;

    public Period(){
        this.period = NO_PERIOD;
        date = Calendar.getInstance().getTimeInMillis();
        times = 0;
    }

    public Period(long date, int period, int times){
        this.period = period;
        this.date = date;
        this.times = times;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(@PeriodMode int period) {

        if(period != this.period){
            times = 1;

            if(period==NO_PERIOD){
                times = 0;
            }
        }

        this.period = period;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.period);
        dest.writeLong(this.date);
        dest.writeInt(this.times);
    }

    protected Period(Parcel in) {
        this.period = in.readInt();
        this.date = in.readLong();
        this.times = in.readInt();
    }

    public static final Parcelable.Creator<Period> CREATOR = new Parcelable.Creator<Period>() {
        @Override
        public Period createFromParcel(Parcel source) {
            return new Period(source);
        }

        @Override
        public Period[] newArray(int size) {
            return new Period[size];
        }
    };

    public Period getNextPeriod(){

        return new Period(getNextDateInMillis(),period,times);
    }

    public long getNextDateInMillis(){

        Calendar next = Calendar.getInstance();
        next.setTimeInMillis(date);
        next.add(getCalendarPeriod(),times);
        return next.getTimeInMillis();
    }

    public int getCalendarPeriod(){
        switch (getPeriod()){
            default:
            case Period.PER_DAY:
                 return Calendar.DATE;

            case Period.PER_WEEK:
                return Calendar.WEEK_OF_YEAR;

            case Period.PER_MONTH:
                return Calendar.MONTH;

            case Period.PER_YEAR:
                return Calendar.YEAR;
        }
    }
}
