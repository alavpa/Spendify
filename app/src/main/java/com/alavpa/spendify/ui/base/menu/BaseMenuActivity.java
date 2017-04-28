package com.alavpa.spendify.ui.base.menu;

import android.support.annotation.LayoutRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.ui.base.BaseActivity;
import com.alavpa.spendify.ui.menu.MenuFragment;

/**
 * Created by alavpa on 10/02/17.
 */

@PerActivity
public class BaseMenuActivity extends BaseActivity implements BaseMenuView {

    public Toolbar toolbar;
    public DrawerLayout drawerLayout;
    MenuFragment menuFragment;
    private ActionBarDrawerToggle toggle;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base_toolbar);
        FrameLayout content = (FrameLayout)findViewById(R.id.content);
        initMenu();

        LayoutInflater.from(this).inflate(layoutResID,content,true);
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

    @Override
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
