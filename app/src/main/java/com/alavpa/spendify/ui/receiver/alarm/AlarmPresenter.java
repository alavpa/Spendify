package com.alavpa.spendify.ui.receiver.alarm;

import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.InsertOrUpdateAmount;
import com.alavpa.spendify.domain.usecases.SendEndDayNotification;
import com.alavpa.spendify.domain.usecases.SendEndMonthNotification;
import com.alavpa.spendify.domain.usecases.SendOfflimitNotification;
import com.alavpa.spendify.domain.usecases.SetAlarmEndDay;
import com.alavpa.spendify.domain.usecases.SetAlarmEndMonth;

import javax.inject.Inject;
import javax.inject.Singleton;

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

    private SetAlarmEndDay setAlarmEndDay;

    private SetAlarmEndMonth setAlarmEndMonth;

    private InsertOrUpdateAmount insertOrUpdateAmount;

    @Inject
    public AlarmPresenter(SendEndDayNotification sendEndDayNotification,
                          SendEndMonthNotification sendEndMonthNotification,
                          SendOfflimitNotification sendOfflimitNotification,
                          SetAlarmEndDay setAlarmEndDay,
                          SetAlarmEndMonth setAlarmEndMonth,
                          InsertOrUpdateAmount insertOrUpdateAmount){
        this.sendEndDayNotification = sendEndDayNotification;
        this.sendEndMonthNotification = sendEndMonthNotification;
        this.sendOfflimitNotification = sendOfflimitNotification;
        this.setAlarmEndDay = setAlarmEndDay;
        this.setAlarmEndMonth = setAlarmEndMonth;
        this.insertOrUpdateAmount = insertOrUpdateAmount;
    }

    public void onReceiveAlarmEndDay(AlarmEndDay alarmEndDay) {
        if(preferences.notifyEndOfDay()) {

            sendEndDayNotification.execute();
            setAlarmEndDay.execute(alarmEndDay.getNextAlarm());
        }
    }

    public void onReceiveAlarmEndMonth(AlarmEndMonth alarmEndMonth) {
        if(preferences.notifyEndOfMonth()) {

            long from = dateUtils.calculateFrom(alarmEndMonth.getDate(), preferences.getMonthDay()).getTimeInMillis();
            sendEndMonthNotification.setFrom(from);
            sendEndMonthNotification.execute();

            setAlarmEndMonth.execute(alarmEndMonth.getNextAlarm());
        }
    }

    public void onReceiveAlarmOfflimit(AlarmOfflimit alarmOfflimit) {

        if(preferences.notifyOfflimit()){

            sendOfflimitNotification.setAlarmOfflimit(alarmOfflimit);
            sendOfflimitNotification.execute();
        }

    }

    public void onReceiveAlarmAmount(AlarmAmount alarmAmount) {
        Amount amount = alarmAmount.getAmount().getNextAmount();
        insertOrUpdateAmount.setAmount(amount);
        insertOrUpdateAmount.execute(new DisposableSingleObserver<Amount>() {
            @Override
            public void onSuccess(Amount amount) {
                insertOrUpdateAmount.dispose();
            }

            @Override
            public void onError(Throwable e) {
                insertOrUpdateAmount.dispose();
            }
        });
    }
}
