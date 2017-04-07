package com.alavpa.spendify.data.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;

import com.alavpa.spendify.R;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.ui.IntentUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

/*
 * Copyright (c) 2017 AXA Group Solutions.
 *
 * Licensed under the AXA Group Solutions License (the "License"); you
 * may not use this file except in compliance with the License.
 * A copy of the License can be found in the LICENSE.TXT file distributed
 * together with this file.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@Singleton
public class NotificationManager {

    private Context context;
    private NotificationManagerCompat notificationManager;
    private IntentUtils intentUtils;

    @Inject
    public NotificationManager(Context context, IntentUtils intentUtils){
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

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(title)
                .setContentText(content)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_notification_b)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context,R.color.colorPrimary))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(content));

        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }
}
