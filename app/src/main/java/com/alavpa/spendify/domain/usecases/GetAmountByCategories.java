package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alavpa on 19/02/17.
 */

public class GetAmountByCategories extends UseCase{

    Repository repository;
    private boolean income;
    private long from;
    private long to;

    @Inject
    public GetAmountByCategories(Repository repository){
        this.repository = repository;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public void execute(DisposableSingleObserver<List<Amount>> disposable){
        repository.getAmountByCategories(income,from,to)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable);

        addDisposable(disposable);
    }
}
