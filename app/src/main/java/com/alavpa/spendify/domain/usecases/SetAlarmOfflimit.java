package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.model.Amount;

import javax.inject.Inject;

public class SetAlarmOfflimit {

    AlarmManager alarmManager;
    Amount amount;

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Inject
    public SetAlarmOfflimit(AlarmManager alarmManager, PrefsDatasource preferences){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.setAlarmAmount(amount);
    }
}
