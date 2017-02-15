package com.alavpa.spendify.ui.main;

import com.alavpa.spendify.domain.di.PerActivity;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by alavpa on 10/02/17.
 */
@PerActivity
public class MainPresenter extends BasePresenter<MainView> {

    @Inject
    public MainPresenter(){}
    public void showValue(String value) {
        getView().setAmount(value);
    }
}
