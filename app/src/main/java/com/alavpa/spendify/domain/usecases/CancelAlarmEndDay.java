package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

public class CancelAlarmEndDay extends UseCase<Object>{

    AlarmManager alarmManager;

    @Inject
    public CancelAlarmEndDay(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }

    @Override
    public Single<Object> build() {
        return Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                alarmManager.cancelAlarmEndDay();
                return new Object();
            }
        });
    }
}
