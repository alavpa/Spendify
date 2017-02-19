package com.alavpa.spendify.domain.usecases.base;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by alavpa on 19/02/17.
 */

public class UseCase {
    CompositeDisposable disposables;
    public UseCase(){
        disposables = new CompositeDisposable();
    }

    protected void addDisposable(Disposable disposable){
        disposables.add(disposable);
    }

    public void dispose(){
        disposables.dispose();
    }
}
