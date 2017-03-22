package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;

import javax.inject.Inject;

public class SetAlarmPromises {

    AlarmManager alarmManager;
    PrefsDatasource preferences;


    @Inject
    public SetAlarmPromises(AlarmManager alarmManager, PrefsDatasource preferences){
        this.alarmManager = alarmManager;
        this.preferences = preferences;
    }

    public void execute(){
        alarmManager.setAlarmPromises(preferences.getNotifyPromisesPeriod());
    }
}
