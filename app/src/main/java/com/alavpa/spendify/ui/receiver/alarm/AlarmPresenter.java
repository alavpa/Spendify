package com.alavpa.spendify.ui.receiver.alarm;

import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.GetAmount;
import com.alavpa.spendify.domain.usecases.InsertOrUpdateAmount;
import com.alavpa.spendify.domain.usecases.InsertOrUpdateNextAmount;
import com.alavpa.spendify.domain.usecases.SendEndDayNotification;
import com.alavpa.spendify.domain.usecases.SendEndMonthNotification;
import com.alavpa.spendify.domain.usecases.SendOfflimitNotification;
import com.alavpa.spendify.domain.usecases.SetAlarm;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.SingleSource;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

@Singleton
public class AlarmPresenter {

    @Inject
    public DateUtils dateUtils;

    @Inject
    public PrefsDatasource preferences;

    private SendEndMonthNotification sendEndMonthNotification;

    private SendEndDayNotification sendEndDayNotification;

    private SendOfflimitNotification sendOfflimitNotification;

    private SetAlarm setAlarm;

    private InsertOrUpdateNextAmount insertOrUpdateNextAmount;

    @Inject
    public AlarmPresenter(SendEndDayNotification sendEndDayNotification,
                          SendEndMonthNotification sendEndMonthNotification,
                          SendOfflimitNotification sendOfflimitNotification,
                          SetAlarm setAlarm,
                          InsertOrUpdateNextAmount insertOrUpdateNextAmount){
        this.sendEndDayNotification = sendEndDayNotification;
        this.sendEndMonthNotification = sendEndMonthNotification;
        this.sendOfflimitNotification = sendOfflimitNotification;
        this.setAlarm = setAlarm;
        this.insertOrUpdateNextAmount = insertOrUpdateNextAmount;
    }

    public void onReceiveAlarmEndDay(AlarmEndDay alarmEndDay) {
        if(preferences.notifyEndOfDay()) {

            sendEndDayNotification.execute();
            setAlarm.setAlarm(alarmEndDay.getNextAlarm());
            setAlarm.execute();
        }
    }

    public void onReceiveAlarmEndMonth(AlarmEndMonth alarmEndMonth) {
        if(preferences.notifyEndOfMonth()) {

            long from = dateUtils.calculateFrom(alarmEndMonth.getPeriod().getDate(),
                    preferences.getMonthDay()).getTimeInMillis();

            sendEndMonthNotification.setFrom(from);
            sendEndMonthNotification.execute();

            setAlarm.setAlarm(alarmEndMonth.getNextAlarm());
            setAlarm.execute();
        }
    }

    public void onReceiveAlarmOfflimit(AlarmOfflimit alarmOfflimit) {

        if(preferences.notifyOfflimit()){

            sendOfflimitNotification.setAlarmOfflimit(alarmOfflimit);
            sendOfflimitNotification.execute();
        }

    }

    public void onReceiveAlarmAmount(AlarmAmount alarmAmount) {

        insertOrUpdateNextAmount.setId(alarmAmount.getRefId());

        insertOrUpdateNextAmount.execute(new DisposableSingleObserver<Amount>() {
            @Override
            public void onSuccess(Amount amount) {
                insertOrUpdateNextAmount.dispose();
            }

            @Override
            public void onError(Throwable e) {
                insertOrUpdateNextAmount.dispose();
            }
        });
    }
}
