package com.alavpa.spendify.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.alavpa.spendify.Application;
import com.alavpa.spendify.di.activity.ActivityComponent;
import com.alavpa.spendify.di.activity.ActivityModule;
import com.alavpa.spendify.di.activity.DaggerActivityComponent;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.di.base.BaseModule;
import com.alavpa.spendify.di.base.DaggerBaseComponent;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.ui.custom.dialogs.LoaderDialog;

/**
 * Created by alavpa on 10/02/17.
 */

@PerActivity
public class BaseActivity extends AppCompatActivity implements BaseView{


    private LoaderDialog loaderDialog;
    private BasePresenter basePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loaderDialog = LoaderDialog.getInstance();
        DaggerBaseComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(new BaseModule(this))
                .build()
                .inject(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        basePresenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dismissLoader();
        basePresenter.detachView();
    }

    public ApplicationComponent getApplicationComponent(){
        return ((Application)getApplication()).getApplicationComponent();
    }

    public BaseModule getBaseModule(){
        return new BaseModule(this);
    }

    @Override
    public void showError(String message) {
        Log.e(getClass().getSimpleName(),message);
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showLoader() {
        if (!loaderDialog.isVisible()) {
            loaderDialog.show(getSupportFragmentManager(), LoaderDialog.class.getName());
        }
    }

    @Override
    public void dismissLoader() {
        if (loaderDialog.isVisible()) {
            loaderDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loaderDialog = null;
    }

    protected void setPresenter(BasePresenter basePresenter){
        this.basePresenter = basePresenter;
    }

    protected ActivityComponent getActivityComponent(){
        return DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .baseModule(getBaseModule())
                .activityModule(new ActivityModule())
                .build();
    }

}
