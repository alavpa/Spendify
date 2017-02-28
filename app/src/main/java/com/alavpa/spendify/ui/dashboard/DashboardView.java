package com.alavpa.spendify.ui.dashboard;

import com.alavpa.spendify.ui.base.BaseView;
import com.alavpa.spendify.ui.custom.graphics.AmountBar;

import java.util.List;

/**
 * Created by alavpa on 24/02/17.
 */
public interface DashboardView extends BaseView{


    void showOutcome(List<AmountBar.AmountBarPart> outcome);

    void showIncome(List<AmountBar.AmountBarPart> income);
}
