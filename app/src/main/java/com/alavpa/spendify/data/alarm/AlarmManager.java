package com.alavpa.spendify.data.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import com.alavpa.spendify.Application;
import com.alavpa.spendify.BuildConfig;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.qualifiers.ApplicationContext;
import com.alavpa.spendify.domain.model.Alarm;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmManager {

    public static final String ACTION_ALARM_ENDDAY = BuildConfig.APPLICATION_ID + ".data.alarm.ACTION_ALARM_ENDDAY";
    public static final String ACTION_ALARM_ENDMONTH = BuildConfig.APPLICATION_ID + ".data.alarm.ACTION_ALARM_ENDMONTH";
    public static final String ACTION_ALARM_AMOUNT = BuildConfig.APPLICATION_ID + ".data.alarm.ACTION_AMOUNT";
    public static final String ACTION_ALARM_OFFLIMIT = BuildConfig.APPLICATION_ID + ".data.alarm.ACTION_OFFLIMIT";
    public static final String EXTRA_ALARM_DATA = "EXTRA_ALARM_DATA";
    public static final String EXTRA_ALARM_PARCELABLE = "EXTRA_ALARM_PARCELABLE";

    Context context;
    android.app.AlarmManager alarmManager;

    @Inject
    public AlarmManager(@ApplicationContext Context context) {
        this.context = context;
        alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    public void setAlarm(String action, long request, long time, Parcelable alarm) {
        PendingIntent pendingIntent = getPendingIntent(action, (int)request, time, alarm);
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

    public void cancelAlarm(String action, long request) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)request, intent, PendingIntent
                .FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public void setAlarm(String action, Alarm alarm) {

    }
}
