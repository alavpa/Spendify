package com.alavpa.spendify.ui;

import android.app.Activity;
import android.content.Intent;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.ui.category.add.AddCategoryActivity;
import com.alavpa.spendify.ui.dashboard.DashboardActivity;
import com.alavpa.spendify.ui.dashboard.amounts.DashboardAmountsActivity;
import com.alavpa.spendify.ui.dashboard.sectors.DashboardSectorsActivity;
import com.alavpa.spendify.ui.details.DetailsActivity;
import com.alavpa.spendify.ui.init.day.SelectDayActivity;
import com.alavpa.spendify.ui.main.MainActivity;

import javax.inject.Inject;

/**
 * Created by alavpa on 18/02/17.
 */
@PerActivity
public class Navigator {

    private Activity activity;

    public static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";
    public static final String EXTRA_INCOME = "EXTRA_INCOME";
    public static final String EXTRA_SECTOR = "EXTRA_SECTOR";

    public static final int REQUEST_CODE_ADD_CATEGORY = 1;
    public static final int REQUEST_CODE_DETAILS = 2;

    @Inject
    public Navigator(Activity activity){
        this.activity = activity;
    }

    private Intent getIntent(Class activityClass){
        Intent intent = new Intent(activity, activityClass);
        return intent;
    }
    public void openDetails(Amount amount){
        Intent intent = getIntent(DetailsActivity.class);
        intent.putExtra(EXTRA_AMOUNT,amount);
        activity.startActivityForResult(intent,REQUEST_CODE_DETAILS);
    }

    public void openMain(Amount amount){
        Intent intent = getIntent(MainActivity.class);
        intent.putExtra(EXTRA_AMOUNT,amount);
        activity.startActivity(intent);
    }

    public void openDashboard() {
        Intent intent = getIntent(DashboardActivity.class);
        activity.startActivity(intent);
    }

    public void openDashboardSectors(boolean income, String amount) {
        Intent intent = getIntent(DashboardSectorsActivity.class);
        intent.putExtra(EXTRA_INCOME,income);
        intent.putExtra(EXTRA_AMOUNT,amount);
        activity.startActivity(intent);
    }

    public void openDashboardAmounts(Sector sector) {
        Intent intent = getIntent(DashboardAmountsActivity.class);
        intent.putExtra(EXTRA_SECTOR,sector);
        activity.startActivity(intent);
    }

    public void openAddCategory(boolean income) {
        Intent intent = getIntent(AddCategoryActivity.class);
        intent.putExtra(EXTRA_INCOME,income);
        activity.startActivityForResult(intent,REQUEST_CODE_ADD_CATEGORY);
    }

    public void openSelectDay() {
        Intent intent = getIntent(SelectDayActivity.class);
        activity.startActivity(intent);
    }
}
