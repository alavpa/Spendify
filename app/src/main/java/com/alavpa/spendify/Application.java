package com.alavpa.spendify;

import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.application.ApplicationModule;
import com.alavpa.spendify.di.application.DaggerApplicationComponent;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by alavpa on 10/02/17.
 */

public class Application extends android.app.Application {

    ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
