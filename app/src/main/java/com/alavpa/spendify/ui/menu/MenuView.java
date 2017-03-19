package com.alavpa.spendify.ui.menu;

import com.alavpa.spendify.ui.base.BaseView;

/**
 * Created by alavpa on 23/02/17.
 */

public interface MenuView extends BaseView{

    void populateMenu(String[] menu);
    void hideMenu();
}
