package com.alavpa.spendify.ui.init;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.ui.base.BaseActivity;

import javax.inject.Inject;

public class StartActivity extends BaseActivity implements StartView{

    @Inject
    StartPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build()
                .inject(this);

        setPresenter(presenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.initView();
    }
}
