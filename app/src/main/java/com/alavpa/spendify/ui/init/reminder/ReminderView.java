package com.alavpa.spendify.ui.init.reminder;

import com.alavpa.spendify.ui.base.BaseView;

import java.util.Calendar;

interface ReminderView extends BaseView{
    void showEndOfDay(boolean enabled);

    void showEndOfMonth(boolean enabled);

    void showOfflimit(boolean enabled);

    void showEndOfDayTime(String time);

    void showTimePickerDialog(Calendar calendar);
}
