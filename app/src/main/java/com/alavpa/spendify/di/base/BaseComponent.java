package com.alavpa.spendify.di.base;

import android.app.Activity;
import android.content.Context;

import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.qualifiers.ActivityContext;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.base.BaseFragment;
import com.alavpa.spendify.ui.base.menu.BaseMenuActivity;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {BaseModule.class})
public interface BaseComponent {
    void inject(BaseMenuActivity baseMenuActivity);
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    @ActivityContext
    Context context();
    Activity activity();
    Navigator navigator();
}
