package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmEndMonth;

import javax.inject.Inject;

public class CancelAlarmEndMonth{

    private
    AlarmManager alarmManager;

    private AlarmEndMonth alarmEndMonth;

    public void setAlarmEndMonth(AlarmEndMonth alarmEndMonth) {
        this.alarmEndMonth = alarmEndMonth;
    }

    @Inject
    public CancelAlarmEndMonth(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmEndMonth.cancel(alarmManager);
    }

}
