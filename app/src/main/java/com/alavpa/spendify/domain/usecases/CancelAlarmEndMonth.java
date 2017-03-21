package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;

import javax.inject.Inject;

public class CancelAlarmEndMonth{

    AlarmManager alarmManager;

    @Inject
    public CancelAlarmEndMonth(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.cancelAlarmEndMonth();
    }

}
