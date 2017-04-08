package com.alavpa.spendify.di.application;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.ui.receiver.alarm.AlarmReceiver;
import com.alavpa.spendify.ui.receiver.boot.BootReceiver;

import java.text.SimpleDateFormat;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by alavpa on 10/02/17.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(AlarmReceiver alarmReceiver);

    Repository repository();
    ResDatasource resDatasource();
    PrefsDatasource prefsDatasource();
    SimpleDateFormat simpleDateFormat();
    AlarmManager alarmManager();
    DateUtils dateUtils();

    void inject(BootReceiver bootReceiver);
}
