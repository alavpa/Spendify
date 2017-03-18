package com.alavpa.spendify.ui.dashboard.details;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.GetAmountsBy;
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
class DashboardDetailsPresenter extends BasePresenter<DashboardDetailsView> {

    private
    GetAmountsBy getAmountsBy;

    private GetSumBy getSumBy;

    private
    boolean income;

    private
    String amount;

    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();


    @Inject
    public DashboardDetailsPresenter(GetAmountsBy getAmountsBy, GetSumBy getSumBy){
        super(getAmountsBy, getSumBy);
        this.getAmountsBy = getAmountsBy;
        this.getSumBy = getSumBy;
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

        getAmountsBy.setIncome(income);
        getAmountsBy.setTo(to.getTimeInMillis());
        getAmountsBy.setFrom(from.getTimeInMillis());

        UseCase<List<AmountBarPart>> useCase = new UseCase<List<AmountBarPart>>() {
            @Override
            public Single<List<AmountBarPart>> build() {
                return Single.zip(getSumBy.build(), getAmountsBy.build(), new BiFunction<Double, List<Amount>, List<AmountBarPart>>() {
                    @Override
                    public List<AmountBarPart> apply(Double total, List<Amount> amounts) throws Exception {
                        List<AmountBarPart> amountBarPartList = AmountBarPart.getParts(resources,amounts,total.floatValue());
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

        addUseCase(useCase);

        getAmountsBy.execute(new DisposableSingleObserver<List<Amount>>() {
            @Override
            public void onSuccess(List<Amount> amounts) {
                getView().populateDetails(amounts,resources.getCategoryColorsArray());
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });

    }
}
