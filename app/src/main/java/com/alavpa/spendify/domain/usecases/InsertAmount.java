package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertAmount extends UseCase{

    Repository repository;
    private Amount amount;

    @Inject
    public InsertAmount(Repository repository){
        this.repository = repository;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public void execute(DisposableSingleObserver<Amount> disposable){
        repository.insertAmount(amount)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable);

        addDisposable(disposable);
    }
}
