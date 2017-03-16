package com.alavpa.spendify.ui.dashboard;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.usecases.GetAmountsBy;
import com.alavpa.spendify.domain.usecases.GetSumBy;
import com.alavpa.spendify.domain.usecases.base.UseCase;
import com.alavpa.spendify.ui.base.BasePresenter;
import com.alavpa.spendify.ui.model.AmountBarPart;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by alavpa on 24/02/17.
 */

public class DashboardPresenter extends BasePresenter<DashboardView>{

    ResDatasource resDatasource;
    GetAmountsBy getAmountsBy;
    GetSumBy getSumByOutcome;
    GetSumBy getSumByIncome;

    Calendar from = Calendar.getInstance();
    Calendar to = Calendar.getInstance();

    DecimalFormat decimalFormat = new DecimalFormat();

    @Inject
    public DashboardPresenter(ResDatasource resDatasource,
                              GetAmountsBy getAmountsBy,
                              GetSumBy getSumByOutcome,
                              GetSumBy getSumByIncome){
        super(getAmountsBy, getSumByIncome, getSumByOutcome);
        this.resDatasource = resDatasource;
        this.getAmountsBy = getAmountsBy;
        this.getSumByOutcome = getSumByOutcome;
        this.getSumByIncome = getSumByIncome;

        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);

    }

    public void initView() {

        from.set(2017,Calendar.JANUARY,1);
        to.set(2017,Calendar.DECEMBER,31);

        getSumByIncome.setFrom(from.getTimeInMillis());
        getSumByIncome.setTo(to.getTimeInMillis());
        getSumByIncome.setIncome(true);

        getSumByOutcome.setFrom(from.getTimeInMillis());
        getSumByOutcome.setTo(to.getTimeInMillis());
        getSumByOutcome.setIncome(false);

        UseCase<Double[]> useCase = new UseCase<Double[]>() {
            @Override
            public Single<Double[]> build() {
                return Single.zip(getSumByIncome.build(),
                        getSumByOutcome.build(),
                        new BiFunction<Double, Double, Double[]>() {
                            @Override
                            public Double[] apply(Double totalIncome, Double totalOutcome) throws Exception {
                                return new Double[]{totalIncome,totalOutcome,Math.max(totalIncome,totalOutcome)};
                            }
                        });
            }
        };


        useCase.execute(new DisposableSingleObserver<Double[]>() {
            @Override
            public void onSuccess(Double[] total) {
                populateIncome(total[0].floatValue(), total[2].floatValue());
                populateOutcome(total[1].floatValue(), total[2].floatValue());

                getView().showTotalIncome(decimalFormat.format(total[0]));
                getView().showTotalOutcome(decimalFormat.format(total[1]));
                getView().showTotal(decimalFormat.format(total[0]-total[1]));
            }

            @Override
            public void onError(Throwable e) {
                getView().showError(e.getMessage());
            }
        });


    }

    public void populateOutcome(float totalPartial, float total){
        
        AmountBarPart income = new AmountBarPart(totalPartial/total, resDatasource.getColor(R.color.red));
        List<AmountBarPart> amountBarPartList = new ArrayList<>();
        amountBarPartList.add(income);
        getView().showOutcome(amountBarPartList);
    }

    public void populateIncome(float totalPartial, float total){

        AmountBarPart income = new AmountBarPart(totalPartial/total, resDatasource.getColor(R.color.blue));
        List<AmountBarPart> amountBarPartList = new ArrayList<>();
        amountBarPartList.add(income);
        getView().showIncome(amountBarPartList);

    }
}

