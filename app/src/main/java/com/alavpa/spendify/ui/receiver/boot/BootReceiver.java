package com.alavpa.spendify.ui.receiver.boot;


import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.ui.receiver.BaseReceiver;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BootReceiver extends BaseReceiver{

    @Inject
    BootPresenter presenter;

    @Override
    public void onReceive(Context context, Intent intent) {
        getApplicationComponent(context).inject(this);

        //TODO: Reschedule alarms
        presenter.configureEndDayAlarm();
        presenter.configureEndMonthAlarm();
        presenter.configureAmountAlarm();
    }
}
