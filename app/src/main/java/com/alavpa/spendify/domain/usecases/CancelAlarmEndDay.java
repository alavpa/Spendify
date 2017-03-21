package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;

import javax.inject.Inject;

public class CancelAlarmEndDay{

    AlarmManager alarmManager;

    @Inject
    public CancelAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.cancelAlarmEndDay();
    }
}
