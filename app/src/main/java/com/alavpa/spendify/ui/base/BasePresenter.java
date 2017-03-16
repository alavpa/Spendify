package com.alavpa.spendify.ui.base;

import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alavpa on 10/02/17.
 */

public class BasePresenter<T extends BaseView> {

    private
    T view;

    private
    List<UseCase> useCases;

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
