package com.alavpa.spendify.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alavpa.spendify.Application;
import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.base.BaseModule;
import com.alavpa.spendify.ui.Navigator;

import javax.inject.Inject;

/**
 * Created by alavpa on 10/02/17.
 */

@PerActivity
public class BaseActivity extends AppCompatActivity implements BaseView{

    @Inject
    protected
    Navigator navigator;

    public Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);
        FrameLayout content = (FrameLayout)findViewById(R.id.content);
        LayoutInflater.from(this).inflate(layoutResID,content,true);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getApplicationComponent()
                .inject(this);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public ApplicationComponent getApplicationComponent(){
        return ((Application)getApplication()).getApplicationComponent();
    }

    public BaseModule getBaseModule(){
        return new BaseModule(this);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

}
