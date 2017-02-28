package com.alavpa.spendify.ui.dashboard;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.BasePresenter;
import com.alavpa.spendify.ui.custom.graphics.AmountBar;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by alavpa on 24/02/17.
 */

public class DashboardPresenter extends BasePresenter<DashboardView>{

    List<Amount> amounts;
    ResDatasource resDatasource;

    @Inject
    public DashboardPresenter(ResDatasource resDatasource){
        this.resDatasource = resDatasource;
    }

    public void initView() {
        populateIncome();
        populateOutcome();
    }

    public void populateOutcome(){
        List<AmountBar.AmountBarPart> outcome = new ArrayList<>();

        AmountBar.AmountBarPart outcome1 = new AmountBar.AmountBarPart(0.5f,resDatasource.getColor(R.color.cyan));
        AmountBar.AmountBarPart outcome2 = new AmountBar.AmountBarPart(0.5f,resDatasource.getColor(R.color.amber));

        outcome.add(outcome1);
        outcome.add(outcome2);

        getView().showOutcome(outcome);
    }

    public void populateIncome(){
        List<AmountBar.AmountBarPart> income = new ArrayList<>();
        AmountBar.AmountBarPart income1 = new AmountBar.AmountBarPart(1.0f,resDatasource.getColor(R.color.blue));
        income.add(income1);

        getView().showIncome(income);
    }
}

