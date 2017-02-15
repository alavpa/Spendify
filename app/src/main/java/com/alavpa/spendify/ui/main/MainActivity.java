package com.alavpa.spendify.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.di.base.DaggerBaseComponent;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.custom.Keyboard;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    @BindView(R.id.keyboard)
    Keyboard keyboard;

    @Inject
    MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        DaggerBaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build()
                .inject(this);

        presenter.attachView(this);

        keyboard.setOnPressKey(new Keyboard.OnPressKey() {
            @Override
            public void onPress(String value) {
                presenter.showValue(value);
            }
        });

        presenter.showValue(keyboard.getValue());
    }

    @Override
    public void setAmount(String amount) {
        tvAmount.setText(amount);
    }

    @OnClick(R.id.btn_income)
    public void onIncomeClick(View v){

    }

    @OnClick(R.id.btn_outcome)
    public void OnOutcomeClick(View v){

    }
}
