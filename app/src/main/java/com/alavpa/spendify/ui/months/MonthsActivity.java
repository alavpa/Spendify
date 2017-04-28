package com.alavpa.spendify.ui.months;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
import com.alavpa.spendify.ui.custom.LinearLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.MonthAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class MonthsActivity extends BaseNoMenuActivity implements MonthsView {

    @BindView(R.id.rv_months)
    RecyclerView rvMonths;

    @Inject
    MonthsPresenter presenter;

    MonthAdapter monthAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_months);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        setPresenter(presenter);

        rvMonths.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initView();
    }

    @Override
    public void populateMonths(List<Long> months) {
        if(monthAdapter==null){
            monthAdapter = new MonthAdapter(this, months, new MonthAdapter.OnMonthClick() {
                @Override
                public void onMonthClick(long month) {
                    presenter.onClickMonth(month);
                }
            });

            rvMonths.setAdapter(monthAdapter);
        }else{
            monthAdapter.setMonths(months);
            monthAdapter.notifyDataSetChanged();
        }
    }
}
