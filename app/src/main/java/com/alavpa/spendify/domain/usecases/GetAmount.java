package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public class GetAmount extends UseCase<Amount>{

    Repository repository;
    private long id;

    @Inject
    public GetAmount(Repository repository){
        this.repository = repository;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Single<Amount> build() {
        return repository.getAmount(id);
    }
}
