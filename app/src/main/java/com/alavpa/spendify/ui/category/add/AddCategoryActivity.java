package com.alavpa.spendify.ui.category.add;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.EditText;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.HasComponent;
import com.alavpa.spendify.di.activity.ActivityComponent;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.toolbar.BaseToolbarActivity;
import com.alavpa.spendify.ui.custom.GridLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.CategoryColorAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 28/02/17.
 */

public class AddCategoryActivity extends BaseToolbarActivity implements AddCategoryView,
        HasComponent<ActivityComponent>{

    @BindView(R.id.chk_income)
    CheckBox chkIncome;
    @BindView(R.id.et_name)
    EditText etTitle;
    @BindView(R.id.rv_colors)
    RecyclerView rvColors;

    @Inject
    AddCategoryPresenter presenter;

    ActivityComponent component;
    CategoryColorAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);

        component = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(new ActivityModule())
                .baseModule(getBaseModule())
                .build();
        component.inject(this);

        presenter.attachView(this);

        rvColors.setLayoutManager(new GridLayoutManager(this));

        presenter.showColors();

        boolean income = getIntent().getBooleanExtra(Navigator.EXTRA_INCOME,false);
        chkIncome.setChecked(income);
    }

    @Override
    public ActivityComponent getComponent() {
        return component;
    }

    @Override
    public void populateColors(List<Drawable> colors) {
        if(adapter==null){
            adapter = new CategoryColorAdapter(this,colors);
            rvColors.setAdapter(adapter);
        }else{
            adapter.setCategoryColors(colors);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public String name() {
        return etTitle.getText().toString();
    }

    @Override
    public boolean income() {
        return chkIncome.isChecked();
    }

    @Override
    public int color() {
        return adapter.getSelected();
    }

    @Override
    public void onSendSuccess() {
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.btn_ok)
    public void onSend(){
        presenter.send();
    }
}
