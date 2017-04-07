package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.notifications.NotificationManager;
import com.alavpa.spendify.data.resources.ResDatasource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SendEndMonthNotification {

    private NotificationManager notificationManager;
    private ResDatasource resources;

    private long from;

    @Inject
    public SendEndMonthNotification(NotificationManager notificationManager,
                                    ResDatasource resources){

        this.notificationManager = notificationManager;
        this.resources = resources;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void execute(){

        notificationManager.publishEndMonth(resources.getString(R.string.notification_title_endmonth),
                resources.getString(R.string.notification_content_endmonth),
                from);
    }
}
