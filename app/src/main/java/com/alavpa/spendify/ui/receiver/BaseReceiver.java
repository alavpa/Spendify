package com.alavpa.spendify.ui.receiver;

import android.content.Context;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.alavpa.spendify.Application;
import com.alavpa.spendify.di.application.ApplicationComponent;

public abstract class BaseReceiver extends WakefulBroadcastReceiver {

    public ApplicationComponent getApplicationComponent(Context context) {
        return ((Application) context.getApplicationContext()).getApplicationComponent();
    }

}
