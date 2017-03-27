package com.alavpa.spendify.ui.details;

import android.content.Intent;
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
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
import com.alavpa.spendify.ui.custom.GridLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.CategoryAdapter;
import com.alavpa.spendify.ui.custom.dialogs.DatePickerDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alavpa.spendify.ui.Navigator.EXTRA_AMOUNT;

/**
 * Created by alavpa on 14/02/17.
 */

public class DetailsActivity extends BaseNoMenuActivity implements DetailsView {

    @Inject
    DetailsPresenter presenter;

    @BindView(R.id.tv_date)
    TextView tvDate;

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

    DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build()
                .inject(this);

        setPresenter(presenter);

        Amount amount = getIntent().getParcelableExtra(EXTRA_AMOUNT);
        presenter.setAmount(amount);

        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initView();
    }

    public void initView() {

        days = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.days));
        days.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weeks = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.weeks));
        weeks.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        months = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.months));
        months.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        years = new ArrayAdapter<>(this,
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

                presenter.setPeriod(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spPeriod.setSelection(Period.PER_DAY);

        rvCategories.setLayoutManager(new GridLayoutManager(this));

        chkRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    llRepeat.setVisibility(View.VISIBLE);
                } else {
                    llRepeat.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void populateCategories(List<Category> categories, int[] backgrouns) {
        if (categoryAdapter == null) {
            categoryAdapter = new CategoryAdapter(this,
                    categories,
                    backgrouns,
                    true,
                    new CategoryAdapter.OnCategoryClick() {
                        @Override
                        public void onClick(Category category) {

                        }

                        @Override
                        public void onAddClick() {

                            presenter.goToAddCategory();

                        }
                    });
            rvCategories.setAdapter(categoryAdapter);
        } else {
            categoryAdapter.setCategories(categories);
            categoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setAmount(String amount) {
        tvAmount.setText(amount);
    }

    @Override
    public String description() {
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
    public Category category() {
        return categoryAdapter.getSelected();
    }

    @Override
    public void setDescription(String description) {
        etDescription.setText(description);
    }

    @Override
    public void setDate(String date) {
        tvDate.setText(date);
    }

    @Override
    public void selectCategory(Category category) {
        categoryAdapter.setSelected(category);
        categoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRepeat(boolean repeat) {
        chkRepeat.setChecked(repeat);
    }

    @Override
    public void setTimes(int times) {
        spTimes.setSelection(times);
    }

    @Override
    public void setPeriod(int period) {
        spPeriod.setSelection(period);
    }

    @Override
    public void showDatePickerDialog(long date) {
        if (datePickerDialog == null) {
            datePickerDialog = DatePickerDialog.getInstance(date, new DatePickerDialog.OnDatePickerSelected() {
                @Override
                public void onDateSet(long date) {
                    presenter.setDate(date);
                    presenter.showDate();
                }
            });
        }

        datePickerDialog.show(getSupportFragmentManager(), "date_picker");
    }

    @OnClick(R.id.btn_apply)
    public void send(View view) {
        presenter.send();
    }

    @OnClick(R.id.tv_date)
    public void selectDate(View view) {
        presenter.showDatePickerDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Navigator.REQUEST_CODE_ADD_CATEGORY &&
                resultCode == RESULT_OK) {
            presenter.showCategories();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
