package com.alavpa.spendify.di.base;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.ui.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alavpa on 10/02/17.
 */
@Module
public class BaseModule {

    private
    final BaseActivity activity;

    public BaseModule(BaseActivity activity){
        this.activity = activity;
    }

    @Provides
    @PerActivity
    public BaseActivity providesActivity(){
        return this.activity;
    }
}
