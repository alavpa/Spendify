package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetSumBy extends UseCase<Double>{

    long from;
    long to;
    boolean income;

    Repository repository;

    @Inject
    public GetSumBy(Repository repository){
        this.repository = repository;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    @Override
    public Single<Double> build() {
        return repository.getSumBy(income, from,to);
    }
}
