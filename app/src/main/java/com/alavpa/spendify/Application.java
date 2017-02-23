package com.alavpa.spendify;

import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.application.ApplicationModule;
import com.alavpa.spendify.di.application.DaggerApplicationComponent;

/**
 * Created by alavpa on 10/02/17.
 */

public class Application extends android.app.Application {

    ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
