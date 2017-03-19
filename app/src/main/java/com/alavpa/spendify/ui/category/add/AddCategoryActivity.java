package com.alavpa.spendify.ui.category.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
import com.alavpa.spendify.ui.custom.GridLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.CategoryColorAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 28/02/17.
 */

public class AddCategoryActivity extends BaseNoMenuActivity implements AddCategoryView{

    @BindView(R.id.chk_income)
    CheckBox chkIncome;
    @BindView(R.id.et_name)
    EditText etTitle;
    @BindView(R.id.rv_colors)
    RecyclerView rvColors;

    @Inject
    AddCategoryPresenter presenter;

    CategoryColorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);

        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule())
                .baseModule(getBaseModule())
                .build()
                .inject(this);

        setPresenter(presenter);

        rvColors.setLayoutManager(new GridLayoutManager(this));

        Category category = getIntent().getParcelableExtra(Navigator.EXTRA_CATEGORY);
        presenter.setCategory(category);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.showColors();
        presenter.showIncome();
        presenter.showName();
        presenter.showSelected();
    }

    @Override
    public void populateColors(int[] colors, int selected) {
        if(adapter==null){
            adapter = new CategoryColorAdapter(this,colors);
            adapter.setSelected(selected);
            rvColors.setAdapter(adapter);
        }else{
            adapter.setCategoryColors(colors);
            adapter.setSelected(selected);
            adapter.notifyDataSetChanged();
        }
    }

    public String name() {
        return etTitle.getText().toString();
    }

    public boolean income() {
        return chkIncome.isChecked();
    }

    public int color() {
        return adapter.getSelected();
    }

    @Override
    public void onSendSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void showIncome(boolean income) {
        chkIncome.setChecked(income);
    }

    @Override
    public void showName(String name) {
        etTitle.setText(name);
    }

    @Override
    public void setSelected(int color) {
        adapter.setSelected(color);
    }

    @OnClick(R.id.btn_ok)
    public void onSend(){
        presenter.send(name(),income(),color());
    }
}
