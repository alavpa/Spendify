package com.alavpa.spendify.domain.di.activity;

import android.app.Activity;

import com.alavpa.spendify.domain.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alavpa on 10/02/17.
 */
@Module
public class ActivityModule {
    private
    final Activity activity;

    public ActivityModule(Activity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public Activity providesActivity(){
        return this.activity;
    }
}
