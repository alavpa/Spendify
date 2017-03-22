package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.Amount;

import javax.inject.Inject;

public class CancelAlarmAmount {

    AlarmManager alarmManager;

    private Amount amount;

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Inject
    public CancelAlarmAmount(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.cancelAlarmAmount(amount);
    }

}
