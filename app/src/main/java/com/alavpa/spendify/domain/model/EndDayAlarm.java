package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class EndDayAlarm extends AlarmRepeat{

    public EndDayAlarm(long time){
        date = calculateDate(time);
        period = new Period(date,Period.PER_DAY,1);
    }

    private long calculateDate(long time){
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(time);

        Calendar alarmCalendar = Calendar.getInstance();

        alarmCalendar.set(Calendar.HOUR_OF_DAY,timeCalendar.get(Calendar.HOUR_OF_DAY));
        alarmCalendar.set(Calendar.MINUTE,timeCalendar.get(Calendar.MINUTE));
        alarmCalendar.set(Calendar.SECOND,0);

        long date = alarmCalendar.getTimeInMillis();
        while(date<System.currentTimeMillis()){
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
}
