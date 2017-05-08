package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.Alarm;

import java.util.List;

/**
 * Created by alavpa on 8/05/17.
 */

public class SetAlarms {

    private AlarmManager alarmManager;
    private List<Alarm> alarms;

    public SetAlarms(AlarmManager alarmManager) {
        this.alarmManager = alarmManager;
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

    public void execute() {
        for (Alarm alarm : alarms) {
            alarmManager.setAlarm(AlarmManager.ACTION_ALARM_AMOUNT, alarm);
        }
    }
}
