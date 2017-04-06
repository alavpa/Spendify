package com.alavpa.spendify.ui.details;


import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.domain.usecases.DeleteAmount;
import com.alavpa.spendify.domain.usecases.GetCategories;
import com.alavpa.spendify.domain.usecases.InsertOrUpdateAmount;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

import static android.app.Activity.RESULT_OK;

/**
 * Created by alavpa on 14/02/17.
 */
@PerActivity
public class DetailsPresenter extends BasePresenter<DetailsView> {

    private
    Amount amount;

    @Inject
    public DecimalFormat decimalFormat;

    @Inject
    public SimpleDateFormat simpleDateFormat;

    private
    GetCategories getCategories;

    private
    InsertOrUpdateAmount insertOrUpdateAmount;

    private DeleteAmount deleteAmount;

    @Inject
    public DetailsPresenter(GetCategories getCategories,
                            InsertOrUpdateAmount insertOrUpdateAmount,
                            DeleteAmount deleteAmount){

        this.amount = new Amount();

        this.getCategories = getCategories;
        this.insertOrUpdateAmount = insertOrUpdateAmount;
        this.deleteAmount = deleteAmount;

        addUseCases(getCategories, insertOrUpdateAmount, deleteAmount);
    }

    public void initView(){

        getView().showAmount(this.amount.getAmount());

        getView().setDescription(amount.getDescription());

        if(amount.getPeriod().getPeriod() == Period.NO_PERIOD){
            getView().setRepeat(false);
        }else{
            getView().setRepeat(true);
            getView().setPeriod(amount.getPeriod());
        }

        showDate();

        getCategories.setIncome(amount.isIncome());
        getCategories.execute(new DisposableSingleObserver<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                getView().populateCategories(categories,resources.getCategoryBackgroundsArray());
                getView().selectCategory(amount.getCategory());
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

        getView().setDeletable(amount.getId()>0);
    }

    public void showCategories(){

        getCategories.setIncome(amount.isIncome());
        getCategories.execute(new DisposableSingleObserver<List<Category>>() {
            @Override
            public void onSuccess(List<Category> categories) {
                getView().populateCategories(categories,resources.getCategoryBackgroundsArray());
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

    }

    public void setAmount(Amount amount){
        this.amount = amount;
    }

    public void send() {

        fillAmountFromView();

        insertOrUpdateAmount.setAmount(amount);
        insertOrUpdateAmount.execute(new DisposableSingleObserver<Amount>() {
            @Override
            public void onSuccess(Amount amount) {
                getView().setResult(RESULT_OK);
                getView().finish();
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });
    }

    public void setDate(long date) {
        Period period = getView().period();
        period.setDate(date);
        amount.setPeriod(period);
    }

    public void showDate(){

        String dateText = simpleDateFormat.format(amount.getPeriod().getDate());
        getView().setDate(dateText.substring(0,1).toUpperCase()+dateText.substring(1));
    }

    public void fillAmountFromView(){
        amount.setAmount(getView().amount());
        amount.setDescription(getView().description());
        amount.setCategory(getView().category());

        if(getView().repeat()){
            amount.setPeriod(getView().period());
        }
    }

    public void showDatePickerDialog() {
        getView().showDatePickerDialog(amount.getPeriod().getDate());
    }

    public void goToAddCategory() {

        Category category = new Category();
        category.setIncome(amount.isIncome());
        navigator.openAddCategory(category);
    }

    public void deleteAmount() {

        deleteAmount.setAmount(amount);
        deleteAmount.execute(new DisposableSingleObserver<Boolean>() {
            @Override
            public void onSuccess(Boolean success) {
                if(success){
                    getView().finish();
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });
    }
}
