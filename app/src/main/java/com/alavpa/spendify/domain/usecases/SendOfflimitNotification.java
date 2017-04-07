package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.notifications.NotificationManager;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.ui.IntentUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SendOfflimitNotification {

    private NotificationManager notificationManager;
    private ResDatasource resources;
    private IntentUtils intentUtils;

    private AlarmOfflimit alarmOfflimit;

    @Inject
    public SendOfflimitNotification(NotificationManager notificationManager,
                                    ResDatasource resources,
                                    IntentUtils intentUtils){

        this.notificationManager = notificationManager;
        this.resources = resources;
        this.intentUtils = intentUtils;
    }

    public void setAlarmOfflimit(AlarmOfflimit alarmOfflimit) {
        this.alarmOfflimit = alarmOfflimit;
    }

    public void execute(){

        int notificationId = alarmOfflimit.getRequest();
        notificationManager.publishOfflimit(notificationId,
                resources.getString(R.string.notification_title_offlimit,alarmOfflimit.getCategory().getName()),
                resources.getString(R.string.notification_content_offlimit,alarmOfflimit.getCategory().getLimit(),
                        alarmOfflimit.getCategory().getName()));

    }
}
