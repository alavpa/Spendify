package com.alavpa.spendify.di.base;

import android.app.Activity;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.application.ApplicationComponent;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BaseModule.class})
public interface BaseComponent {
    Activity activity();
}
