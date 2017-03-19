package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.Calendar;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

public class SetAlarmEndDay extends UseCase<Object>{

    AlarmManager alarmManager;

    Calendar time;

    @Inject
    public SetAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    @Override
    public Single<Object> build() {
        return Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                Calendar alarm = Calendar.getInstance();

                alarm.set(Calendar.HOUR_OF_DAY,time.get(Calendar.HOUR_OF_DAY));
                alarm.set(Calendar.MINUTE,time.get(Calendar.MINUTE));
                alarm.set(Calendar.SECOND,0);

                if(alarm.getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){
                    alarm.add(Calendar.DATE,1);
                }

                alarmManager.setAlarmEndDay(alarm);
                return new Object();
            }
        });
    }
}
