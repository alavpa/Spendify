package com.alavpa.spendify.domain.di.activity;

import android.app.Activity;

import com.alavpa.spendify.domain.di.PerActivity;
import com.alavpa.spendify.domain.di.application.ApplicationComponent;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
