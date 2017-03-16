package com.alavpa.spendify.ui.dashboard.details;

import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.BaseView;
import com.alavpa.spendify.ui.model.AmountBarPart;

import java.util.List;

/**
 * Created by alavpa on 24/02/17.
 */
public interface DashboardDetailsView extends BaseView{


    void showDetails(List<AmountBarPart> details);

    void showTitle(String title);

    void showAmount(String amount);

    void populateDetails(List<Amount> details, int[] categoryColorsArray);
}
