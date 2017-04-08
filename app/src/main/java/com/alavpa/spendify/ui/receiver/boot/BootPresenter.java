package com.alavpa.spendify.ui.receiver.boot;

import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.RestoreRepeatAlarms;
import com.alavpa.spendify.domain.usecases.SetAlarmAmount;
import com.alavpa.spendify.domain.usecases.SetAlarmEndDay;
import com.alavpa.spendify.domain.usecases.SetAlarmEndMonth;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.observers.DisposableSingleObserver;

@Singleton
public class BootPresenter {

    @Inject
    PrefsDatasource preferences;

    private SetAlarmEndDay setAlarmEndDay;

    private SetAlarmEndMonth setAlarmEndMonth;

    private SetAlarmAmount setAlarmAmount;

    private RestoreRepeatAlarms restoreRepeatAlarms;

    @Inject
    public BootPresenter(SetAlarmEndDay setAlarmEndDay,
                         SetAlarmEndMonth setAlarmEndMonth,
                         SetAlarmAmount setAlarmAmount,
                         RestoreRepeatAlarms restoreRepeatAlarms){

        this.setAlarmEndDay = setAlarmEndDay;
        this.setAlarmEndMonth = setAlarmEndMonth;
        this.setAlarmAmount = setAlarmAmount;
        this.restoreRepeatAlarms = restoreRepeatAlarms;
    }

    public void configureEndDayAlarm() {
        if(preferences.notifyEndOfDay()){
            setAlarmEndDay.setTime(preferences.getEndOfDayTime().getTimeInMillis());
            setAlarmEndDay.execute();
        }
    }

    public void configureEndMonthAlarm() {

        if(preferences.notifyEndOfMonth()){
            setAlarmEndMonth.setDay(preferences.getMonthDay());
            setAlarmEndMonth.execute();
        }
    }

    public void configureAmountAlarm(){
        restoreRepeatAlarms.setCurrentTime(Calendar.getInstance().getTimeInMillis());
        restoreRepeatAlarms.setSetAlarmAmount(setAlarmAmount);
        restoreRepeatAlarms.execute(new DisposableSingleObserver<List<Amount>>() {
            @Override
            public void onSuccess(List<Amount> amounts) {
                restoreRepeatAlarms.dispose();
            }

            @Override
            public void onError(Throwable e) {
                restoreRepeatAlarms.dispose();
            }
        });
    }
}
