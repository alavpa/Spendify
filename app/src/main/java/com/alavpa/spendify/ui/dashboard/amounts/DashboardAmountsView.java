package com.alavpa.spendify.ui.dashboard.amounts;

import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

public interface DashboardAmountsView extends BaseView {

    void populateDetails(List<Amount> amounts);

    void showCategoryColor(int colorResId);

    void showCategoryName(String name);

    void showCategoryAmount(String amount);
}
