package com.alavpa.spendify.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.alavpa.spendify.di.qualifiers.ApplicationContext;
import com.alavpa.spendify.domain.model.Period;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefsDatasource {

    public static final String KEY_MONTHDAY = "KEY_MONTHDAY";
    private static final String KEY_INIT = "KEY_INIT";
    private static final String KEY_ENDOFDAY = "KEY_ENDOFDAY";
    private static final String KEY_ENDOFDAY_TIME = "KEY_ENDOFDAY_TIME";
    private static final String KEY_ENDOFMONTH = "KEY_ENDOFMONTH";
    private static final String KEY_OFFLIMIT = "KEY_OFFLIMIT";
    private static final String KEY_PROMISES = "KEY_PROMISES";
    private static final String KEY_PROMISES_PERIOD = "KEY_PROMISES_PERIOD";
    private static final String KEY_PROMISES_PERIOD_TIMES = "KEY_PROMISES_PERIOD_TIMES";
    private static final String KEY_PROMISES_PERIOD_DATE = "KEY_PROMISES_PERIOD_DATE";
    private SharedPreferences sharedPreferences;

    @Inject
    public PrefsDatasource(@ApplicationContext Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getMonthDay(){
        return sharedPreferences.getInt(KEY_MONTHDAY,1);
    }

    public void setMonthDay(int day){
        set(KEY_MONTHDAY,day);
    }

    public boolean isInitialized(){
        return sharedPreferences.getBoolean(KEY_INIT,false);
    }

    public void setInitialized(boolean initialized){
        set(KEY_INIT,initialized);
    }

    public boolean notifyEndOfDay() {
        return sharedPreferences.getBoolean(KEY_ENDOFDAY,true);
    }

    public void setNotifyEndOfDay(boolean value){
        set(KEY_ENDOFDAY,value);
    }

    public Calendar getEndOfDayTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,20);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        long time = sharedPreferences.getLong(KEY_ENDOFDAY_TIME,calendar.getTimeInMillis());
        calendar.setTimeInMillis(time);

        return calendar;
    }

    public void setEndOfDayTime(Calendar calendar){

        set(KEY_ENDOFDAY_TIME,calendar.getTimeInMillis());
    }

    public boolean notifyEndOfMonth() {
        return sharedPreferences.getBoolean(KEY_ENDOFMONTH,true);
    }

    public void setNotifyEndOfMonth(boolean value){
        set(KEY_ENDOFMONTH,value);
    }

    public boolean notifyOfflimit() {
        return sharedPreferences.getBoolean(KEY_OFFLIMIT,true);
    }

    public void setNotifyOfflimit(boolean value){
        set(KEY_OFFLIMIT,value);
    }

    public boolean notifyPromises() {
        return sharedPreferences.getBoolean(KEY_PROMISES,false);
    }

    public void setNotifyPromises(boolean value){
        set(KEY_PROMISES,value);
    }

    public Period getNotifyPromisesPeriod(){

        Calendar calendar = Calendar.getInstance();
        long date = sharedPreferences.getLong(KEY_PROMISES_PERIOD_DATE,calendar.getTimeInMillis());
        int period = sharedPreferences.getInt(KEY_PROMISES_PERIOD,Period.PER_DAY);
        int times = sharedPreferences.getInt(KEY_PROMISES_PERIOD_TIMES,1);

        return new Period(date,period,times);
    }

    public void setNotifyPromisesPeriod(Period period){

        set(KEY_PROMISES_PERIOD_DATE,period.getDate());
        set(KEY_PROMISES_PERIOD,period.getPeriod());
        set(KEY_PROMISES_PERIOD_TIMES,period.getTimes());
    }

    private void set(String key, boolean value){
        sharedPreferences.edit()
                .putBoolean(key,value)
                .apply();
    }

    private void set(String key, int value){
        sharedPreferences.edit()
                .putInt(key,value)
                .apply();
    }

    private void set(String key, long value){
        sharedPreferences.edit()
                .putLong(key,value)
                .apply();
    }
}
