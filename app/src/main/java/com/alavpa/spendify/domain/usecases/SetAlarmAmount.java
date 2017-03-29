package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.model.AlarmAmount;

import javax.inject.Inject;

public class SetAlarmAmount {

    private
    AlarmManager alarmManager;

    private
    AlarmAmount alarmAmount;

    public void setAlarmAmount(AlarmAmount alarmAmount) {
        this.alarmAmount = alarmAmount;
    }

    @Inject
    public SetAlarmAmount(AlarmManager alarmManager, PrefsDatasource preferences){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmAmount.set(alarmManager);
    }
}
