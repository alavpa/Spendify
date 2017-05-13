package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.alarm.AlarmManager;

import java.util.Calendar;

public class AlarmEndMonth extends Alarm implements Parcelable {

    public static final int NOTIFICATION_ENDMONTH_ID = 2;
    public static final Creator<AlarmEndMonth> CREATOR = new Creator<AlarmEndMonth>() {
        @Override
        public AlarmEndMonth createFromParcel(Parcel source) {
            return new AlarmEndMonth(source);
        }

        @Override
        public AlarmEndMonth[] newArray(int size) {
            return new AlarmEndMonth[size];
        }
    };

    public AlarmEndMonth(){
        super(AlarmManager.ACTION_ALARM_ENDMONTH);
    }

    public AlarmEndMonth(long time){
        this();
        period = new Period(time,Period.PER_MONTH,1);
    }

    protected AlarmEndMonth(Parcel in) {
        super(in);
    }


    public AlarmEndMonth getNextAlarm() {
        AlarmEndMonth alarmEndMonth = new AlarmEndMonth();
        long date = period.getNextDateInMillis();
        period = new Period(date,Period.PER_MONTH,1);
        return alarmEndMonth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
