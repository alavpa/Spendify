package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.Category;

import javax.inject.Inject;

public class CancelAlarmOfflimit {

    AlarmManager alarmManager;
    Category category;

    public void setCategory(Category category) {
        this.category = category;
    }

    @Inject
    public CancelAlarmOfflimit(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void execute(){
        alarmManager.cancelAlarmOfflimit(category);
    }

}
