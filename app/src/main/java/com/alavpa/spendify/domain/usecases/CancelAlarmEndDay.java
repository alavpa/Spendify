package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmEndDay;

import java.util.Calendar;

import javax.inject.Inject;

public class CancelAlarmEndDay{

    private
    AlarmManager alarmManager;

    @Inject
    public CancelAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        AlarmEndDay alarmEndDay = new AlarmEndDay(Calendar.getInstance().getTimeInMillis());
        alarmEndDay.cancel(alarmManager);
    }
}
