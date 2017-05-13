package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class SetAlarm extends UseCase<Alarm> {

    Repository repository;
    private AlarmManager alarmManager;

    private Alarm alarm;

    @Inject
    public SetAlarm(Repository repository, AlarmManager alarmManager){
        this.alarmManager = alarmManager;
        this.repository = repository;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    public Single<Alarm> build(){

        return repository.getAlam(alarm.getAction(),alarm.getRefId())
                .flatMap(new Function<Alarm, SingleSource<? extends Alarm>>() {
                    @Override
                    public SingleSource<? extends Alarm> apply(Alarm localAlarm) throws Exception {
                        if(localAlarm.getId()==0){
                            return repository.insertAlarm(alarm);
                        }else{
                            alarm.setId(localAlarm.getId());
                            return repository.updateAlarm(alarm);
                        }
                    }
                })
                .doOnSuccess(new Consumer<Alarm>() {
                    @Override
                    public void accept(Alarm alarm) throws Exception {
                        alarmManager.setAlarm(alarm.getAction(),
                                alarm.getId(),
                                alarm.getPeriod().getDate(),
                                alarm);
                    }
                });

    }

}
