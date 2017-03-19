package com.alavpa.spendify.ui.custom.widgets.period;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.Period;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PeriodWidget extends LinearLayout implements PeriodView{

    @BindView(R.id.sp_times)
    Spinner spTimes;

    @BindView(R.id.sp_period)
    Spinner spPeriod;

    PeriodPresenter presenter;

    ArrayAdapter<String> days;
    ArrayAdapter<String> weeks;
    ArrayAdapter<String> months;
    ArrayAdapter<String> years;

    public PeriodWidget(Context context) {
        super(context);
        init();
    }

    public PeriodWidget(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PeriodWidget(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PeriodWidget(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){

        presenter = new PeriodPresenter(this);

        LayoutInflater.from(getContext())
                .inflate(R.layout.layout_period,this,true);
        ButterKnife.bind(this);

        days = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.days));
        days.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weeks = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.weeks));
        weeks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        months = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.months));
        months.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        years = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.years));
        years.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case Period.PER_DAY:
                        spTimes.setAdapter(days);
                        break;
                    case Period.PER_WEEK:
                        spTimes.setAdapter(weeks);
                        break;
                    case Period.PER_MONTH:
                        spTimes.setAdapter(months);
                        break;
                    case Period.PER_YEAR:
                        spTimes.setAdapter(years);
                        break;
                }

                presenter.onChangePeriod(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                presenter.setTimes(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spPeriod.setSelection(Period.PER_DAY);
    }

    public void setPeriod(Period period){
        presenter.setPeriod(period);
    }

    @Override
    public void setPeriod(int position){
        spPeriod.setSelection(position);
    }

    @Override
    public void setTimes(int position){
        spTimes.setSelection(position);
    }

    public Period getPeriod(){
        return presenter.getPeriod();
    }
}
