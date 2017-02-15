package com.alavpa.spendify.ui.base;

/**
 * Created by alavpa on 10/02/17.
 */

public class BasePresenter<T extends BaseView> {

    private
    T view;

    public void attachView(T view){
        this.view = view;
    }

    protected T getView(){
        return view;
    }

    public void detachView(){
        view = null;
    }
}
