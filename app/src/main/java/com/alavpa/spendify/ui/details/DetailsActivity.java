package com.alavpa.spendify.ui.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import com.alavpa.spendify.ui.custom.dialogs.ConfirmDialog;
import com.alavpa.spendify.ui.custom.dialogs.DatePickerDialog;
import com.alavpa.spendify.ui.custom.keyboard.Keyboard;
import com.alavpa.spendify.ui.custom.widgets.period.PeriodWidget;

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
    public DetailsPresenter presenter;

    @BindView(R.id.hidden_keyboard)
    public Keyboard hiddenKeyboard;

    @BindView(R.id.tv_date)
    public TextView tvDate;

    @BindView(R.id.chk_repeat)
    public CheckBox chkRepeat;

    @BindView(R.id.tv_amount)
    public TextView tvAmount;

    @BindView(R.id.rv_categories)
    public RecyclerView rvCategories;

    @BindView(R.id.et_description)
    public EditText etDescription;

    @BindView(R.id.w_period)
    PeriodWidget wPeriod;

    private
    CategoryAdapter categoryAdapter;

    private
    DatePickerDialog datePickerDialog;

    private
    boolean deletable;

    private
    ConfirmDialog confirmDialog;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.ic_delete);
        item.setVisible(deletable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.ic_delete) {
            confirmDialog.show(getSupportFragmentManager(), ConfirmDialog.class.getSimpleName());
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initView();
    }

    public void initView() {

        hiddenKeyboard.setTextView(tvAmount);

        tvAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hiddenKeyboard.getVisibility()==View.VISIBLE){
                    hiddenKeyboard.setVisibility(View.GONE);
                }else{
                    hiddenKeyboard.setVisibility(View.VISIBLE);
                }
            }
        });


        deletable = false;

        confirmDialog = ConfirmDialog.getInstance(getString(R.string.confirm_delete_amount), new ConfirmDialog
                .ConfirmDialogListener() {
            @Override
            public void onOk() {
                presenter.deleteAmount();
            }

            @Override
            public void onCancel() {

            }
        });


        rvCategories.setLayoutManager(new GridLayoutManager(this));

        chkRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wPeriod.setVisibility(View.VISIBLE);
                } else {
                    wPeriod.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public void populateCategories(List<Category> categories, List<Integer> backgrouns) {
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
    public void showAmount(double amount) {
        hiddenKeyboard.setValue(amount);
    }

    @Override
    public double amount() {
        return hiddenKeyboard.getValue();
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
    public Period period() {
        return wPeriod.getPeriod();
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
    public void setPeriod(Period period) {
        wPeriod.setPeriod(period);
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

        if(hiddenKeyboard.getVisibility()==View.VISIBLE){
           hiddenKeyboard.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }
}
