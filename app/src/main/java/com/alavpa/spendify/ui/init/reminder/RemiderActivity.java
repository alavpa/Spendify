package com.alavpa.spendify.ui.init.reminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

@PerActivity
public class RemiderActivity extends BaseActivity implements ReminderView{

    @BindView(R.id.chk_endmonth)
    CheckBox chkEndMonth;

    @BindView(R.id.chk_endday)
    CheckBox chkEndDay;

    @BindView(R.id.chk_offlimit)
    CheckBox chkOfflimit;

    @BindView(R.id.chk_promises)
    CheckBox chkPromises;

    @BindView(R.id.tv_endday)
    TextView tvEndDay;

    @BindView(R.id.sp_times)
    Spinner spTimes;

    @BindView(R.id.sp_period)
    Spinner spPeriod;

    @BindView(R.id.btn_apply)
    TextView btnApply;

    @Inject
    ReminderPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);
        setPresenter(presenter);

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onApply();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initViews();
    }

    @Override
    public void showEndOfDay(boolean enabled) {
        chkEndDay.setChecked(enabled);
    }

    @Override
    public void showEndOfMonth(boolean enabled) {
        chkEndMonth.setChecked(enabled);
    }

    @Override
    public void showOfflimit(boolean enabled) {
        chkOfflimit.setChecked(enabled);
    }

    @Override
    public void showPromises(boolean enabled) {
        chkPromises.setChecked(enabled);
    }

    @Override
    public void showEndOfDayTime(String time) {

    }
}
