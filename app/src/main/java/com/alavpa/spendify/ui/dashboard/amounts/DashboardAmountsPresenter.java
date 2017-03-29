package com.alavpa.spendify.ui.dashboard.amounts;

import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.domain.usecases.GetAmountsByCategory;
import com.alavpa.spendify.domain.usecases.GetSector;
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

    private GetSector getSector;

    private Category category;


    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();

    public void setCategory(Category category) {
        this.category = category;
    }

    @Inject
    public DashboardAmountsPresenter(GetAmountsByCategory getAmountsByCategory, GetSector getSector){
        this.getAmountsByCategory = getAmountsByCategory;
        this.getSector = getSector;
        addUseCases(getAmountsByCategory, getSector);

    }

    public void initView() {

        getSector.setCategory(category);
        getSector.setFrom(from.getTimeInMillis());
        getSector.setTo(to.getTimeInMillis());
        getSector.execute(new DisposableSingleObserver<Sector>() {
            @Override
            public void onSuccess(Sector sector) {
                List<Integer> colors = resources.getCategoryColorsArray();
                int color = colors.get(sector.getCategory().getColor());
                getView().showCategoryColor(color);
                getView().showCategoryName(sector.getCategory().getName());
                getView().showCategoryAmount(decimalFormat.format(sector.getAmount()));
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

        getAmountsByCategory.setFrom(from.getTimeInMillis());
        getAmountsByCategory.setTo(to.getTimeInMillis());
        getAmountsByCategory.setCategory(category);

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

    public void onClickAmount(Amount amount) {
        navigator.openDetails(amount);
    }
}
