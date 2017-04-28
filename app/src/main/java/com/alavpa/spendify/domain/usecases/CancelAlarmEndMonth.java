package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;

import javax.inject.Inject;

public class CancelAlarmEndMonth{

    private
    AlarmManager alarmManager;

    @Inject
    public CancelAlarmEndMonth(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.cancelAlarm(AlarmManager.ACTION_ALARM_ENDMONTH,
                AlarmManager.REQUEST_ALARM_ENDMONTH);
    }

}
