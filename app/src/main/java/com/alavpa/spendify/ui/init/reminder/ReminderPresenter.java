package com.alavpa.spendify.ui.init.reminder;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.domain.usecases.CancelAlarmEndDay;
import com.alavpa.spendify.domain.usecases.CancelAlarmEndMonth;
import com.alavpa.spendify.domain.usecases.SetAlarmEndDay;
import com.alavpa.spendify.domain.usecases.SetAlarmEndMonth;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

@PerActivity
public class ReminderPresenter extends BasePresenter<ReminderView> {


    @Inject
    SimpleDateFormat simpleDateFormat;

    SetAlarmEndDay setAlarmEndDay;
    CancelAlarmEndDay cancelAlarmEndDay;

    SetAlarmEndMonth setAlarmEndMonth;
    CancelAlarmEndMonth cancelAlarmEndMonth;

    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
    Calendar calendar = Calendar.getInstance();

    @Inject
    public ReminderPresenter(SetAlarmEndDay setAlarmEndDay,
                             CancelAlarmEndDay cancelAlarmEndDay,
                             SetAlarmEndMonth setAlarmEndMonth,
                             CancelAlarmEndMonth cancelAlarmEndMonth) {
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
        getView().showPromises(preferences.notifyPromises());
        showPromisesPeriod();
    }

    private void showEndOfDayTime() {
        Calendar time = preferences.getEndOfDayTime();
        setEndDayTime(time);
    }

    private void showPromisesPeriod() {
        Period period = preferences.getNotifyPromisesPeriod();
        getView().showPromisesPeriod(period);
    }

    public void onApply(boolean notifyEndDay,
                        boolean notifyEndMonth,
                        boolean notifyOfflimit,
                        boolean notifyPromises,
                        String time,
                        Period period,
                        boolean save) {

        preferences.setNotifyEndOfMonth(notifyEndMonth);
        if(notifyEndMonth){
            setAlarmEndMonth.execute();
        }else{
            cancelAlarmEndMonth.execute();
        }

        preferences.setNotifyOfflimit(notifyOfflimit);
        preferences.setNotifyPromises(notifyPromises);

        preferences.setNotifyEndOfDay(notifyEndDay);
        if (notifyEndDay && time != null) {
            try {

                Date timeEndDay = simpleTimeFormat.parse(time);
                calendar.setTime(timeEndDay);

                preferences.setEndOfDayTime(calendar);

                setAlarmEndDay.setTime(calendar);
                setAlarmEndDay.execute();

            } catch (ParseException e) {
                getView().showError(e.getMessage());
                return;
            }
        }else{
            alarmManager.cancelAlarmEndDay();
        }

        if (period != null) {
            preferences.setNotifyPromisesPeriod(period);
        }

        if(save) {
            getView().finish();
        }else{
            preferences.setInitialized(true);
            navigator.openMain();
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
}
