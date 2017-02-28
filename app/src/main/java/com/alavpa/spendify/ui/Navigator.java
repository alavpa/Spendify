package com.alavpa.spendify.ui;

import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.dashboard.DashboardActivity;
import com.alavpa.spendify.ui.details.DetailsActivity;
import com.alavpa.spendify.ui.main.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alavpa on 18/02/17.
 */
@Singleton
public class Navigator {

    public static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";

    @Inject
    public Navigator(){}

    private Intent getIntent(Context context, Class activityClass){
        Intent intent = new Intent(context, activityClass);
        return intent;
    }
    public void openDetails(Context context, Amount amount){
        Intent intent = getIntent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_AMOUNT,amount);
        context.startActivity(intent);
    }

    public void openMain(Context context, Amount amount){
        Intent intent = getIntent(context, MainActivity.class);
        intent.putExtra(EXTRA_AMOUNT,amount);
        context.startActivity(intent);
    }

    public void openDashboard(Context context) {
        Intent intent = getIntent(context, DashboardActivity.class);
        context.startActivity(intent);
    }
}
