package com.alavpa.spendify.ui.dashboard.amounts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.custom.LinearLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.AmountAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class DashboardAmountsActivity extends BaseActivity implements DashboardAmountsView{

    @Inject
    DashboardAmountsPresenter presenter;

    @Inject
    DecimalFormat decimalFormat;

    @Inject
    SimpleDateFormat simpleDateFormat;

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    @BindView(R.id.tv_category)
    TextView tvCategory;

    @BindView(R.id.fl_category)
    FrameLayout flCategory;

    @BindView(R.id.rv_details)
    RecyclerView rvDetails;

    AmountAdapter amountAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_amounts);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build()
                .inject(this);
        initView();
        presenter.attachView(this);


        Sector sector = getIntent().getParcelableExtra(Navigator.EXTRA_SECTOR);
        presenter.setSector(sector);
        presenter.initView();

    }

    private void initView() {
        rvDetails.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void populateDetails(List<Amount> amounts){
        if(amountAdapter==null){
            amountAdapter = new AmountAdapter(this,
                    amounts,
                    decimalFormat,
                    simpleDateFormat);

            rvDetails.setAdapter(amountAdapter);
        }
    }

    @Override
    public void showCategoryColor(int color) {
        flCategory.setBackgroundColor(color);
    }

    @Override
    public void showCategoryName(String name) {
        tvCategory.setText(name);
    }

    @Override
    public void showCategoryAmount(String amount) {
        tvAmount.setText(amount);
    }

}
