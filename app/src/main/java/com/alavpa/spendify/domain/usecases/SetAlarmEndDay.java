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

                alarmManager.setAlarmEndDay(time);
                return new Object();
            }
        });
    }
}
