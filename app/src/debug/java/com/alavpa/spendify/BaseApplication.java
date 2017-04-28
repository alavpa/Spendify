package com.alavpa.spendify;

import com.squareup.leakcanary.LeakCanary;

import timber.log.Timber;

/**
 * Created by alavpa on 9/04/17.
 */

public class BaseApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        Timber.plant(new Timber.DebugTree());

    }
}
