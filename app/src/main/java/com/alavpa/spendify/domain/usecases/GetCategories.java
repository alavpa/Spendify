package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alavpa on 19/02/17.
 */

public class GetCategories extends UseCase{

    Repository repository;
    private boolean income;

    @Inject
    public GetCategories(Repository repository){
        this.repository = repository;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public void execute(DisposableSingleObserver<List<Category>> disposable){
        repository.getCategories(income)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable);

        addDisposable(disposable);
    }
}
