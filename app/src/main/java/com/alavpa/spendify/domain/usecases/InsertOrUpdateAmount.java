package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.io.Serializable;

import javax.inject.Inject;

import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableSingleMaybe;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertOrUpdateAmount extends UseCase<Amount>{

    private PrefsDatasource prefsDatasource;
    private DateUtils dateUtils;
    private Repository repository;
    private Amount amount;
    private SetAlarm setAlarmAmount;
    private SetAlarm setAlarmOfflimit;
    private CancelAlarm cancelAlarmAmount;

    @Inject
    public InsertOrUpdateAmount(PrefsDatasource prefsDatasource,
                                DateUtils dateUtils,
                                Repository repository,
                                SetAlarm setAlarmAmount,
                                SetAlarm setAlarmOfflimit,
                                CancelAlarm cancelAlarmAmount) {

        this.prefsDatasource = prefsDatasource;
        this.dateUtils = dateUtils;
        this.repository = repository;
        this.setAlarmAmount = setAlarmAmount;
        this.setAlarmOfflimit = setAlarmOfflimit;
        this.cancelAlarmAmount = cancelAlarmAmount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public Single<Amount> build() {
        int day = prefsDatasource.getMonthDay();
        long from = dateUtils.calculateFrom(amount.getPeriod().getDate(),day).getTimeInMillis();
        long to = dateUtils.calculateTo(amount.getPeriod().getDate(),day).getTimeInMillis();

        if(amount.getId()>0){
            return update(from,to);
        }else {
            return insert(from,to);
        }

    }

    private Single<Amount> insert(final long from, final long to){
        return repository.insertAmount(amount)
                .flatMap(new Function<Amount, SingleSource<Amount>>() {
                    @Override
                    public SingleSource<Amount> apply(Amount amount) throws Exception {
                        return setAlarms(amount, from, to);
                    }
                });
    }

    private Single<Amount> update(final long from, final long to){
        return repository.updateAmount(amount)
                .flatMap(new Function<Amount, SingleSource<Amount>>() {
                    @Override
                    public SingleSource<Amount> apply(Amount amount) throws Exception {
                        return setAlarms(amount, from, to);
                    }
                });

    }

    private Single<Amount> setAlarms(final Amount amount, long from, long to){
        InsertOrUpdateAmount.this.amount = amount;

        return Single.concat(
                setAmountAlarm(),
                setOfflimitAlarm(from, to))
                .last(new Alarm())
                .map(new Function<Alarm, Amount>() {
                    @Override
                    public Amount apply(Alarm alarm) throws Exception {
                        return amount;
                    }
                });
    }

    private Single<Alarm> setOfflimitAlarm(long from, long to){

        return repository.getSumByCategory(amount.getCategory(),from,to)
                .flatMap(new Function<Double, Single<Alarm>>() {
                    @Override
                    public Single<Alarm> apply(Double sum) throws Exception {
                        if(prefsDatasource.notifyOfflimit() && amount.getCategory().isOverLimit(sum)) {
                            return setAlarmOfflimit(amount.getCategory());
                        }

                        return Single.just(new Alarm());
                    }
                });
    }

    private Single<Alarm> setAmountAlarm(){
        if(amount.getPeriod().getPeriod()== Period.NO_PERIOD ||
                amount.isDeleted()){
            return cancelAlarmAmount(amount);
        }else{
            return setAlarmAmount(amount);
        }
    }

    private Single<Alarm> setAlarmOfflimit(Category category) {
        Alarm alarmOfflimit = new AlarmOfflimit(category);
        setAlarmOfflimit.setAlarm(alarmOfflimit);
        return setAlarmOfflimit.build();
    }

    private Single<Alarm> setAlarmAmount(Amount amount) {
        AlarmAmount alarmAmount = new AlarmAmount(amount);
        setAlarmAmount.setAlarm(alarmAmount);
        return setAlarmAmount.build();
    }

    private Single<Alarm> cancelAlarmAmount(Amount amount) {
        AlarmAmount alarmAmount = new AlarmAmount(amount);
        cancelAlarmAmount.setAlarm(alarmAmount);
        return cancelAlarmAmount.build();
    }
}
