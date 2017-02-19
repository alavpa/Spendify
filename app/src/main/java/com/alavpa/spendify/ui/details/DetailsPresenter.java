package com.alavpa.spendify.ui.details;


import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.domain.usecases.GetCategories;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by alavpa on 14/02/17.
 */
@PerActivity
public class DetailsPresenter extends BasePresenter<DetailsView> {

    private
    Amount amount;

    private DecimalFormat decimalFormat;
    private SimpleDateFormat simpleDateFormat;

    private
    GetCategories getCategories;

    @Inject
    public DetailsPresenter(GetCategories getCategories){
        super(getCategories);

        this.amount = new Amount();
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setMinimumFractionDigits(2);
        this.decimalFormat.setMaximumFractionDigits(2);
        this.getCategories = getCategories;
    }

    public void showCategories(){

        getCategories.setIncome(amount.isIncome());
        getCategories.execute(new DisposableSingleObserver<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                getView().populateCategories(categories);
            }

            @Override
            public void onError(Throwable e) {

            }
        });

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
        amount.setCategory(getView().selectedCategory());

        Period period = amount.getPeriod();

        if(getView().repeat()){
            period.setTimes(getView().every()+1);
            period.setPeriod(getView().period());
        }
    }

    public void setDate(long date) {
        amount.getPeriod().setDate(date);
    }

    public void showDate(){
        if(simpleDateFormat==null){
            simpleDateFormat = new SimpleDateFormat(resourceProvider.getString(R.string.date_format),
                    Locale.getDefault());
        }

        String dateText = simpleDateFormat.format(amount.getPeriod().getDate());
        getView().showDate(dateText.substring(0,1).toUpperCase()+dateText.substring(1));
    }

    public void initDatePicker() {
        getView().initDatePicker(amount.getPeriod().getDate());
    }
}
