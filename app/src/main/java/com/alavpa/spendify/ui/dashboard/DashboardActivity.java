package com.alavpa.spendify.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.HasComponent;
import com.alavpa.spendify.di.activity.ActivityComponent;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.ui.base.toolbar.BaseToolbarActivity;
import com.alavpa.spendify.ui.custom.graphics.AmountBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 24/02/17.
 */

public class DashboardActivity extends BaseToolbarActivity implements DashboardView, HasComponent<ActivityComponent> {

    @Inject
    DashboardPresenter presenter;

    @BindView(R.id.bar_income)
    AmountBar barIncome;

    @BindView(R.id.bar_outcome)
    AmountBar barOutcome;

    public
    ActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);

        component = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build();

        component.inject(this);
        presenter.attachView(this);

        initView();
        presenter.initView();
    }

    public void initView(){

    }

    @Override
    public void showOutcome(List<AmountBar.AmountBarPart> outcome) {
        barOutcome.setParts(outcome);
    }

    @Override
    public void showIncome(List<AmountBar.AmountBarPart> income) {
        barIncome.setParts(income);
    }

    @Override
    public ActivityComponent getComponent() {
        return component;
    }
}
