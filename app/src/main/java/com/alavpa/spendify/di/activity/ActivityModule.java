package com.alavpa.spendify.di.activity;

import com.alavpa.spendify.di.scopes.PerActivity;

import java.text.DecimalFormat;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alavpa on 10/02/17.
 */
@Module
public class ActivityModule {

    @PerActivity
    @Provides
    public DecimalFormat provideDecimalFormat(){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(2);
        decimalFormat.setMaximumFractionDigits(2);
        return decimalFormat;
    }

}
