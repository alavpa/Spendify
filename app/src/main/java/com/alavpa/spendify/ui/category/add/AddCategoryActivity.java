package com.alavpa.spendify.ui.category.add;

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
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
import com.alavpa.spendify.ui.custom.GridLayoutManager;
import com.alavpa.spendify.ui.custom.adapters.CategoryColorAdapter;
import com.alavpa.spendify.ui.custom.dialogs.ConfirmDialog;
import com.alavpa.spendify.ui.custom.keyboard.Keyboard;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 28/02/17.
 */

public class AddCategoryActivity extends BaseNoMenuActivity implements AddCategoryView {

    @BindView(R.id.keyboard)
    Keyboard keyboard;

    @BindView(R.id.tv_limit)
    TextView tvLimit;

    @BindView(R.id.chk_limit)
    CheckBox chkLimit;

    @BindView(R.id.et_name)
    EditText etTitle;
    @BindView(R.id.rv_colors)
    RecyclerView rvColors;

    @Inject
    AddCategoryPresenter presenter;

    CategoryColorAdapter adapter;

    boolean deletable;

    ConfirmDialog confirmDialog;

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

        chkLimit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    tvLimit.setVisibility(View.VISIBLE);
                } else {
                    tvLimit.setVisibility(View.GONE);
                }
            }
        });

        tvLimit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (keyboard.getVisibility() == View.GONE) {
                    keyboard.setVisibility(View.VISIBLE);
                } else {
                    keyboard.setVisibility(View.GONE);
                }
            }
        });

        keyboard.setTextView(tvLimit);

        deletable = false;

        confirmDialog = ConfirmDialog.getInstance(getString(R.string.confirm_delete), new ConfirmDialog.ConfirmDialogListener() {
            @Override
            public void onOk() {
                presenter.deleteCategory();
            }

            @Override
            public void onCancel() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_category, menu);
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
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        if (item.getItemId() == R.id.ic_delete) {
            confirmDialog.show(getSupportFragmentManager(), ConfirmDialog.class.getSimpleName());
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.showColors();
        presenter.showLimit();
        presenter.showName();
        presenter.showSelected();
        presenter.showDelete();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(confirmDialog!=null){
            confirmDialog.dismiss();
            confirmDialog = null;
        }
    }

    @Override
    public void populateColors(int[] colors, int selected) {
        if (adapter == null) {
            adapter = new CategoryColorAdapter(this, colors);
            adapter.setSelected(selected);
            rvColors.setAdapter(adapter);
        } else {
            adapter.setCategoryColors(colors);
            adapter.setSelected(selected);
            adapter.notifyDataSetChanged();
        }
    }

    public String name() {
        return etTitle.getText().toString();
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
    public void showName(String name) {
        etTitle.setText(name);
    }

    @Override
    public void setSelected(int color) {
        adapter.setSelected(color);
    }

    @Override
    public void showLimit(double limit) {
        chkLimit.setChecked(limit > 0);
        keyboard.setValue(limit);
    }

    @Override
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    @OnClick(R.id.btn_ok)
    public void onSend() {
        if (chkLimit.isChecked()) {
            presenter.send(name(), color(), limit());
        } else {
            presenter.send(name(), color());
        }
    }

    private double limit() {
        return keyboard.getValue();
    }

    @Override
    public void onBackPressed() {
        if (keyboard.getVisibility() == View.VISIBLE) {
            keyboard.setVisibility(View.GONE);
        } else {
            super.onBackPressed();
        }
    }


}
