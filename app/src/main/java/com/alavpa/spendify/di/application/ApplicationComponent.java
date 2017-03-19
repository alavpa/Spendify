package com.alavpa.spendify.di.application;

import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.Repository;

import java.text.SimpleDateFormat;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Repository repository();
    ResDatasource resDatasource();
    PrefsDatasource prefsDatasource();
    SimpleDateFormat simpleDateFormat();
}
