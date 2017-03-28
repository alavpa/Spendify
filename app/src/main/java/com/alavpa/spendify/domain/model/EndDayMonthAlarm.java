package com.alavpa.spendify.domain.model;

import android.os.Parcel;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class EndDayMonthAlarm extends AlarmRepeat{

    public EndDayMonthAlarm(int time){
        date = calculateDate(time);
        period = new Period(date,Period.PER_MONTH,1);
    }

    private long calculateDate(int time){
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.set(Calendar.DATE,time);
        alarmCalendar.add(Calendar.DATE,-1);

        long date = alarmCalendar.getTimeInMillis();
        while(date<System.currentTimeMillis()){
            alarmCalendar.add(Calendar.MONTH,1);
            date = alarmCalendar.getTimeInMillis();
        }

        return date;
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

    protected EndDayMonthAlarm(Parcel in) {
        super(in);
    }

    public static final Creator<EndDayMonthAlarm> CREATOR = new Creator<EndDayMonthAlarm>() {
        @Override
        public EndDayMonthAlarm createFromParcel(Parcel source) {
            return new EndDayMonthAlarm(source);
        }

        @Override
        public EndDayMonthAlarm[] newArray(int size) {
            return new EndDayMonthAlarm[size];
        }
    };
}
