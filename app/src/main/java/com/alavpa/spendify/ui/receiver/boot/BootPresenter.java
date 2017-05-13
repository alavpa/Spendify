package com.alavpa.spendify.ui.receiver.boot;

import com.alavpa.spendify.domain.usecases.GetAlarms;
import com.alavpa.spendify.domain.usecases.GetAmount;
import com.alavpa.spendify.domain.usecases.SetAlarms;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class BootPresenter {

    SetAlarms setAlarms;
    @Inject
    public BootPresenter(SetAlarms setAlarms, GetAlarms getAlarms){
        this.setAlarms = setAlarms;
    }

    public void resetAlarms(){
        setAlarms.execute();
    }
}
