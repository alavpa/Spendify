package com.alavpa.spendify.ui.receiver.alarm;

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

import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.model.AlarmAmount;
import com.alavpa.spendify.domain.model.AlarmEndDay;
import com.alavpa.spendify.domain.model.AlarmEndMonth;
import com.alavpa.spendify.domain.model.AlarmOfflimit;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.usecases.InsertOrUpdateAmount;
import com.alavpa.spendify.domain.usecases.SendEndDayNotification;
import com.alavpa.spendify.domain.usecases.SendEndMonthNotification;
import com.alavpa.spendify.domain.usecases.SendOfflimitNotification;
import com.alavpa.spendify.domain.usecases.SetAlarmEndDay;
import com.alavpa.spendify.domain.usecases.SetAlarmEndMonth;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.observers.DisposableSingleObserver;

@Singleton
public class AlarmPresenter {

    @Inject
    public DateUtils dateUtils;

    @Inject
    public PrefsDatasource preferences;

    private SendEndMonthNotification sendEndMonthNotification;

    private SendEndDayNotification sendEndDayNotification;

    private SendOfflimitNotification sendOfflimitNotification;

    private SetAlarmEndDay setAlarmEndDay;

    private SetAlarmEndMonth setAlarmEndMonth;

    private InsertOrUpdateAmount insertOrUpdateAmount;

    @Inject
    public AlarmPresenter(SendEndDayNotification sendEndDayNotification,
                          SendEndMonthNotification sendEndMonthNotification,
                          SendOfflimitNotification sendOfflimitNotification,
                          SetAlarmEndDay setAlarmEndDay,
                          SetAlarmEndMonth setAlarmEndMonth,
                          InsertOrUpdateAmount insertOrUpdateAmount){
        this.sendEndDayNotification = sendEndDayNotification;
        this.sendEndMonthNotification = sendEndMonthNotification;
        this.sendOfflimitNotification = sendOfflimitNotification;
        this.setAlarmEndDay = setAlarmEndDay;
        this.setAlarmEndMonth = setAlarmEndMonth;
    }

    public void onReceiveAlarmEndDay(AlarmEndDay alarmEndDay) {
        if(preferences.notifyEndOfDay()) {

            sendEndDayNotification.execute();

            setAlarmEndDay.setAlarmEndDay(alarmEndDay.getNextAlarm());
            setAlarmEndDay.execute();
        }
    }

    public void onReceiveAlarmEndMonth(AlarmEndMonth alarmEndMonth) {
        if(preferences.notifyEndOfMonth()) {

            long from = dateUtils.calculateFrom(alarmEndMonth.getDate(), preferences.getMonthDay()).getTimeInMillis();
            sendEndMonthNotification.setFrom(from);
            sendEndMonthNotification.execute();

            setAlarmEndMonth.setAlarmEndMonth(alarmEndMonth.getNextAlarm());
            setAlarmEndMonth.execute();
        }
    }

    public void onReceiveAlarmOfflimit(AlarmOfflimit alarmOfflimit) {

        if(preferences.notifyOfflimit()){

            sendOfflimitNotification.setAlarmOfflimit(alarmOfflimit);
            sendOfflimitNotification.execute();
        }

    }

    public void onReceiveAlarmAmount(AlarmAmount alarmAmount) {
        Amount amount = alarmAmount.getAmount().getNextAmount();
        insertOrUpdateAmount.setAmount(amount);
        insertOrUpdateAmount.execute(new DisposableSingleObserver<Amount>() {
            @Override
            public void onSuccess(Amount amount) {
                insertOrUpdateAmount.dispose();
            }

            @Override
            public void onError(Throwable e) {
                insertOrUpdateAmount.dispose();
            }
        });
    }
}
