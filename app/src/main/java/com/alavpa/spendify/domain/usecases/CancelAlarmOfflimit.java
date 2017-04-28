package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Category;

import javax.inject.Inject;

public class CancelAlarmOfflimit {

    private
    AlarmManager alarmManager;

    private Category category;

    @Inject
    public CancelAlarmOfflimit(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void execute(){
        AlarmOfflimit alarmOfflimit = new AlarmOfflimit(category);
        alarmManager.cancelAlarm(AlarmManager.ACTION_ALARM_OFFLIMIT,
                alarmOfflimit.getRequest());
    }

}
