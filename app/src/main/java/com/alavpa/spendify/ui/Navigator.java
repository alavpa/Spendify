package com.alavpa.spendify.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;

import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.ui.category.add.AddCategoryActivity;
import com.alavpa.spendify.ui.dashboard.DashboardActivity;
import com.alavpa.spendify.ui.dashboard.amounts.DashboardAmountsActivity;
import com.alavpa.spendify.ui.dashboard.sectors.DashboardSectorsActivity;
import com.alavpa.spendify.ui.details.DetailsActivity;
import com.alavpa.spendify.ui.init.categories.ManageCategoriesActivity;
import com.alavpa.spendify.ui.init.categories.StartCategoriesActivity;
import com.alavpa.spendify.ui.init.day.SelectDayActivity;
import com.alavpa.spendify.ui.init.day.SelectDayMonthActivity;
import com.alavpa.spendify.ui.init.reminder.NotificationsActivity;
import com.alavpa.spendify.ui.init.reminder.RemiderActivity;
import com.alavpa.spendify.ui.main.MainActivity;
import com.alavpa.spendify.ui.months.MonthsActivity;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

/**
 * Created by alavpa on 18/02/17.
 */
@PerActivity
public class Navigator {

    public static final String EXTRA_CATEGORY = "EXTRA_CATEGORY";
    public static final String EXTRA_FROM = "EXTRA_FROM";
    public static final String EXTRA_AMOUNT = "EXTRA_AMOUNT";
    public static final String EXTRA_INCOME = "EXTRA_INCOME";
    public static final String EXTRA_SECTOR = "EXTRA_SECTOR";
    public static final int REQUEST_CODE_ADD_CATEGORY = 1;
    public static final int REQUEST_CODE_DETAILS = 2;
    private WeakReference<Activity> context;


    @Inject
    public Navigator(Activity context) {
        this.context = new WeakReference<>(context);
    }


    private Intent getIntent(Class activityClass){
        Intent intent = new Intent(context.get(), activityClass);
        return intent;
    }
    public void openDetails(Amount amount){
        Intent intent = getIntent(DetailsActivity.class);
        intent.putExtra(EXTRA_AMOUNT,amount);
        context.get().startActivityForResult(intent, REQUEST_CODE_DETAILS);
    }

    public void openMain(){
        Intent intent = getIntent(MainActivity.class);
        context.get().startActivity(intent);
    }

    public void openDashboard(long month) {
        Intent intent = getIntent(DashboardActivity.class);
        intent.putExtra(Navigator.EXTRA_FROM,month);
        context.get().startActivity(intent);
    }

    public void openDashboardSectors(boolean income, long from) {
        Intent intent = getIntent(DashboardSectorsActivity.class);
        intent.putExtra(EXTRA_INCOME,income);
        intent.putExtra(EXTRA_FROM,from);
        context.get().startActivity(intent);
    }

    public void openDashboardAmounts(Sector sector, long from) {
        Intent intent = getIntent(DashboardAmountsActivity.class);
        intent.putExtra(EXTRA_SECTOR,sector);
        intent.putExtra(EXTRA_FROM,from);
        context.get().startActivity(intent);
    }

    public void openAddCategory(Category category) {
        Intent intent = getIntent(AddCategoryActivity.class);
        intent.putExtra(EXTRA_CATEGORY,category);
        context.get().startActivityForResult(intent, REQUEST_CODE_ADD_CATEGORY);
    }

    public void openSelectDay() {
        Intent intent = getIntent(SelectDayActivity.class);
        context.get().startActivity(intent);
    }

    public void openStartCategories() {
        Intent intent = getIntent(StartCategoriesActivity.class);
        context.get().startActivity(intent);
    }

    public void openManageCategories() {
        Intent intent = getIntent(ManageCategoriesActivity.class);
        context.get().startActivity(intent);
    }

    public void openMonths(){
        Intent intent = getIntent(MonthsActivity.class);
        context.get().startActivity(intent);
    }

    public void openReminder(){
        Intent intent = getIntent(RemiderActivity.class);
        context.get().startActivity(intent);
    }

    public void openSelectDayMonth() {
        Intent intent = getIntent(SelectDayMonthActivity.class);
        context.get().startActivity(intent);
    }

    public void openNotifications() {
        Intent intent = getIntent(NotificationsActivity.class);
        context.get().startActivity(intent);
    }

    public void goToUp(){
        NavUtils.navigateUpFromSameTask(context.get());
    }
}
