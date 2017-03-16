package com.alavpa.spendify.ui.dashboard;

import com.alavpa.spendify.ui.base.BaseView;
import com.alavpa.spendify.ui.model.AmountBarPart;

import java.util.List;

/**
 * Created by alavpa on 24/02/17.
 */
public interface DashboardView extends BaseView{


    void showOutcome(List<AmountBarPart> outcome);

    void showIncome(List<AmountBarPart> income);

    void showTotalOutcome(String totalOutcome);

    void showTotalIncome(String totalIncome);

    void showTotal(String total);

    void openDetails(boolean income, String amount);
}
