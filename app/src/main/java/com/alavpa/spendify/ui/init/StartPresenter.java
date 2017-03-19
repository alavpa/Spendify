package com.alavpa.spendify.ui.init;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class StartPresenter extends BasePresenter<StartView> {

    @Inject
    public StartPresenter(){

    }

    public void initView() {
        navigator.openSelectDay();
//        if(preferences.isInitialized()){
//            navigator.openMain(new Amount());
//        }else{
//            navigator.openSelectDay();
//        }


    }
}
