package com.alavpa.spendify.domain.di.application;

import com.alavpa.spendify.ui.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);
}
