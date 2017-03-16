package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertAmount extends UseCase<Amount>{

    Repository repository;
    private Amount amount;

    @Inject
    public InsertAmount(Repository repository){
        this.repository = repository;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public Single<Amount> build() {
        return repository.insertAmount(amount);
    }

}
