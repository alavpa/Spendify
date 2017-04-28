package com.alavpa.spendify.di.base;

import android.app.Activity;
import android.content.Context;

import com.alavpa.spendify.di.qualifiers.ActivityContext;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alavpa on 10/02/17.
 */
@Module
public class BaseModule {

    private final Activity context;

    public BaseModule(Activity context) {
        this.context = context;
    }

    @Provides
    @ActivityContext
    public Context providesContext() {
        return this.context;
    }

    @Provides
    public Activity providesActivity(){
        return context;
    }
}
