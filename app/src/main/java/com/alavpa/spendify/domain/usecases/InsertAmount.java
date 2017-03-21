package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertAmount extends UseCase<Amount>{

    AlarmManager alarmManager;
    Repository repository;
    private Amount amount;

    @Inject
    public InsertAmount(Repository repository, AlarmManager alarmManager){
        this.repository = repository;
        this.alarmManager = alarmManager;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public Single<Amount> build() {
        return repository.insertAmount(amount)
                .doOnSuccess(new Consumer<Amount>() {
                    @Override
                    public void accept(Amount amount) throws Exception {
                        alarmManager.setAlarmAmount(amount);
                    }
                });
    }

}
