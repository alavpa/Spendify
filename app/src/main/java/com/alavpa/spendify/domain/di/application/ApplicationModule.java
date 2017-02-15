package com.alavpa.spendify.domain.di.application;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by alavpa on 10/02/17.
 */
@Module
public class ApplicationModule {
    Context context;

    public ApplicationModule(Context context){
        this.context = context;
    }


    @Provides
    @Singleton
    public Context provideContext(){
        return context;
    }
}
