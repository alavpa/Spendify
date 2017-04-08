package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.Amount;

import javax.inject.Inject;

public class SetAlarmAmount {

    private
    AlarmManager alarmManager;

    private
    Amount amount;

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Inject
    public SetAlarmAmount(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        AlarmAmount alarmAmount = new AlarmAmount(amount);
        alarmAmount.set(alarmManager);
    }
}
