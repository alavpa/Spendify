package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertOrUpdateNextAmount extends UseCase<Amount>{

    InsertOrUpdateAmount insertOrUpdateAmount;
    GetAmount getAmount;
    private long id;

    @Inject
    public InsertOrUpdateNextAmount(InsertOrUpdateAmount insertOrUpdateAmount,
                                    GetAmount getAmount){
        this.insertOrUpdateAmount = insertOrUpdateAmount;
        this.getAmount = getAmount;

    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Single<Amount> build() {
        return getAmount.build()
                .flatMap(new Function<Amount, SingleSource<Amount>>() {
                    @Override
                    public SingleSource<Amount> apply(Amount amount) throws Exception {
                        insertOrUpdateAmount.setAmount(amount.getNextAmount());
                        return insertOrUpdateAmount.build();
                    }
                });
    }
}
