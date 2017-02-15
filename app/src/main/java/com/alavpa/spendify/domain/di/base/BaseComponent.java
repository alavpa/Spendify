package com.alavpa.spendify.domain.di.base;

import com.alavpa.spendify.domain.di.PerActivity;
import com.alavpa.spendify.domain.di.activity.ActivityModule;
import com.alavpa.spendify.domain.di.application.ApplicationComponent;
import com.alavpa.spendify.ui.details.DetailsActivity;
import com.alavpa.spendify.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, BaseModule.class})
public interface BaseComponent {
    void inject(MainActivity mainActivity);
    void inject(DetailsActivity detailsActivity);
}
