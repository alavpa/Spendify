package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.OfflimitAlarm;

import javax.inject.Inject;

public class SetAlarmOfflimit {

    AlarmManager alarmManager;

    @Inject
    public SetAlarmOfflimit(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        new OfflimitAlarm()
                .set(alarmManager);
    }
}
