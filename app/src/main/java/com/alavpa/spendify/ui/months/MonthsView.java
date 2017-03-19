package com.alavpa.spendify.ui.months;

import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

interface MonthsView extends BaseView{

    void populateMonths(List<Long> months);
}
