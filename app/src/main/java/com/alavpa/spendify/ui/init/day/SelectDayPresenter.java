package com.alavpa.spendify.ui.init.day;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

@PerActivity
public class SelectDayPresenter extends BasePresenter<SelectDayView> {

    @Inject
    public SelectDayPresenter(){

    }

    public void initView() {
        int day = preferences.getMonthDay();
        getView().setDay(day);
    }

    public void next(double value) {
        int day = Double.valueOf(value).intValue();
        preferences.setInitialized(true);
        preferences.setMonthDay(day);
    }

    public void goToUp(){
        navigator.goToUp();
    }

    public void goToNext(){
        navigator.openStartCategories();
    }
}
