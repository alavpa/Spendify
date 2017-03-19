package com.alavpa.spendify.data.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmManager {

    public static final int REQUEST_ALARM_ENDDAY = 1;
    public static final int REQUEST_ALARM_ENDMONTH = 2;
    public static final String ACTION_ALARM_ENDDAY = "com.alavpa.spendify.data.alarm.ACTION_ALARM_ENDDAY";
    public static final String ACTION_ALARM_ENDMONTH = "com.alavpa.spendify.data.alarm.ACTION_ALARM_ENDMONTH";
    public static final String EXTRA_ALARM_DATA = "EXTRA_ALARM_DATA";

    Context context;
    android.app.AlarmManager alarmManager;

    @Inject
    public AlarmManager(Context context){
        this.context = context;
        alarmManager = (android.app.AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarmEndDay(Calendar time){

        Calendar alarm = Calendar.getInstance();

        alarm.set(Calendar.HOUR_OF_DAY,time.get(Calendar.HOUR_OF_DAY));
        alarm.set(Calendar.MINUTE,time.get(Calendar.MINUTE));
        alarm.set(Calendar.SECOND,0);

        if(alarm.getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){
            alarm.add(Calendar.DATE,1);
        }

        Intent intent = new Intent(ACTION_ALARM_ENDDAY);
        intent.putExtra(EXTRA_ALARM_DATA,time.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_ALARM_ENDDAY,intent,PendingIntent.FLAG_ONE_SHOT);

        alarmManager.set(android.app.AlarmManager.RTC,time.getTimeInMillis(),pendingIntent);
    }

    public void setAlarmEndMonth(int day){

        Calendar alarm = Calendar.getInstance();
        alarm.set(Calendar.DATE,day);
        alarm.set(Calendar.DATE,-1);

        if(alarm.getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){
            alarm.add(Calendar.MONTH,1);
        }

        Intent intent = new Intent(ACTION_ALARM_ENDMONTH);

        intent.putExtra(EXTRA_ALARM_DATA,alarm.getTimeInMillis());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_ALARM_ENDMONTH,intent,PendingIntent.FLAG_ONE_SHOT);

        alarmManager.set(android.app.AlarmManager.RTC,alarm.getTimeInMillis(),pendingIntent);
    }

    public void cancelAlarmEndDay() {
        cancelAlarm(REQUEST_ALARM_ENDDAY);
    }

    public void cancelAlarmEndMonth() {
        cancelAlarm(REQUEST_ALARM_ENDMONTH);
    }

    public void cancelAlarm(int request) {
        Intent intent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request,intent,0);
        alarmManager.cancel(pendingIntent);
    }
}
