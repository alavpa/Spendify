package com.alavpa.spendify.di.application;

import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.ui.Navigator;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.base.BaseFragment;
import com.alavpa.spendify.ui.base.toolbar.BaseToolbarActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseToolbarActivity baseActivity);
    void inject(BaseActivity baseActivity);
    void inject(BaseFragment baseFragment);

    Navigator navigator();
    Repository repository();
    ResDatasource resDatasource();
    DecimalFormat decimalFormat();
    SimpleDateFormat simpleDateFormat();
}
