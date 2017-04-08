package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;

/**
 * Created by alavpa on 19/02/17.
 */

public class RestoreRepeatAlarms extends UseCase<List<Amount>> {

    private AlarmManager alarmManager;
    private Repository repository;
    private long currentTime;

    private SetAlarmAmount setAlarmAmount;

    @Inject
    public RestoreRepeatAlarms(Repository repository, AlarmManager alarmManager) {
        this.repository = repository;
        this.alarmManager = alarmManager;
    }

    public void setCurrentTime(long currentTime) {
        this.currentTime = currentTime;
    }

    public void setSetAlarmAmount(SetAlarmAmount setAlarmAmount) {
        this.setAlarmAmount = setAlarmAmount;
    }

    @Override
    public Single<List<Amount>> build() {

        return repository.getRepeatAmounts()
                .flatMap(new Function<List<Amount>, SingleSource<List<Amount>>>() {
                    @Override
                    public SingleSource<List<Amount>> apply(List<Amount> amounts) throws Exception {

                        return Observable.fromIterable(amounts)
                                .filter(new Predicate<Amount>() {
                                    @Override
                                    public boolean test(Amount amount) throws Exception {
                                        return amount.getPeriod().getNextDateInMillis() > currentTime;
                                    }
                                })
                                .flatMap(new Function<Amount, ObservableSource<Amount>>() {
                                    @Override
                                    public ObservableSource<Amount> apply(Amount amount) throws Exception {
                                        setAlarmAmount.setAmount(amount);
                                        setAlarmAmount.execute();
                                        return Observable.just(amount);
                                    }
                                })
                                .toList();

                    }
                });
    }


}
