package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class CancelAlarm extends UseCase<Alarm>{

    private Repository repository;
    private AlarmManager alarmManager;
    private SetAlarm setAlarm;

    private Alarm alarm;

    @Inject
    public CancelAlarm(Repository repository,
                       AlarmManager alarmManager,
                       SetAlarm setAlarm) {
        this.alarmManager = alarmManager;
        this.repository = repository;
        this.setAlarm = setAlarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public Single<Alarm> build(){

        return repository.getAlam(alarm.getAction(),alarm.getRefId())
                .flatMap(new Function<Alarm, SingleSource<Alarm>>() {
                    @Override
                    public SingleSource<Alarm> apply(Alarm alarm) throws Exception {
                        if(alarm.getId()!=0 && alarm.isActive()){
                            alarm.setActive(false);
                            setAlarm.setAlarm(alarm);
                            return setAlarm.build();
                        }
                        return Single.just(new Alarm());
                    }
                })
                .doOnSuccess(new Consumer<Alarm>() {
                    @Override
                    public void accept(Alarm alarm) throws Exception {
                        if(alarm.getId()!=0) {
                            alarmManager.cancelAlarm(alarm.getAction(), alarm.getId());
                        }
                    }
                });
    }

}
