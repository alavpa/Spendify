package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmEndMonth;

import javax.inject.Inject;

public class SetAlarmEndMonth{

    private
    AlarmManager alarmManager;

    private AlarmEndMonth alarmEndMonth;

    public void setAlarmEndMonth(AlarmEndMonth alarmEndMonth) {
        this.alarmEndMonth = alarmEndMonth;
    }

    @Inject
    public SetAlarmEndMonth(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmEndMonth.set(alarmManager);
    }
}
