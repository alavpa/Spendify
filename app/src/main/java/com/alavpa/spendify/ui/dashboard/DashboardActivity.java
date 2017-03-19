package com.alavpa.spendify.ui.dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
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
public class DashboardActivity extends BaseNoMenuActivity implements DashboardView {

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

    @BindView(R.id.tv_total)
    TextView tvTotal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build()
                .inject(this);

        setPresenter(presenter);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        presenter.initView();
    }

    public void initView(){

        barIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openIncomeDetails(tvIncome.getText().toString());
            }
        });

        barOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.openOutcomeDetails(tvOutcome.getText().toString());
            }
        });
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

}
