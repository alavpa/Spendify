package com.alavpa.spendify.ui;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.di.qualifiers.ApplicationContext;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.ui.dashboard.DashboardActivity;
import com.alavpa.spendify.ui.main.MainActivity;

import javax.inject.Inject;

public class IntentUtils {

    private Context context;

    @Inject
    public IntentUtils(@ApplicationContext Context context) {
        this.context = context;
    }

    public Intent getIntent(Class activityClass) {
        Intent intent = new Intent(context, activityClass);
        return intent;
    }

    public Intent getMainIntent() {
        return getIntent(MainActivity.class);
    }

    public Intent getDashboardIntent(long month) {
        Intent intent = getIntent(DashboardActivity.class);
        intent.putExtra(Navigator.EXTRA_FROM, month);
        return intent;
    }

    public PendingIntent getEndMonthPendingIntent(long from) {
        Intent mainIntent = getDashboardIntent(from);
        return PendingIntent.getActivity(context,
                AlarmEndMonth.NOTIFICATION_ENDMONTH_ID,
                mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    public PendingIntent getEndDayPendingIntent() {
        Intent mainIntent = getMainIntent();
        return PendingIntent.getActivity(context,
                AlarmEndDay.NOTIFICATION_ENDDAY_ID,
                mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

    }

    public PendingIntent getOfflimitPendingIntent(int id) {
        Intent mainIntent = getMainIntent();
        return PendingIntent.getActivity(context,
                id,
                mainIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

    }
}
