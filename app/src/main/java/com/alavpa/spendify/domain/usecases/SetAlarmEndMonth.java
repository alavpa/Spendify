package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmEndMonth;

import javax.inject.Inject;

public class SetAlarmEndMonth{

    private
    AlarmManager alarmManager;

    private int day;

    @Inject
    public SetAlarmEndMonth(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void execute(){
        AlarmEndMonth alarmEndMonth = new AlarmEndMonth(day);
        execute(alarmEndMonth);
    }

    public void execute(AlarmEndMonth alarmEndMonth){
        alarmManager.setAlarm(AlarmManager.ACTION_ALARM_ENDMONTH,
                AlarmManager.REQUEST_ALARM_ENDMONTH,
                alarmEndMonth.getDate(),
                alarmEndMonth);
    }
}
