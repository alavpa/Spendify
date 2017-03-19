package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.Calendar;
import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;

public class SetAlarmEndMonth extends UseCase<Object>{

    AlarmManager alarmManager;
    PrefsDatasource preferences;


    @Inject
    public SetAlarmEndMonth(AlarmManager alarmManager, PrefsDatasource preferences){
        this.alarmManager = alarmManager;
        this.preferences = preferences;
    }

    @Override
    public Single<Object> build() {
        return Single.fromCallable(new Callable<Object>() {
            @Override
            public Object call() throws Exception {

                Calendar alarm = Calendar.getInstance();
                alarm.set(Calendar.DATE,preferences.getMonthDay());
                alarm.set(Calendar.DATE,-1);

                if(alarm.getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){
                    alarm.add(Calendar.MONTH,1);
                }

                alarmManager.setAlarmEndMonth(alarm);
                return new Object();
            }
        });
    }
}
