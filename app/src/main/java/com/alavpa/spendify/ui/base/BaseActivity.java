package com.alavpa.spendify.ui.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.alavpa.spendify.ui.menu.MenuFragment;

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
    public DrawerLayout drawerLayout;
    MenuFragment menuFragment;
    int position;
    private ActionBarDrawerToggle toggle;


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
        initMenu();
    }

    public void initMenu(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        menuFragment = MenuFragment.getInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.menu,menuFragment).commit();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.addDrawerListener(toggle);
        drawerLayout.setStatusBarBackground(R.color.colorPrimary);
        toggle.syncState();

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

    public void hideMenu(){
        drawerLayout.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            hideMenu();
        } else {
            super.onBackPressed();
        }
    }
}
