package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.notifications.NotificationManager;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.ui.IntentUtils;

import javax.inject.Inject;

public class SendEndDayNotification {

    private NotificationManager notificationManager;
    private ResDatasource resources;
    private IntentUtils intentUtils;

    @Inject
    public SendEndDayNotification(NotificationManager notificationManager,
                                  ResDatasource resources,
                                  IntentUtils intentUtils){

        this.notificationManager = notificationManager;
        this.resources = resources;
        this.intentUtils = intentUtils;
    }

    public void execute(){

        notificationManager.publishEndDay(intentUtils,
                resources.getString(R.string.notification_title_endday),
                resources.getString(R.string.notification_content_endday));
    }
}
