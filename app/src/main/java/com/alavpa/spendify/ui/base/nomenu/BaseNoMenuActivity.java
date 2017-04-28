package com.alavpa.spendify.ui.base.nomenu;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.ui.base.BaseActivity;

/**
 * Created by alavpa on 10/02/17.
 */

@PerActivity
public class BaseNoMenuActivity extends BaseActivity implements BaseNoMenuView{

    public Toolbar toolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base);
        FrameLayout content = (FrameLayout)findViewById(R.id.content);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater.from(this).inflate(layoutResID,content,true);
    }
}
