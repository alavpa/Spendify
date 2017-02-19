package com.alavpa.spendify.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.di.base.DaggerBaseComponent;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.custom.GridLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.CategoryAdapter;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 14/02/17.
 */

public class DetailsActivity extends BaseActivity implements DetailsView {

    public static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";
    public static final String EXTRA_ISINCOME = "EXTRA_ISINCOME";

    @Inject
    DetailsPresenter presenter;

    @BindView(R.id.chk_repeat)
    CheckBox chkRepeat;

    @BindView(R.id.ll_repeat)
    LinearLayout llRepeat;

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    @BindView(R.id.rv_categories)
    RecyclerView rvCategories;

    @BindView(R.id.et_description)
    EditText etDescription;

    @BindView(R.id.sp_times)
    Spinner spTimes;

    @BindView(R.id.sp_period)
    Spinner spPeriod;

    CategoryAdapter categoryAdapter;

    ArrayAdapter<String> days;
    ArrayAdapter<String> weeks;
    ArrayAdapter<String> months;
    ArrayAdapter<String> years;

    long amountDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        DaggerBaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build()
                .inject(this);

        presenter.attachView(this);

        initView();
    }

    public void initView(){

        String amount = getIntent().getStringExtra(EXTRA_AMOUNT);
        presenter.setAmount(amount);

        boolean isIncome = getIntent().getBooleanExtra(EXTRA_ISINCOME,false);
        presenter.setIsIncome(isIncome);

        days = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.days));
        days.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weeks = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.weeks));
        weeks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        months = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.months));
        months.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        years = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.years));
        years.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPeriod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        spTimes.setAdapter(days);
                        break;
                    case 1:
                        spTimes.setAdapter(weeks);
                        break;
                    case 2:
                        spTimes.setAdapter(months);
                        break;
                    case 3:
                        spTimes.setAdapter(years);
                        break;
                }

                spTimes.setSelection(0);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPeriod.setSelection(0);

        presenter.showAmount();

        rvCategories.setLayoutManager(new GridLayoutManager(this));
        presenter.showCategories();

        chkRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    llRepeat.setVisibility(View.VISIBLE);
                }else{
                    llRepeat.setVisibility(View.GONE);
                }
            }
        });

        amountDate = Calendar.getInstance().getTimeInMillis();
    }

    @Override
    public void populateCategories(List<Category> categories) {
        categoryAdapter = new CategoryAdapter(this,categories);
        rvCategories.setAdapter(categoryAdapter);
    }

    @Override
    public void setAmount(String amount) {
        tvAmount.setText(amount);
    }

    @Override
    public String getDescription() {
        return etDescription.getText().toString();
    }

    @Override
    public boolean repeat() {
        return chkRepeat.isChecked();
    }

    @Override
    public int every() {
        return spTimes.getSelectedItemPosition();
    }

    @Override
    public int period() {
        return spPeriod.getSelectedItemPosition();
    }

    @Override
    public Category selectedCategory() {
        return categoryAdapter.getSelectedCategory();
    }

    @Override
    public long getAmountDate() {
        return amountDate;
    }

    @OnClick
    public void send(View view){
        presenter.send();
    }
}
