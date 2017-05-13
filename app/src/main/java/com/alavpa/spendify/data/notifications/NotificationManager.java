package com.alavpa.spendify.data.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.qualifiers.ApplicationContext;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.ui.utils.IntentUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationManager {

    private static final int LED_ON = 1000;
    private static final int LED_OFF = 1000;

    private Context context;
    private NotificationManagerCompat notificationManager;
    private IntentUtils intentUtils;

    @Inject
    public NotificationManager(@ApplicationContext Context context, IntentUtils intentUtils) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
        this.intentUtils = intentUtils;
    }

    public void publishEndDay(IntentUtils intentUtils, String title, String content){

        PendingIntent pendingIntent = intentUtils.getEndDayPendingIntent();
        publish(AlarmEndDay.NOTIFICATION_ENDDAY_ID,title,content,pendingIntent);
    }

    public void publishEndMonth(String title, String content, long from){

        PendingIntent pendingIntent = intentUtils.getEndMonthPendingIntent(from);
        publish(AlarmEndMonth.NOTIFICATION_ENDMONTH_ID,title,content,pendingIntent);
    }

    public void publishOfflimit(int id,String title, String content) {
        PendingIntent pendingIntent = intentUtils.getOfflimitPendingIntent(id);
        publish(id,title,content, pendingIntent);
    }

    private void publish(int id, String title, String content,PendingIntent pendingIntent) {

        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notification_b)
                .setLights(ContextCompat.getColor(context, R.color.colorPrimary), LED_ON, LED_OFF)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setSound(soundUri)
                .setVibrate(new long[]{1000, 500, 1000})
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content));

        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }
}
