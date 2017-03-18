package com.alavpa.spendify.ui.dashboard.sectors;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.domain.usecases.GetSectorsBy;
import com.alavpa.spendify.domain.usecases.GetSumBy;
import com.alavpa.spendify.domain.usecases.base.UseCase;
import com.alavpa.spendify.ui.base.BasePresenter;
import com.alavpa.spendify.ui.model.AmountBarPart;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;

@PerActivity
class DashboardSectorsPresenter extends BasePresenter<DashboardSectorsView> {

    private GetSumBy getSumBy;

    private GetSectorsBy getSectorsBy;

    private
    boolean income;

    private
    String amount;

    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();


    @Inject
    public DashboardSectorsPresenter(GetSumBy getSumBy, GetSectorsBy getSectorsBy){
        this.getSumBy = getSumBy;
        this.getSectorsBy = getSectorsBy;

        addUseCases(getSumBy, getSectorsBy);
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void initView() {

        from.set(2017,Calendar.JANUARY,1);
        to.set(2017,Calendar.DECEMBER,31);

        getView().showAmount(amount);
        if(income){
            getView().showTitle(resources.getString(R.string.income));
        }else{
            getView().showTitle(resources.getString(R.string.outcome));
        }

        getSumBy.setIncome(income);
        getSumBy.setFrom(from.getTimeInMillis());
        getSumBy.setTo(to.getTimeInMillis());

        getSectorsBy.setIncome(income);
        getSectorsBy.setTo(to.getTimeInMillis());
        getSectorsBy.setFrom(from.getTimeInMillis());

        UseCase<List<AmountBarPart>> useCase = new UseCase<List<AmountBarPart>>() {
            @Override
            public Single<List<AmountBarPart>> build() {
                return Single.zip(getSumBy.build(), getSectorsBy.build(), new BiFunction<Double, List<Sector>, List<AmountBarPart>>() {
                    @Override
                    public List<AmountBarPart> apply(Double total, List<Sector> sectors) throws Exception {
                        List<AmountBarPart> amountBarPartList = AmountBarPart.getParts(resources,sectors,total);
                        return amountBarPartList;
                    }
                });
            }
        };

        useCase.execute(new DisposableSingleObserver<List<AmountBarPart>>() {
            @Override
            public void onSuccess(List<AmountBarPart> amountBarParts) {
                getView().showDetails(amountBarParts);
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
                getView().populateDetails(sectors,resources.getCategoryColorsArray());
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

    }

    public void onClickSector(Sector sector) {
        navigator.openDashboardAmounts(sector);
    }
}
