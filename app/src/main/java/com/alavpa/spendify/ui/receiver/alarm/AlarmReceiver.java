package com.alavpa.spendify.ui.receiver.alarm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.alavpa.spendify.Application;
import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.ui.IntentUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmReceiver extends WakefulBroadcastReceiver {

    @Inject
    public AlarmPresenter presenter;

    @Inject
    public IntentUtils intentUtils;

    @Override
    public void onReceive(Context context, Intent intent) {

        inject(context);
        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDDAY)) {

            AlarmEndDay alarmEndDay = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmEndDay(alarmEndDay);

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDMONTH)) {

            AlarmEndMonth alarmEndMonth = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmEndMonth(alarmEndMonth);

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_AMOUNT)) {

            AlarmAmount alarmAmount = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmAmount(alarmAmount);

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_OFFLIMIT)) {

            AlarmOfflimit alarmOfflimit = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmOfflimit(alarmOfflimit);
        }
    }

    public void inject(Context context) {
        getApplicationComponent(context)
                .inject(this);

    }

    public ApplicationComponent getApplicationComponent(Context context) {
        return ((Application) context.getApplicationContext()).getApplicationComponent();
    }


}
