package com.alavpa.spendify.ui.dashboard.sectors;

import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.ui.base.BaseView;
import com.alavpa.spendify.ui.model.AmountBarPart;

import java.util.List;

/**
 * Created by alavpa on 24/02/17.
 */
public interface DashboardSectorsView extends BaseView{


    void showDetails(List<AmountBarPart> details);

    void showTitle(String title);

    void showAmount(String amount);

    void populateDetails(List<Sector> details, List<Integer> categoryColorsArray);
}
