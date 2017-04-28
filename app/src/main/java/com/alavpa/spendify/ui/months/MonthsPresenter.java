package com.alavpa.spendify.ui.months;

import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.domain.usecases.GetMonthList;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

@PerActivity
public class MonthsPresenter extends BasePresenter<MonthsView>{

    GetMonthList getMonthList;

    @Inject
    public MonthsPresenter(GetMonthList getMonthList){
        this.getMonthList = getMonthList;
    }
    public void initView(){
        getMonthList.execute(new DisposableSingleObserver<List<Long>>() {
            @Override
            public void onSuccess(List<Long> months) {
                getView().populateMonths(months);
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });
    }
    public void onClickMonth(long month) {
        navigator.openDashboard(month);
    }
}
