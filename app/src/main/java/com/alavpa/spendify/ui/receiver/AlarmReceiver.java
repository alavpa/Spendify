package com.alavpa.spendify.ui.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.alavpa.spendify.Application;
import com.alavpa.spendify.R;
import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.di.application.ApplicationComponent;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.IntentUtils;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AlarmReceiver extends WakefulBroadcastReceiver {

    public static final int NOTIFICATION_ENDDAY_ID = 1;
    public static final int NOTIFICATION_ENDMONTH_ID = 2;
    public static final int NOTIFICATION_OFFLIMIT_BASE = 1000;

    @Inject
    ResDatasource resources;

    @Inject
    AlarmManager alarmManager;

    @Inject
    IntentUtils intentUtils;

    @Override
    public void onReceive(Context context, Intent intent) {

        inject(context);
        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDDAY)) {

            String title = resources.getString(R.string.notification_title_endday);
            String content = resources.getString(R.string.notification_content_endday);
            Intent mainIntent = intentUtils.getMainIntent(context);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    NOTIFICATION_ENDDAY_ID,
                    mainIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            publish(context, NOTIFICATION_ENDDAY_ID, title, content, pendingIntent);
        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_ENDMONTH)) {

            String title = resources.getString(R.string.notification_title_endmonth);
            String content = resources.getString(R.string.notification_content_endmonth);
            Intent dashboardIntent = intentUtils.getDashboardIntent(context, Calendar.getInstance().getTimeInMillis());
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    NOTIFICATION_ENDMONTH_ID,
                    dashboardIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            publish(context, NOTIFICATION_ENDMONTH_ID, title, content, pendingIntent);

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_AMOUNT)) {

            //TODO: Insert amount from intent

        }

        if (intent.getAction().equals(AlarmManager.ACTION_ALARM_OFFLIMIT)) {

            AlarmOfflimit alarmOfflimit = intent.getParcelableExtra(AlarmManager.EXTRA_ALARM_PARCELABLE);

            Category category = alarmOfflimit.getCategory();
            int notificationId = NOTIFICATION_OFFLIMIT_BASE + (int)category.getId();

            String title = resources.getString(R.string.notification_title_offlimit,category.getName());
            String content = resources.getString(R.string.notification_content_offlimit, category.getLimit(),
                    category.getName());
            Intent dashboardIntent = intentUtils.getDashboardIntent(context, Calendar.getInstance().getTimeInMillis());
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    notificationId,
                    dashboardIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            publish(context, notificationId, title, content, pendingIntent);

        }
    }

    private void publish(Context context, int id, String title, String content, PendingIntent pendingIntent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.ic_notification_b);
        builder.setAutoCancel(true);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));
        Notification notification = builder.build();
        notify(context, id, notification);
    }

    private void notify(Context context, int id, Notification notification) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);

    }

    public void inject(Context context) {
        getApplicationComponent(context)
                .inject(this);

    }

    public ApplicationComponent getApplicationComponent(Context context) {
        return ((Application) context.getApplicationContext()).getApplicationComponent();
    }


}
