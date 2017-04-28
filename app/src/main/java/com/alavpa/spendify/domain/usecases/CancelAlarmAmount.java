package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.Amount;

import javax.inject.Inject;

public class CancelAlarmAmount {

    private
    AlarmManager alarmManager;

    private Amount amount;

    @Inject
    public CancelAlarmAmount(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void execute(){
        AlarmAmount alarmAmount = new AlarmAmount(amount);
        alarmManager.cancelAlarm(AlarmManager.ACTION_ALARM_AMOUNT, alarmAmount.getRequest());
    }

}
