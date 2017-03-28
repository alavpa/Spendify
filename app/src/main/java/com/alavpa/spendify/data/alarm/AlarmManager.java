package com.alavpa.spendify.data.alarm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmManager {

    public static final int REQUEST_ALARM_ENDDAY = 1;
    public static final int REQUEST_ALARM_ENDMONTH = 2;
    public static final int REQUEST_ALARM_PROMISES = 3;
    public static final int REQUEST_ALARM_AMOUNT = 1000;
    public static final int REQUEST_ALARM_OFFLIMIT = 10000;
    public static final String ACTION_ALARM_ENDDAY = "com.alavpa.spendify.data.alarm.ACTION_ALARM_ENDDAY";
    public static final String ACTION_ALARM_ENDMONTH = "com.alavpa.spendify.data.alarm.ACTION_ALARM_ENDMONTH";
    public static final String ACTION_ALARM_PROMISES = "com.alavpa.spendify.data.alarm.ACTION_PROMISES";
    public static final String ACTION_ALARM_AMOUNT = "com.alavpa.spendify.data.alarm.ACTION_AMOUNT";
    public static final String ACTION_ALARM_OFFLIMIT = "com.alavpa.spendify.data.alarm.ACTION_OFFLIMIT";
    public static final String EXTRA_ALARM_DATA = "EXTRA_ALARM_DATA";
    public static final String EXTRA_ALARM_OFFLIMIT = "EXTRA_ALARM_OFFLIMIT";

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

        //24*60*60*1000 one day in millis
        setAlarmRepeat(ACTION_ALARM_ENDDAY,REQUEST_ALARM_ENDDAY,alarm.getTimeInMillis(),24*60*60*1000);

    }

    public void setAlarmEndMonth(int day){

        Calendar alarm = Calendar.getInstance();
        alarm.set(Calendar.DATE,day);
        alarm.set(Calendar.DATE,-1);

        if(alarm.getTimeInMillis()<System.currentTimeMillis()){
            alarm.add(Calendar.MONTH,1);
        }

        setAlarm(ACTION_ALARM_ENDMONTH,REQUEST_ALARM_ENDMONTH,alarm.getTimeInMillis());
    }

    public void setAlarm(String action, int request, long time){
        PendingIntent pendingIntent = getPendingIntent(action,request,time);
        setAlarm(pendingIntent,time);
    }
    private void setAlarm(PendingIntent pendingIntent, long time){
        alarmManager.set(android.app.AlarmManager.RTC,time,pendingIntent);
    }
    private void setAlarmRepeat(String action, int request, long time, long period){
        PendingIntent pendingIntent = getPendingIntent(action,request,time);
        setAlarmRepeat(pendingIntent,time,period);
    }

    private void setAlarmRepeat(PendingIntent pendingIntent, long time, long period){
        alarmManager.setRepeating(android.app.AlarmManager.RTC,time,period,pendingIntent);
    }

    private Intent getIntent(String action, long time){
        Intent intent = new Intent(action);
        intent.putExtra(EXTRA_ALARM_DATA,time);
        return intent;
    }

    private PendingIntent getPendingIntent(String action, int request, long time){
        Intent intent = getIntent(action,time);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

    private PendingIntent getPendingIntent(int request, Intent intent){
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        return pendingIntent;
    }

    public void cancelAlarmEndDay() {
        cancelAlarm(ACTION_ALARM_ENDDAY, REQUEST_ALARM_ENDDAY);
    }

    public void cancelAlarmEndMonth() {
        cancelAlarm(ACTION_ALARM_ENDMONTH, REQUEST_ALARM_ENDMONTH);
    }

    public void cancelAlarm(String action, int request) {
        Intent intent = new Intent(action);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, request,intent,PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }

    public void setAlarmPromises(Period period) {

        setAlarm(ACTION_ALARM_PROMISES,REQUEST_ALARM_PROMISES,period.getNextDateInMillis());

    }

    public void setAlarmAmount(Amount amount) {
        int request = REQUEST_ALARM_AMOUNT + (int)amount.getId();
        setAlarm(ACTION_ALARM_AMOUNT,request,amount.getPeriod().getNextDateInMillis());
    }

    public void cancelAlarmPromises() {
        cancelAlarm(ACTION_ALARM_PROMISES,REQUEST_ALARM_PROMISES);
    }

    public void cancelAlarmAmount(Amount amount) {
        int request = REQUEST_ALARM_AMOUNT + (int)amount.getId();
        cancelAlarm(ACTION_ALARM_AMOUNT,request);
    }

    public void setAlarmOfflimit(Category category) {
        int request = REQUEST_ALARM_OFFLIMIT + (int)category.getId();
        long time = System.currentTimeMillis() + 1*60*1000;
        Intent intent = getIntent(ACTION_ALARM_OFFLIMIT,time);
        intent.putExtra(EXTRA_ALARM_OFFLIMIT,category);
        PendingIntent pendingIntent = getPendingIntent(request, intent);
        setAlarm(pendingIntent,time);
    }

    public void cancelAlarmOfflimit(Category category) {
        int request = REQUEST_ALARM_OFFLIMIT + (int)category.getId();
        cancelAlarm(ACTION_ALARM_OFFLIMIT,request);
    }
}
