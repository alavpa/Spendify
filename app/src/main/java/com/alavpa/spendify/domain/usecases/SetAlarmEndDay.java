package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmEndDay;

import javax.inject.Inject;

public class SetAlarmEndDay{

    private
    AlarmManager alarmManager;

    private long time;

    public void setTime(long time) {
        this.time = time;
    }

    @Inject
    public SetAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        AlarmEndDay alarmEndDay = new AlarmEndDay(time);
        execute(alarmEndDay);
    }

    public void execute(AlarmEndDay alarmEndDay){
        alarmEndDay.set(alarmManager);
    }

}
