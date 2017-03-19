package com.alavpa.spendify.ui.init.reminder;

import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

@PerActivity
public class ReminderPresenter extends BasePresenter<ReminderView>{

    SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("HH:mm");

    @Inject
    public ReminderPresenter(){

    }

    public void initViews() {
        getView().showEndOfDay(preferences.notifyEndOfDay());
        showEndOfDayTime();
        getView().showEndOfMonth(preferences.notifyEndOfMonth());
        getView().showOfflimit(preferences.notifyOfflimit());
        getView().showPromises(preferences.notifyPromises());
    }

    private void showEndOfDayTime(){
        Calendar time = preferences.getEndOfDayTime();
        getView().showEndOfDayTime(simpleTimeFormat.format(time.getTime()));
    }

    private void showPromisesPeriod(){

    }

    public void onApply() {
        navigator.openMain(new Amount());
    }
}
