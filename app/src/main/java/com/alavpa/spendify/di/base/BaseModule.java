package com.alavpa.spendify.di.base;

import android.app.Activity;

import com.alavpa.spendify.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alavpa on 10/02/17.
 */
@Module
public class BaseModule {

    private
    final Activity activity;

    public BaseModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity providesActivity(){
        return this.activity;
    }
}
