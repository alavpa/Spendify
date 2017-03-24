package com.alavpa.spendify.ui.init.reminder;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.custom.widgets.period.PeriodWidget;

import java.util.Calendar;

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

    @BindView(R.id.w_period)
    PeriodWidget wPeriod;

    @BindView(R.id.btn_apply)
    TextView btnApply;

    TimePickerDialog timePickerDialog;

    @Inject
    ReminderPresenter presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        ButterKnife.bind(this);
        getActivityComponent().inject(this);

        setPresenter(presenter);

        tvEndDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.showTimePickerDialog(tvEndDay.getText().toString());
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String time = null;
                Period period = null;

                if(chkEndDay.isChecked()){
                    time = tvEndDay.getText().toString();
                }

                if(chkPromises.isChecked()){
                    period = wPeriod.getPeriod();
                }

                presenter.onApply(chkEndDay.isChecked(),
                        chkEndMonth.isChecked(),
                        chkOfflimit.isChecked(),
                        chkPromises.isChecked(),
                        time,
                        period);

                presenter.goToNext();
            }
        });

        chkEndDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                tvEndDay.setVisibility((isChecked)?View.VISIBLE:View.GONE);
            }
        });

        chkPromises.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                wPeriod.setVisibility((isChecked)?View.VISIBLE:View.GONE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(timePickerDialog!=null){
            if(timePickerDialog.isShowing()){
                timePickerDialog.dismiss();
            }
            timePickerDialog = null;
        }
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
        tvEndDay.setText(time);
    }



    @Override
    public void showPromisesPeriod(Period period){
        wPeriod.setPeriod(period);
    }

    @Override
    public void showTimePickerDialog(Calendar calendar) {

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        if(timePickerDialog == null){
            timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int min) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, hour);
                    calendar.set(Calendar.MINUTE,min);
                    calendar.set(Calendar.SECOND,0);

                    presenter.setEndDayTime(calendar);
                }
            },hour,min,true);

        }else {

            timePickerDialog.updateTime(hour, min);
        }

        timePickerDialog.show();
    }
}
