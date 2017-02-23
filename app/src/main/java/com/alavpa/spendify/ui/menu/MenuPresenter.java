package com.alavpa.spendify.ui.menu;

import com.alavpa.spendify.R;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by alavpa on 23/02/17.
 */

public class MenuPresenter extends BasePresenter<MenuView> {

    String[] menu;

    @Inject
    public MenuPresenter(){

    }

    public void showMenu(){
        menu = resourceProvider.getArrayString(R.array.menu);
        getView().populateMenu(menu);
    }
}
