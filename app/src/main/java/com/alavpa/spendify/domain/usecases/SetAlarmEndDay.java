package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmEndDay;

import javax.inject.Inject;

public class SetAlarmEndDay{

    private
    AlarmManager alarmManager;

    private AlarmEndDay alarmEndDay;

    public void setAlarmEndDay(AlarmEndDay alarmEndDay) {
        this.alarmEndDay = alarmEndDay;
    }

    @Inject
    public SetAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }



    public void execute(){

        alarmEndDay.set(alarmManager);
    }

}
