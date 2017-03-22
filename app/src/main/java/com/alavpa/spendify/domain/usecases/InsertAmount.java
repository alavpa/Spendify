package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertAmount extends UseCase<Amount>{

    AlarmManager alarmManager;
    PrefsDatasource prefsDatasource;
    DateUtils dateUtils;
    Repository repository;
    private Amount amount;

    @Inject
    public InsertAmount(Repository repository,
                        AlarmManager alarmManager,
                        PrefsDatasource prefsDatasource,
                        DateUtils dateUtils){
        this.repository = repository;
        this.alarmManager = alarmManager;
        this.prefsDatasource = prefsDatasource;
        this.dateUtils = dateUtils;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public Single<Amount> build() {
        int day = prefsDatasource.getMonthDay();
        long from = dateUtils.calculateFrom(amount.getPeriod().getDate(),day).getTimeInMillis();
        long to = dateUtils.calculateTo(amount.getPeriod().getDate(),day).getTimeInMillis();
        return Single.zip(repository.insertAmount(amount),
                repository.getSumByCategory(amount.getCategory(), from, to),
                new BiFunction<Amount, Double, Amount>() {
                    @Override
                    public Amount apply(Amount amount, Double sum) throws Exception {
                        if(amount.getPeriod().getPeriod()== Period.NO_PERIOD){
                            alarmManager.cancelAlarmAmount(amount);
                        }else{
                            alarmManager.setAlarmAmount(amount);
                        }

                        if(prefsDatasource.notifyOfflimit()){
                            if(amount.getCategory().getLimit()>sum){
                                alarmManager.setAlarmOfflimit(amount.getCategory());
                            }
                        }

                        return amount;
                    }
                });
    }

}
