package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by alavpa on 19/02/17.
 */

public class DeleteAmount extends UseCase<Boolean>{

    private
    Repository repository;

    private Amount amount;

    @Inject
    public DeleteAmount(Repository repository){
        this.repository = repository;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    @Override
    public Single<Boolean> build() {

        amount.setDeleted(true);
        return repository.updateAmount(amount)
                .map(new Function<Amount, Boolean>() {
                    @Override
                    public Boolean apply(Amount amount) throws Exception {
                        return amount.isDeleted();
                    }
                });

    }
}
