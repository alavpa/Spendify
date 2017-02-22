package com.alavpa.spendify.ui.main;

import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.ui.base.BaseView;

/**
 * Created by alavpa on 10/02/17.
 */

public interface MainView extends BaseView {
    void setValue(double amount);
    void goToDetails(Amount amount);
}
