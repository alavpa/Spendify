package com.alavpa.spendify.ui.dashboard.amounts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
import com.alavpa.spendify.ui.custom.LinearLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.AmountAdapter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class DashboardAmountsActivity extends BaseNoMenuActivity implements DashboardAmountsView{

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

        setPresenter(presenter);
        initView();

        Sector sector = getIntent().getParcelableExtra(Navigator.EXTRA_SECTOR);
        presenter.setCategory(sector.getCategory());

        long from = getIntent().getLongExtra(Navigator.EXTRA_FROM,0);
        presenter.setFrom(from);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
                    simpleDateFormat,
                    new AmountAdapter.OnClickAmount() {
                        @Override
                        public void onClick(Amount amount) {
                            presenter.onClickAmount(amount);
                        }
                    });

            rvDetails.setAdapter(amountAdapter);
        }else{
            amountAdapter.setAmounts(amounts);
            amountAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showCategoryColor(int colorResId) {
        flCategory.setBackgroundResource(colorResId);
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
