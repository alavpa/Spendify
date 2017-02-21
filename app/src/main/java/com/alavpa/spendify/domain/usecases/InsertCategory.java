package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertCategory extends UseCase{

    Repository repository;
    private Category category;

    @Inject
    public InsertCategory(Repository repository){
        this.repository = repository;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void execute(DisposableSingleObserver<Category> disposable){
        repository.insertCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable);

        addDisposable(disposable);
    }
}
