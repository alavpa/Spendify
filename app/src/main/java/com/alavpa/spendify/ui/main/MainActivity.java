package com.alavpa.spendify.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.HasComponent;
import com.alavpa.spendify.di.activity.ActivityComponent;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.menu.BaseMenuActivity;
import com.alavpa.spendify.ui.custom.keyboard.Keyboard;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMenuActivity implements MainView, HasComponent<ActivityComponent> {

    @BindView(R.id.tv_amount)
    TextView tvAmount;

    @BindView(R.id.keyboard)
    Keyboard keyboard;

    @Inject
    MainPresenter presenter;

    private ActivityComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        component = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build();

        component.inject(this);

        setPresenter(presenter);

        keyboard.setTextView(tvAmount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Navigator.REQUEST_CODE_DETAILS){
            if(resultCode == RESULT_OK){
                presenter.setAmount(new Amount());
            }
        }
    }

    @Override
    public void setValue(double value) {
        keyboard.setValue(value);
    }

    @OnClick(R.id.btn_income)
    public void onIncomeClick(View v){
        presenter.setIncome(true);
        presenter.goToDetails(keyboard.getValue());
    }

    @OnClick(R.id.btn_outcome)
    public void OnOutcomeClick(View v){
        presenter.setIncome(false);
        presenter.goToDetails(keyboard.getValue());
    }

    @Override
    public ActivityComponent getComponent() {
        return component;
    }
}
