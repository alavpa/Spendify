package com.alavpa.spendify.ui.init.day;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.ui.base.nomenu.BaseNoMenuActivity;
import com.alavpa.spendify.ui.custom.keyboard.Keyboard;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectDayMonthActivity extends BaseNoMenuActivity implements SelectDayView {

    @BindView(R.id.btn_apply)
    TextView btnApply;

    @BindView(R.id.tv_day)
    TextView tvDay;

    @BindView(R.id.keyboard)
    Keyboard keyboard;

    @Inject
    SelectDayPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_day);
        ButterKnife.bind(this);
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build()
                .inject(this);

        setPresenter(presenter);
        keyboard.setTextView(tvDay);
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.next(keyboard.getValue(), true);
            }
        });

        btnApply.setText(R.string.save);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initView();
    }

    @Override
    public void setDay(int day) {
        keyboard.setValue(day);
    }
}
