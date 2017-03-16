package com.alavpa.spendify.di.activity;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.base.BaseModule;
import com.alavpa.spendify.ui.category.add.AddCategoryActivity;
import com.alavpa.spendify.ui.dashboard.DashboardActivity;
import com.alavpa.spendify.ui.dashboard.details.DashboardDetailsActivity;
import com.alavpa.spendify.ui.details.DetailsActivity;
import com.alavpa.spendify.ui.main.MainActivity;
import com.alavpa.spendify.ui.menu.MenuFragment;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BaseModule.class, ActivityModule.class})
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(DetailsActivity detailsActivity);
    void inject(MenuFragment menuFragment);
    void inject(DashboardActivity dashboardActivity);
    void inject(AddCategoryActivity addCategoryActivity);
    void inject(DashboardDetailsActivity dashboardDetailsActivity);
}
