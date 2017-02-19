package com.alavpa.spendify.domain.di.application;

import android.content.Context;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.DbDatasource;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.RepositoryImpl;
import com.alavpa.spendify.ui.ResourceProvider;

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

    @Provides
    @Singleton
    public Repository provideRepository(RepositoryImpl repository){
        return repository;
    }

    @Provides
    @Singleton
    public Datasource provideDatasource(DbDatasource datasource){
        return datasource;
    }

    @Provides
    @Singleton
    public ResourceProvider provideResourceProvider(){
        return new ResourceProvider(context);
    }
}
