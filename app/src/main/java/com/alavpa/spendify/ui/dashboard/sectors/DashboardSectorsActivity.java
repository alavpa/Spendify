package com.alavpa.spendify.ui.dashboard.sectors;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
import com.alavpa.spendify.ui.custom.LinearLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.SectorAdapter;
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
public class DashboardSectorsActivity extends BaseNoMenuActivity implements DashboardSectorsView {

    @Inject
    DashboardSectorsPresenter presenter;

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

    SectorAdapter detailsAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_sectors);

        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build()
                .inject(this);


        setPresenter(presenter);

        initView();
        boolean income = getIntent().getBooleanExtra(Navigator.EXTRA_INCOME,false);
        presenter.setIncome(income);

        long from = getIntent().getLongExtra(Navigator.EXTRA_FROM,0);
        presenter.setFrom(from);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void populateDetails(List<Sector> sectors, List<Integer> colors){
        if(detailsAdapter==null){
            detailsAdapter = new SectorAdapter(this,
                    sectors,
                    colors,
                    decimalFormat,
                    new SectorAdapter.OnClickSector() {
                        @Override
                        public void onClick(Sector sector) {
                            presenter.onClickSector(sector);
                        }
                    });

            rvDetails.setAdapter(detailsAdapter);
        }else{
            detailsAdapter.setSectors(sectors);
            detailsAdapter.notifyDataSetChanged();
        }
    }
}
