package com.alavpa.spendify.ui.receiver.alarm;

import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.ui.utils.IntentUtils;
import com.alavpa.spendify.ui.receiver.BaseReceiver;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmReceiver extends BaseReceiver {

    @Inject
    public AlarmPresenter presenter;

    @Inject
    public IntentUtils intentUtils;

    @Override
    public void onReceive(Context context, Intent intent) {

        getApplicationComponent(context).inject(this);
        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDDAY)) {

            Alarm alarmEndDay = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmEndDay(alarmEndDay);

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDMONTH)) {

            Alarm alarmEndMonth = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmEndMonth(alarmEndMonth);

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_AMOUNT)) {

            Alarm alarmAmount = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmAmount(alarmAmount);

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_OFFLIMIT)) {

            Alarm alarmOfflimit = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);
            presenter.onReceiveAlarmOfflimit(alarmOfflimit);
        }
    }

}
