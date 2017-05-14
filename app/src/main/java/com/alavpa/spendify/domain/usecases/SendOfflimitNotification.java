package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.notifications.NotificationManager;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;
import com.alavpa.spendify.ui.utils.IntentUtils;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;

@Singleton
public class SendOfflimitNotification extends UseCase<Category> {

    private NotificationManager notificationManager;
    private ResDatasource resources;
    private Alarm alarmOfflimit;
    private Repository repository;

    @Inject
    public SendOfflimitNotification(NotificationManager notificationManager,
                                    Repository repository,
                                    ResDatasource resources){

        this.notificationManager = notificationManager;
        this.resources = resources;
        this.repository = repository;
    }

    public void setAlarmOfflimit(Alarm alarmOfflimit) {
        this.alarmOfflimit = alarmOfflimit;
    }

    @Override
    public Single<Category> build() {
        return repository.getCategory(alarmOfflimit.getId())
                .doOnSuccess(new Consumer<Category>() {
                    @Override
                    public void accept(Category category) throws Exception {
                        int notificationId = (int)alarmOfflimit.getId();
                        notificationManager.publishOfflimit(notificationId,
                                resources.getString(R.string.notification_title_offlimit,category.getName()),
                                resources.getString(R.string.notification_content_offlimit,category.getLimit(),
                                        category.getName()));
                    }
                });
    }
}
