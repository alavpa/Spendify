package com.alavpa.spendify.ui.utils;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by alavpa on 12/05/17.
 */

public class AlarmUtils {

    @Inject
    public AlarmUtils(){}

    public long calculateNextEndDay(long time){
        long currentTime = System.currentTimeMillis();
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

    public long calculateEndMonth(int day){
        long currentTime = System.currentTimeMillis();
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

}
