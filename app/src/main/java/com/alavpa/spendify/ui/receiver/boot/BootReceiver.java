package com.alavpa.spendify.ui.receiver.boot;


import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.ui.receiver.BaseReceiver;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class BootReceiver extends BaseReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Timber.d("BootReceiver");
    }
}
