package com.alavpa.spendify.di.base;

import android.app.Activity;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.base.BaseFragment;
import com.alavpa.spendify.ui.base.toolbar.BaseToolbarActivity;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BaseModule.class})
public interface BaseComponent {
    void inject(BaseToolbarActivity baseActivity);
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    Activity activity();
    Navigator navigator();
}
