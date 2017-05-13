package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by alavpa on 8/05/17.
 */

public class SetAlarms extends UseCase<List<Alarm>>{

    Repository repository;
    SetAlarm setAlarm;
    GetAlarms getAlarms;

    public SetAlarms(Repository repository, GetAlarms getAlarms,SetAlarm setAlarm) {
        this.repository = repository;
        this.setAlarm = setAlarm;
        this.getAlarms = getAlarms;
    }

    @Override
    public Single<List<Alarm>> build() {
        return getAlarms.build()
                .flatMap(new Function<List<Alarm>, SingleSource<List<Alarm>>>() {
                    @Override
                    public SingleSource<List<Alarm>> apply(List<Alarm> alarms) throws Exception {
                        return Observable.fromIterable(alarms)
                                .flatMap(new Function<Alarm, ObservableSource<Alarm>>() {
                                    @Override
                                    public ObservableSource<Alarm> apply(Alarm alarm) throws Exception {
                                        setAlarm.setAlarm(alarm);
                                        return setAlarm.build()
                                                .toObservable();
                                    }
                                })
                                .toList();
                    }
                });
    }
}
