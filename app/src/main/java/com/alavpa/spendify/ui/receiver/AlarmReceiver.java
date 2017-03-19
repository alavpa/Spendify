package com.alavpa.spendify.ui.receiver;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.usecases.SetAlarmEndDay;
import com.alavpa.spendify.domain.usecases.SetAlarmEndMonth;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmReceiver extends WakefulBroadcastReceiver{
    @Inject
    SetAlarmEndDay setAlarmEndDay;

    @Inject
    SetAlarmEndMonth setAlarmEndMonth;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDDAY)){
            long time = intent.getLongExtra(AlarmManager.EXTRA_ALARM_DATA, 0);
            rescheduleAlarmEndDay(time);
        }

        if(intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDMONTH)){
            rescheduleAlarmEndMonth();
        }
    }

    private void rescheduleAlarmEndMonth() {
        setAlarmEndMonth.execute();
    }

    public void rescheduleAlarmEndDay(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.add(Calendar.DATE,1);

        setAlarmEndDay.setTime(calendar);
        setAlarmEndDay.execute();
    }
}
