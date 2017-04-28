package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;

import javax.inject.Inject;

public class CancelAlarmEndDay{

    private
    AlarmManager alarmManager;

    @Inject
    public CancelAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.cancelAlarm(AlarmManager.ACTION_ALARM_ENDDAY,
                AlarmManager.REQUEST_ALARM_ENDDAY);
    }
}
