package com.alavpa.spendify.domain.model;

import android.os.Parcel;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AlarmEndMonth extends AlarmRepeat{

    public static final int NOTIFICATION_ENDMONTH_ID = 2;

    public AlarmEndMonth(){

    }

    public AlarmEndMonth(int day){
        date = calculateDate(Calendar.getInstance().getTimeInMillis(),day);
        period = new Period(date,Period.PER_MONTH,1);
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

    public void set(AlarmManager alarmManager){
        setAlarm(alarmManager,AlarmManager.ACTION_ALARM_ENDMONTH, AlarmManager.REQUEST_ALARM_ENDMONTH);
    }

    public void cancel(AlarmManager alarmManager){
        cancelAlarm(alarmManager,AlarmManager.ACTION_ALARM_ENDMONTH,AlarmManager.REQUEST_ALARM_ENDMONTH);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    protected AlarmEndMonth(Parcel in) {
        super(in);
    }

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
}
