package com.alavpa.spendify.ui.dashboard.details;

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
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.toolbar.BaseToolbarActivity;
import com.alavpa.spendify.ui.custom.LinearLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.AmountAdapter;
import com.alavpa.spendify.ui.custom.widgets.AmountCircle;
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
public class DashboardDetailsActivity extends BaseToolbarActivity implements DashboardDetailsView, HasComponent<ActivityComponent> {

    @Inject
    DashboardDetailsPresenter presenter;

    @Inject
    DecimalFormat decimalFormat;

    @Inject
    SimpleDateFormat simpleDateFormat;

    @BindView(R.id.bar_details)
    AmountCircle barDetails;

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    @BindView(R.id.rv_details)
    RecyclerView rvDetails;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    AmountAdapter detailsAdapter;

    public
    ActivityComponent component;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_details);

        ButterKnife.bind(this);

        component = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build();

        component.inject(this);
        presenter.attachView(this);

        initView();
        boolean income = getIntent().getBooleanExtra(Navigator.EXTRA_INCOME,false);
        presenter.setIncome(income);

        String amount = getIntent().getStringExtra(Navigator.EXTRA_AMOUNT);
        presenter.setAmount(amount);
        presenter.initView();
    }

    public void initView(){
        rvDetails.setHasFixedSize(true);
        rvDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void showTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void showAmount(String amount) {
        tvAmount.setText(amount);
    }

    @Override
    public void showDetails(List<AmountBarPart> details) {
        barDetails.setParts(details);
        barDetails.invalidate();
    }


    @Override
    public ActivityComponent getComponent() {
        return component;
    }

    public void populateDetails(List<Amount> amounts, int[] colors){
        if(detailsAdapter==null){
            detailsAdapter = new AmountAdapter(this,
                    amounts,
                    colors,
                    decimalFormat,
                    simpleDateFormat);

            rvDetails.setAdapter(detailsAdapter);

        }
    }
}
