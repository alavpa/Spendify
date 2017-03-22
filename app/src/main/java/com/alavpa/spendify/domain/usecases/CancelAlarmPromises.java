package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;

import javax.inject.Inject;

public class CancelAlarmPromises {

    AlarmManager alarmManager;

    @Inject
    public CancelAlarmPromises(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.cancelAlarmPromises();
    }

}
