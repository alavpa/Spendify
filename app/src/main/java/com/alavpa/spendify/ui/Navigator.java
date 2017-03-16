package com.alavpa.spendify.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.category.add.AddCategoryActivity;
import com.alavpa.spendify.ui.dashboard.DashboardActivity;
import com.alavpa.spendify.ui.dashboard.details.DashboardDetailsActivity;
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
    public static final String EXTRA_INCOME = "EXTRA_INCOME";

    public static final int REQUEST_CODE_ADD_CATEGORY = 1;

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

    public void openDashboardDetails(Context context, boolean income, String amount) {
        Intent intent = getIntent(context, DashboardDetailsActivity.class);
        intent.putExtra(EXTRA_INCOME,income);
        intent.putExtra(EXTRA_AMOUNT,amount);
        context.startActivity(intent);
    }

    public void openAddCategory(AppCompatActivity context, boolean income) {
        Intent intent = getIntent(context, AddCategoryActivity.class);
        intent.putExtra(EXTRA_INCOME,income);
        context.startActivityForResult(intent,REQUEST_CODE_ADD_CATEGORY);
    }
}
