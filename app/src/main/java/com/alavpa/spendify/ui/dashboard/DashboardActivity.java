package com.alavpa.spendify.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.HasComponent;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.activity.ActivityComponent;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.toolbar.BaseToolbarActivity;
import com.alavpa.spendify.ui.custom.LinearLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.AmountAdapter;
import com.alavpa.spendify.ui.custom.widgets.AmountBar;
import com.alavpa.spendify.ui.model.AmountBarPart;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 24/02/17.
 */

@PerActivity
public class DashboardActivity extends BaseToolbarActivity implements DashboardView, HasComponent<ActivityComponent> {

    @Inject
    DashboardPresenter presenter;

    @Inject
    DecimalFormat decimalFormat;

    @Inject
    SimpleDateFormat simpleDateFormat;

    @BindView(R.id.bar_income)
    AmountBar barIncome;

    @BindView(R.id.bar_outcome)
    AmountBar barOutcome;

    @BindView(R.id.tv_outcome)
    TextView tvOutcome;

    @BindView(R.id.tv_income)
    TextView tvIncome;

    @BindView(R.id.rv_income)
    RecyclerView rvIncome;

    @BindView(R.id.rv_outcome)
    RecyclerView rvOutcome;

    @BindView(R.id.tv_total)
    TextView tvTotal;

    AmountAdapter incomeAdapter;

    AmountAdapter outcomeAdapter;

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
        //rvIncome.setHasFixedSize(true);
        rvIncome.setLayoutManager(new LinearLayoutManager(this));

        //rvOutcome.setHasFixedSize(true);
        rvOutcome.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showOutcome(List<AmountBarPart> outcome) {
        barOutcome.setParts(outcome);
        barOutcome.invalidate();
    }

    @Override
    public void showIncome(List<AmountBarPart> income) {
        barIncome.setParts(income);
        barIncome.invalidate();
    }

    @Override
    public void showTotalOutcome(String totalOutcome) {
        tvOutcome.setText(totalOutcome);
    }

    @Override
    public void showTotalIncome(String totalIncome) {
        tvIncome.setText(totalIncome);
    }

    @Override
    public void showTotal(String total) {
        tvTotal.setText(total);
    }

    @Override
    public ActivityComponent getComponent() {
        return component;
    }

    public void populateIncome(List<Amount> amounts, int[] colors){
        if(incomeAdapter==null){
            incomeAdapter = new AmountAdapter(this,
                    amounts,
                    colors,
                    decimalFormat,
                    simpleDateFormat);

            rvIncome.setAdapter(incomeAdapter);

        }
    }

    public void populateOutcome(List<Amount> amounts, int[] colors){
        if(outcomeAdapter==null){
            outcomeAdapter = new AmountAdapter(this,
                    amounts,
                    colors,
                    decimalFormat,
                    simpleDateFormat);

            rvOutcome.setAdapter(outcomeAdapter);

        }
    }
}
