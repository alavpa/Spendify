package com.alavpa.spendify.ui.details;


import com.alavpa.spendify.R;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.domain.usecases.GetCategories;
import com.alavpa.spendify.domain.usecases.InsertAmount;
import com.alavpa.spendify.domain.usecases.InsertCategory;
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

    private
    InsertAmount insertAmount;

    private
    InsertCategory insertCategory;

    private ResDatasource resDatasource;

    @Inject
    public DetailsPresenter(ResDatasource resDatasource,
                            GetCategories getCategories,
                            InsertAmount insertAmount,
                            InsertCategory insertCategory){

        super(getCategories, insertAmount, insertCategory);

        this.resDatasource = resDatasource;
        this.amount = new Amount();
        this.decimalFormat = new DecimalFormat();
        this.decimalFormat.setMinimumFractionDigits(2);
        this.decimalFormat.setMaximumFractionDigits(2);

        this.getCategories = getCategories;
        this.insertAmount = insertAmount;
        this.insertCategory = insertCategory;
    }

    public void initView(){

        String value = decimalFormat.format(this.amount.getAmount());
        getView().setAmount(value);

        getView().setDescription(amount.getDescription());

        if(amount.getPeriod().getPeriod() == Period.NO_PERIOD){
            getView().setRepeat(false);
        }else{
            getView().setRepeat(true);
            getView().setPeriod(amount.getPeriod().getPeriod());
        }

        showDate();

        getCategories.setIncome(amount.isIncome());
        getCategories.execute(new DisposableSingleObserver<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                getView().populateCategories(categories);
                getView().selectCategory(amount.getCategory());
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void setPeriod(int period){
        int times;
        if(period == amount.getPeriod().getPeriod()){
            times = amount.getPeriod().getTimes();
        }else{
            times = 1;
        }
        amount.getPeriod().setPeriod(period);
        amount.getPeriod().setTimes(times);
        getView().setTimes(times-1);
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

    public void setAmount(Amount amount){
        this.amount = amount;
    }

    public void send() {

        fillAmountFromView();

        insertAmount.setAmount(amount);
        insertAmount.execute(new DisposableSingleObserver<Amount>() {
            @Override
            public void onSuccess(Amount amount) {
                getView().goToMain(new Amount());
                getView().finish();
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void setDate(long date) {
        amount.getPeriod().setDate(date);
    }

    public void showDate(){
        if(simpleDateFormat==null){
            simpleDateFormat = new SimpleDateFormat(resDatasource.getString(R.string.date_format),
                    Locale.getDefault());
        }

        String dateText = simpleDateFormat.format(amount.getPeriod().getDate());
        getView().setDate(dateText.substring(0,1).toUpperCase()+dateText.substring(1));
    }

    public void addCategory(Category category) {

        insertCategory.setCategory(category);

        insertCategory.execute(new DisposableSingleObserver<Category>() {
            @Override
            public void onSuccess(Category category) {
                showCategories();
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }

    public void fillAmountFromView(){
        amount.setDescription(getView().description());
        amount.setCategory(getView().category());

        Period period = amount.getPeriod();

        if(getView().repeat()){
            period.setTimes(getView().every()+1);
            period.setPeriod(getView().period());
        }
    }
    public void goToMain() {
        fillAmountFromView();
        getView().goToMain(amount);
    }

    public void showDatePickerDialog() {
        getView().showDatePickerDialog(amount.getPeriod().getDate());
    }

    public void goToAddCategory() {
        getView().goToAddCategory(amount.isIncome());
    }
}
