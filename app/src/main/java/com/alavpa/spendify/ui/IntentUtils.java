package com.alavpa.spendify.ui;

import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.ui.dashboard.DashboardActivity;
import com.alavpa.spendify.ui.main.MainActivity;

import javax.inject.Inject;

public class IntentUtils {

    @Inject
    public IntentUtils(){

    }

    public Intent getIntent(Context context, Class activityClass){
        Intent intent = new Intent(context, activityClass);
        return intent;
    }

    public Intent getMainIntent(Context context){
        return getIntent(context,MainActivity.class);
    }

    public Intent getDashboardIntent(Context context, long month) {
        Intent intent = getIntent(context,DashboardActivity.class);
        intent.putExtra(Navigator.EXTRA_FROM,month);
        return intent;
    }
}
