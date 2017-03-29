package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.AlarmAmount;

import javax.inject.Inject;

public class CancelAlarmAmount {

    private
    AlarmManager alarmManager;

    private Amount amount;

    private AlarmAmount alarmAmount;

    public void setAlarmAmount(AlarmAmount alarmAmount) {
        this.alarmAmount = alarmAmount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Inject
    public CancelAlarmAmount(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmAmount.cancel(alarmManager);
    }

}
