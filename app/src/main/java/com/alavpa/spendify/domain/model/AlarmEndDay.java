package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AlarmEndDay extends AlarmRepeat{

    public static final int NOTIFICATION_ENDDAY_ID = 1;

    public AlarmEndDay(){

    }

    public AlarmEndDay(long time){
        date = calculateDate(Calendar.getInstance().getTimeInMillis(),time);
        period = new Period(date,Period.PER_DAY,1);
    }

    public long calculateDate(long currentTime, long time){
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(time);

        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(currentTime);

        alarmCalendar.set(Calendar.HOUR_OF_DAY,timeCalendar.get(Calendar.HOUR_OF_DAY));
        alarmCalendar.set(Calendar.MINUTE,timeCalendar.get(Calendar.MINUTE));
        alarmCalendar.set(Calendar.SECOND,0);

        long date = alarmCalendar.getTimeInMillis();
        while(date<currentTime){
            alarmCalendar.add(Calendar.DATE,1);
            date = alarmCalendar.getTimeInMillis();
        }

        return date;
    }

    public void set(AlarmManager alarmManager){
        setAlarm(alarmManager,AlarmManager.ACTION_ALARM_ENDDAY, AlarmManager.REQUEST_ALARM_ENDDAY);
    }

    public void cancel(AlarmManager alarmManager){
        cancelAlarm(alarmManager,AlarmManager.ACTION_ALARM_ENDDAY,AlarmManager.REQUEST_ALARM_ENDDAY);
    }

    public AlarmEndDay getNextAlarm() {
        AlarmEndDay alarmEndDay = new AlarmEndDay();
        alarmEndDay.setDate(period.getNextDateInMillis());
        alarmEndDay.setPeriod(new Period(date,period.getPeriod(),period.getTimes()));
        return alarmEndDay;
    }
}
