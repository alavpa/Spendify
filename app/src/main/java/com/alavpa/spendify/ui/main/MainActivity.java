package com.alavpa.spendify.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.HasComponent;
import com.alavpa.spendify.di.activity.ActivityComponent;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.toolbar.BaseToolbarActivity;
import com.alavpa.spendify.ui.custom.keyboard.Keyboard;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.alavpa.spendify.ui.Navigator.EXTRA_AMOUNT;

public class MainActivity extends BaseToolbarActivity implements MainView, HasComponent<ActivityComponent> {

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

        presenter.attachView(this);

        keyboard.setOnPressKey(new Keyboard.OnPressKey() {
            @Override
            public void onPress(double value) {
                presenter.setValue(value);
            }
        });

        Amount amount = getIntent().getParcelableExtra(EXTRA_AMOUNT);
        if(amount!=null){
            presenter.setAmount(amount);
        }

        presenter.initView();

    }

    @Override
    public void setValue(double value) {
        keyboard.setValue(value);
        tvAmount.setText(keyboard.getFormattedValue());
    }

    @OnClick(R.id.btn_income)
    public void onIncomeClick(View v){
        presenter.setIncome(true);
        presenter.goToDetails();
        finish();
    }

    @OnClick(R.id.btn_outcome)
    public void OnOutcomeClick(View v){
        presenter.setIncome(false);
        presenter.goToDetails();
        finish();
    }

    @Override
    public ActivityComponent getComponent() {
        return component;
    }
}
