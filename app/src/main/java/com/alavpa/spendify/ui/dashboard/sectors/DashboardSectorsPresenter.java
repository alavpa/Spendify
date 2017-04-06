package com.alavpa.spendify.ui.dashboard.sectors;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.domain.usecases.GetSectorsBy;
import com.alavpa.spendify.domain.usecases.GetSumBy;
import com.alavpa.spendify.domain.usecases.base.UseCase;
import com.alavpa.spendify.ui.base.BasePresenter;
import com.alavpa.spendify.ui.model.AmountBarPart;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;

@PerActivity
class DashboardSectorsPresenter extends BasePresenter<DashboardSectorsView> {

    @Inject
    public DateUtils dateUtils;

    @Inject
    public DecimalFormat decimalFormat;

    private GetSumBy getSumBy;

    private GetSectorsBy getSectorsBy;

    private GetSumBy getSumByAmount;

    private
    boolean income;

    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();


    @Inject
    public DashboardSectorsPresenter(GetSumBy getSumBy, GetSectorsBy getSectorsBy, GetSumBy getSumByAmount) {
        this.getSumBy = getSumBy;
        this.getSectorsBy = getSectorsBy;
        this.getSumByAmount = getSumByAmount;

        addUseCases(getSumBy, getSectorsBy, getSumByAmount);
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public void initView() {

        if (income) {
            getView().showTitle(resources.getString(R.string.income));
        } else {
            getView().showTitle(resources.getString(R.string.outcome));
        }

        getSumByAmount.setIncome(income);
        getSumByAmount.setFrom(from.getTimeInMillis());
        getSumByAmount.setTo(to.getTimeInMillis());

        getSumByAmount.execute(new DisposableSingleObserver<Double>() {
            @Override
            public void onSuccess(Double amount) {
                getView().showAmount(decimalFormat.format(amount));
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

        getSumBy.setIncome(income);
        getSumBy.setFrom(from.getTimeInMillis());
        getSumBy.setTo(to.getTimeInMillis());

        getSectorsBy.setIncome(income);
        getSectorsBy.setTo(to.getTimeInMillis());
        getSectorsBy.setFrom(from.getTimeInMillis());

        UseCase<List<AmountBarPart>> useCase = new UseCase<List<AmountBarPart>>() {
            @Override
            public Single<List<AmountBarPart>> build() {
                return Single.zip(getSumBy.build(),
                        getSectorsBy.build(),
                        new BiFunction<Double, List<Sector>, List<AmountBarPart>>() {

                            @Override
                            public List<AmountBarPart> apply(Double total, List<Sector> sectors) throws Exception {
                                List<AmountBarPart> amountBarPartList =
                                        AmountBarPart.getParts(resources, sectors, total);

                                return amountBarPartList;
                            }
                        });
            }
        };

        useCase.execute(new DisposableSingleObserver<List<AmountBarPart>>() {
            @Override
            public void onSuccess(List<AmountBarPart> amountBarParts) {
                if (amountBarParts.isEmpty()) {
                    getView().finish();
                } else {
                    getView().showDetails(amountBarParts);
                }
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

        addUseCases(useCase);

        getSectorsBy.execute(new DisposableSingleObserver<List<Sector>>() {
            @Override
            public void onSuccess(List<Sector> sectors) {
                getView().populateDetails(sectors, resources.getCategoryColorsArray());
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

    }

    public void onClickSector(Sector sector) {
        navigator.openDashboardAmounts(sector, from.getTimeInMillis());
    }

    public void setFrom(long from) {
        this.from = dateUtils.calculateFrom(from, preferences.getMonthDay());
        this.to = dateUtils.calculateTo(from, preferences.getMonthDay());
    }
}
