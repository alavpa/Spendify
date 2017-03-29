package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmOfflimit;

import javax.inject.Inject;

public class CancelAlarmOfflimit {

    private
    AlarmManager alarmManager;

    private AlarmOfflimit alarmOfflimit;

    public void setAlarmOfflimit(AlarmOfflimit alarmOfflimit) {
        this.alarmOfflimit = alarmOfflimit;
    }

    @Inject
    public CancelAlarmOfflimit(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmOfflimit.cancel(alarmManager);
    }

}
