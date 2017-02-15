package com.alavpa.spendify.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.alavpa.spendify.Application;
import com.alavpa.spendify.domain.di.activity.ActivityModule;
import com.alavpa.spendify.domain.di.application.ApplicationComponent;

/**
 * Created by alavpa on 10/02/17.
 */

public class BaseActivity extends AppCompatActivity implements BaseView{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent()
                .inject(this);
    }

    protected ApplicationComponent getApplicationComponent(){
        return ((Application)getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule(){
        return new ActivityModule(this);
    }

}
