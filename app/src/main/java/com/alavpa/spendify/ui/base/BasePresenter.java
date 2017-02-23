package com.alavpa.spendify.ui.base;

import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

/**
 * Created by alavpa on 10/02/17.
 */

public class BasePresenter<T extends BaseView> {

    private
    T view;

    @Inject
    public ResDatasource resourceProvider;

    private
    UseCase[] useCases;

    public BasePresenter(UseCase... useCases){
        this.useCases = useCases;
    }

    public void attachView(T view){
        this.view = view;
    }

    protected T getView(){
        return view;
    }

    public void detachView(){
        view = null;
    }

    public void dispose(){
        for(UseCase useCase : useCases){
            useCase.dispose();
        }
        detachView();
    }
}
