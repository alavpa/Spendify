package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;

import java.util.Calendar;

import javax.inject.Inject;

public class SetAlarmEndDay{

    AlarmManager alarmManager;

    Calendar time;

    @Inject
    public SetAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void execute(){
        alarmManager.setAlarmEndDay(time);
    }

}
