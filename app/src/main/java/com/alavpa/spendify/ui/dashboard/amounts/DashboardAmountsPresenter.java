package com.alavpa.spendify.ui.dashboard.amounts;

import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.domain.usecases.GetAmountsByCategory;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;

public class DashboardAmountsPresenter extends BasePresenter<DashboardAmountsView>{

    @Inject
    DateUtils dateUtils;

    @Inject
    public DecimalFormat decimalFormat;

    private
    GetAmountsByCategory getAmountsByCategory;

    private
    Sector sector;


    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();

    public void setSector(Sector sector) {
        this.sector = sector;
    }

    @Inject
    public DashboardAmountsPresenter(GetAmountsByCategory getAmountsByCategory){
        this.getAmountsByCategory = getAmountsByCategory;
        addUseCases(getAmountsByCategory);

    }

    public void initView() {

        int color = resources.getCategoryColorsArray()[sector.getCategory().getColor()];
        getView().showCategoryColor(color);
        getView().showCategoryName(sector.getCategory().getName());
        getView().showCategoryAmount(decimalFormat.format(sector.getAmount()));

        getAmountsByCategory.setFrom(from.getTimeInMillis());
        getAmountsByCategory.setTo(to.getTimeInMillis());
        getAmountsByCategory.setCategory(sector.getCategory());

        getAmountsByCategory.execute(new DisposableSingleObserver<List<Amount>>() {
            @Override
            public void onSuccess(List<Amount> amounts) {
                getView().populateDetails(amounts);
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });
    }

    public void setFrom(long from) {
        this.from=dateUtils.calculateFrom(from,preferences.getMonthDay());
        this.to=dateUtils.calculateTo(from,preferences.getMonthDay());
    }
}
