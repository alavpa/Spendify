package com.alavpa.spendify.ui.init;

import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class StartPresenter extends BasePresenter<StartView> {

    @Inject
    public StartPresenter(){

    }

    public void initView() {
        if(preferences.isInitialized()){
            navigator.openMain();
        }else{
            navigator.openSelectDay();
        }
    }
}
