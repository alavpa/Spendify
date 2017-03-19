package com.alavpa.spendify.ui.init.reminder;

import com.alavpa.spendify.ui.base.BaseView;

interface ReminderView extends BaseView{
    void showEndOfDay(boolean enabled);

    void showEndOfMonth(boolean enabled);

    void showOfflimit(boolean enabled);

    void showPromises(boolean enabled);

    void showEndOfDayTime(String time);
}
