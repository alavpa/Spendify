package com.alavpa.spendify.ui.init.reminder;

import android.text.TextUtils;

import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.domain.usecases.CancelAlarm;
import com.alavpa.spendify.domain.usecases.SetAlarm;
import com.alavpa.spendify.ui.base.BasePresenter;
import com.alavpa.spendify.ui.utils.AlarmUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

@PerActivity
public class ReminderPresenter extends BasePresenter<ReminderView> {


    @Inject
    public AlarmUtils alarmUtils;

    @Inject
    public SimpleDateFormat simpleDateFormat;

    private SetAlarm setAlarmEndDay;
    private CancelAlarm cancelAlarmEndDay;

    private SetAlarm setAlarmEndMonth;
    private CancelAlarm cancelAlarmEndMonth;

    private
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
    private
    Calendar calendar = Calendar.getInstance();

    @Inject
    public ReminderPresenter(SetAlarm setAlarmEndDay,
                             CancelAlarm cancelAlarmEndDay,
                             SetAlarm setAlarmEndMonth,
                             CancelAlarm cancelAlarmEndMonth) {

        this.setAlarmEndDay = setAlarmEndDay;
        this.cancelAlarmEndDay = cancelAlarmEndDay;

        this.setAlarmEndMonth = setAlarmEndMonth;
        this.cancelAlarmEndMonth = cancelAlarmEndMonth;

    }

    public void initViews() {
        getView().showEndOfDay(preferences.notifyEndOfDay());
        showEndOfDayTime();
        getView().showEndOfMonth(preferences.notifyEndOfMonth());
        getView().showOfflimit(preferences.notifyOfflimit());
    }

    private void showEndOfDayTime() {
        Calendar time = preferences.getEndOfDayTime();
        setEndDayTime(time);
    }

    public void onApply(boolean notifyEndDay,
                        boolean notifyEndMonth,
                        boolean notifyOfflimit,
                        String time) {

        configureEndMonth(notifyEndMonth);

        configureOfflimit(notifyOfflimit);

        configureEndOfDay(notifyEndDay, time);
    }

    private void configureEndOfDay(boolean notifyEndDay, String time) {

        preferences.setNotifyEndOfDay(notifyEndDay);

        try {

            if (notifyEndDay && !TextUtils.isEmpty(time)) {

                Date timeEndDay = simpleTimeFormat.parse(time);
                calendar.setTime(timeEndDay);
                long timeInMillis = alarmUtils.calculateNextEndDay(calendar.getTimeInMillis());
                Alarm alarm = new AlarmEndDay(timeInMillis);
                setAlarmEndDay.setAlarm(alarm);
                setAlarmEndDay.execute();

            } else {
                cancelAlarmEndDay.setAlarm(new AlarmEndDay());
                cancelAlarmEndDay.execute();
            }

        } catch (ParseException e) {
            getView().showError(e.getMessage());
        }
    }

    private void configureOfflimit(boolean notifyOfflimit) {
        preferences.setNotifyOfflimit(notifyOfflimit);
    }

    private void configureEndMonth(boolean notifyEndMonth) {
        preferences.setNotifyEndOfMonth(notifyEndMonth);

        if(notifyEndMonth){
            long time = alarmUtils.calculateEndMonth(preferences.getMonthDay(),
                    preferences.getEndOfDayTime().getTimeInMillis());
            Alarm alarmEndMonth = new AlarmEndMonth(time);
            setAlarmEndMonth.setAlarm(alarmEndMonth);
            setAlarmEndMonth.execute();
        }else{
            cancelAlarmEndMonth.setAlarm(new AlarmEndMonth());
            cancelAlarmEndMonth.execute();
        }
    }

    public void showTimePickerDialog(String time) {
        try {
            Date date = simpleTimeFormat.parse(time);
            calendar.setTime(date);
            getView().showTimePickerDialog(calendar);
        } catch (ParseException e) {
            getView().showError(e.getMessage());
        }
    }

    public void setEndDayTime(Calendar calendar) {
        String date = simpleTimeFormat.format(calendar.getTime());
        getView().showEndOfDayTime(date);
    }

    public void goToNext(){
        preferences.setInitialized(true);
        navigator.openMain();
    }

    public void goToParent(){
        navigator.goToUp();
    }
}
