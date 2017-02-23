package com.alavpa.spendify.di.base;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.ui.base.BaseActivity;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BaseModule.class})
public interface BaseComponent {
    BaseActivity activity();
}
