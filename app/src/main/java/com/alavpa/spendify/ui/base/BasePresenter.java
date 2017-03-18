package com.alavpa.spendify.ui.base;

import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.usecases.base.UseCase;
import com.alavpa.spendify.ui.Navigator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alavpa on 10/02/17.
 */

@PerActivity
public class BasePresenter<T extends BaseView> {

    @Inject
    protected ResDatasource resources;

    @Inject
    protected Navigator navigator;

    private
    T view;

    private
    List<UseCase> useCases;

    @Inject
    public BasePresenter(UseCase... useCases){
        this.useCases = new ArrayList<>(Arrays.asList(useCases));
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

    public void addUseCase(UseCase useCase){
        useCases.add(useCase);
    }

    public void dispose(){
        for(UseCase useCase : useCases){
            useCase.dispose();
        }
        detachView();
    }
}
