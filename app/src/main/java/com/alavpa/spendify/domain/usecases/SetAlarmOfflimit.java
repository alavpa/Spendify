package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Category;

import javax.inject.Inject;

public class SetAlarmOfflimit {

    private AlarmManager alarmManager;
    private Category category;

    @Inject
    public SetAlarmOfflimit(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void execute(){
        AlarmOfflimit alarmOfflimit = new AlarmOfflimit(category);
        alarmManager.setAlarm(AlarmManager.ACTION_ALARM_OFFLIMIT,
                alarmOfflimit.getRequest(),
                alarmOfflimit.getDate(),
                alarmOfflimit);
    }
}
