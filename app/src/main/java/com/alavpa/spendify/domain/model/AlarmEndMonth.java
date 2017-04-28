package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AlarmEndMonth extends AlarmRepeat implements Parcelable {

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

    }

    public AlarmEndMonth(int day){
        date = calculateDate(Calendar.getInstance().getTimeInMillis(),day);
        period = new Period(date,Period.PER_MONTH,1);
    }

    protected AlarmEndMonth(Parcel in) {
        super(in);
    }

    public long calculateDate(long currentTime, int day){
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(currentTime);
        alarmCalendar.set(Calendar.DATE,day);
        alarmCalendar.add(Calendar.DATE,-1);

        long date = alarmCalendar.getTimeInMillis();
        while(date<currentTime){
            alarmCalendar.add(Calendar.MONTH,1);
            date = alarmCalendar.getTimeInMillis();
        }

        return date;
    }

    public AlarmEndMonth getNextAlarm() {
        AlarmEndMonth alarmEndMonth = new AlarmEndMonth();
        alarmEndMonth.setDate(period.getNextDateInMillis());
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
