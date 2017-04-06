package com.alavpa.spendify.data.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmManager {

    public static final int REQUEST_ALARM_ENDDAY = 1;
    public static final int REQUEST_ALARM_ENDMONTH = 2;
    public static final int REQUEST_ALARM_AMOUNT = 1000;
    public static final int REQUEST_ALARM_OFFLIMIT = 10000;
    public static final String ACTION_ALARM_ENDDAY = "com.alavpa.spendify.data.alarm.ACTION_ALARM_ENDDAY";
    public static final String ACTION_ALARM_ENDMONTH = "com.alavpa.spendify.data.alarm.ACTION_ALARM_ENDMONTH";
    public static final String ACTION_ALARM_AMOUNT = "com.alavpa.spendify.data.alarm.ACTION_AMOUNT";
    public static final String ACTION_ALARM_OFFLIMIT = "com.alavpa.spendify.data.alarm.ACTION_OFFLIMIT";
    public static final String EXTRA_ALARM_DATA = "EXTRA_ALARM_DATA";
    public static final String EXTRA_ALARM_PARCELABLE = "EXTRA_ALARM_PARCELABLE";

    Context context;
    android.app.AlarmManager alarmManager;

    @Inject
    public AlarmManager(Context context) {
        this.context = context;
        alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(String action, int request, long time, Parcelable parcelable) {
        PendingIntent pendingIntent = getPendingIntent(action, request, time, parcelable);
        setAlarm(pendingIntent, time);
    }

    private void setAlarm(PendingIntent pendingIntent, long time) {
        alarmManager.set(android.app.AlarmManager.RTC, time, pendingIntent);
    }

    private Intent getIntent(String action, long time, Parcelable parcelable) {
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_ALARM_DATA, time);
        intent.putExtra(EXTRA_ALARM_PARCELABLE, parcelable);
        return intent;
    }

    private PendingIntent getPendingIntent(String action, int request, long time, Parcelable parcelable) {
        Intent intent = getIntent(action, time, parcelable);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

    public void cancelAlarm(String action, int request) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }
}
