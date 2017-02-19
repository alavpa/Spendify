package com.alavpa.spendify.ui.details;


import com.alavpa.spendify.domain.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alavpa on 14/02/17.
 */
@PerActivity
public class DetailsPresenter extends BasePresenter<DetailsView> {

    public List<Category> categories;
    private
    Amount amount;

    private DecimalFormat decimalFormat;

    @Inject
    public DetailsPresenter(){
        categories = new ArrayList<>();
        categories.add(new Category("asdsad",false));
        categories.add(new Category("bsdsad",false));
        categories.add(new Category("csdsad",false));
        categories.add(new Category("dsdsad",false));
        categories.add(new Category("esdsad",false));
        categories.add(new Category("fsdsad",false));
        categories.add(new Category("gsdsad",false));
        categories.add(new Category("hsdsad",false));

        amount = new Amount();
        decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
    }

    public void showCategories(){
        getView().populateCategories(categories);
    }

    public void setAmount(String amount){
        try {
            this.amount.setAmount(decimalFormat.parse(amount).doubleValue());
        }catch (Exception e){
            getView().showError(e.getMessage());
        }

    }

    public void setIsIncome(boolean isIncome){
        this.amount.setIncome(isIncome);
    }

    public void showAmount() {

        String amount = decimalFormat.format(this.amount.getAmount());
        getView().setAmount(amount);
    }

    public void send() {
        amount.setDescription(getView().getDescription());
        amount.getCategories().add(getView().selectedCategory());

        Period period = new Period();
        period.setDate(getView().getAmountDate());

        if(getView().repeat()){
            period.setTimes(getView().every()+1);
            period.setPeriod(getView().period());
        }

        amount.setPeriod(period);
    }
}
