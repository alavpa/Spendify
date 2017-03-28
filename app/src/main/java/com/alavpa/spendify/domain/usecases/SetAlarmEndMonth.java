package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.model.EndDayMonthAlarm;

import javax.inject.Inject;

public class SetAlarmEndMonth{

    AlarmManager alarmManager;
    PrefsDatasource preferences;


    @Inject
    public SetAlarmEndMonth(AlarmManager alarmManager, PrefsDatasource preferences){
        this.alarmManager = alarmManager;
        this.preferences = preferences;
    }

    public void execute(){
        new EndDayMonthAlarm(preferences.getMonthDay()).set(alarmManager);
    }
}
