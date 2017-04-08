package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmEndMonth;

import javax.inject.Inject;

public class SetAlarmEndMonth{

    private
    AlarmManager alarmManager;

    private int day;

    public void setDay(int day) {
        this.day = day;
    }

    @Inject
    public SetAlarmEndMonth(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        AlarmEndMonth alarmEndMonth = new AlarmEndMonth(day);
        execute(alarmEndMonth);
    }

    public void execute(AlarmEndMonth alarmEndMonth){
        alarmEndMonth.set(alarmManager);
    }
}
