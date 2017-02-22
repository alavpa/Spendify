package com.alavpa.spendify.ui.main;

import com.alavpa.spendify.domain.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by alavpa on 10/02/17.
 */
@PerActivity
public class MainPresenter extends BasePresenter<MainView> {

    private
    Amount amount;
    private String value;

    @Inject
    public MainPresenter(){
        amount = new Amount();
    }

    public void setAmount(Amount amount){
        this.amount = amount;
    }

    public void initView(){
        getView().setValue(amount.getAmount());
    }

    public void goToDetails() {
        getView().goToDetails(amount);
    }

    public void setValue(double value) {
        amount.setAmount(value);
        getView().setValue(value);
    }

    public void setIncome(boolean income) {
        amount.setIncome(income);
    }
}
