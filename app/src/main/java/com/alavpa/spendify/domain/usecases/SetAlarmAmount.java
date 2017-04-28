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

    @Inject
    public SetAlarmAmount(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void execute(){
        AlarmAmount alarmAmount = new AlarmAmount(amount);
        alarmManager.setAlarm(AlarmManager.ACTION_ALARM_AMOUNT,
                alarmAmount.getRequest(),
                alarmAmount.getDate(),
                alarmAmount);
    }
}
