package com.alavpa.spendify.di.application;

import android.content.Context;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.DbDatasource;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.RepositoryData;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

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
    public Repository provideRepository(RepositoryData repository){
        return repository;
    }

    @Provides
    @Singleton
    public Datasource provideDatasource(DbDatasource datasource){
        return datasource;
    }

    @Provides
    @Singleton
    public ResDatasource provideResDatasource(){
        return new ResDatasource(context);
    }

    @Provides
    @Singleton
    public SimpleDateFormat provideSimpleDateFormat(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(context.getString(R.string.date_format));
        return simpleDateFormat;
    }

    @Provides
    @Singleton
    public DecimalFormat provideDecimalFormat(){
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(2);
        decimalFormat.setMinimumFractionDigits(2);
        return decimalFormat;
    }
}
