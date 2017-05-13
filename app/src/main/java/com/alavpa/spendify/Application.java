package com.alavpa.spendify;

import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.application.ApplicationModule;
import com.alavpa.spendify.di.application.DaggerApplicationComponent;
import com.alavpa.spendify.domain.usecases.SetAlarms;

import javax.inject.Inject;

/**
 * Created by alavpa on 10/02/17.
 */

public class Application extends BaseApplication {

    @Inject
    SetAlarms setAlarms;

    ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        setAlarms.execute();
    }


    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
