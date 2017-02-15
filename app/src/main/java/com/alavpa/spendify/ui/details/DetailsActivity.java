package com.alavpa.spendify.ui.details;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.di.base.DaggerBaseComponent;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.details.perday.PerDayFragment;

import butterknife.ButterKnife;

/**
 * Created by alavpa on 14/02/17.
 */

public class DetailsActivity extends BaseActivity implements DetailsView {

    PerDayFragment perDayFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        DaggerBaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build()
                .inject(this);
    }
}
