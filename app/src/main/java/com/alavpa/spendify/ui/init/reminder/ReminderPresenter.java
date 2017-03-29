package com.alavpa.spendify.ui.init.reminder;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
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
    public SimpleDateFormat simpleDateFormat;

    private
    SetAlarmEndDay setAlarmEndDay;
    private
    CancelAlarmEndDay cancelAlarmEndDay;

    private
    SetAlarmEndMonth setAlarmEndMonth;
    private
    CancelAlarmEndMonth cancelAlarmEndMonth;

    private
    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");
    private
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

        try {

            if(time!=null) {
                Date timeEndDay = simpleTimeFormat.parse(time);
                calendar.setTime(timeEndDay);
                preferences.setEndOfDayTime(calendar);
                preferences.setNotifyEndOfDay(notifyEndDay);

                AlarmEndDay alarmEndDay = new AlarmEndDay(calendar.getTimeInMillis());
                if (notifyEndDay) {
                    setAlarmEndDay.setAlarmEndDay(alarmEndDay);
                    setAlarmEndDay.execute();
                } else {
                    cancelAlarmEndDay.setAlarmEndDay(alarmEndDay);
                    cancelAlarmEndDay.execute();
                }
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

        AlarmEndMonth alarmEndMonth = new AlarmEndMonth(preferences.getMonthDay());
        setAlarmEndMonth.setAlarmEndMonth(alarmEndMonth);

        if(notifyEndMonth){
            setAlarmEndMonth.execute();
        }else{
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
