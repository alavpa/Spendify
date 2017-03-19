package com.alavpa.spendify.domain.usecases.base;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by alavpa on 19/02/17.
 */

public abstract class UseCase<T> {
    CompositeDisposable disposables;
    public UseCase(){
        disposables = new CompositeDisposable();
    }

    public abstract Single<T> build();

    public void execute(DisposableSingleObserver<T> disposable){
        build()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposable);

        addDisposable(disposable);
    }

    protected void addDisposable(Disposable disposable){
        disposables.add(disposable);
    }

    public void dispose(){
        disposables.clear();
    }
}
