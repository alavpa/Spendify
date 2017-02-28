package com.alavpa.spendify.ui.menu;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by alavpa on 23/02/17.
 */

public class MenuPresenter extends BasePresenter<MenuView> {

    String[] menu;
    ResDatasource resDatasource;

    @Inject
    public MenuPresenter(ResDatasource resDatasource){
        this.resDatasource = resDatasource;
    }

    public void showMenu(){
        menu = resDatasource.getArrayString(R.array.menu);
        getView().populateMenu(menu);
    }

    public void goTo(int position) {
        if(position == 1){
            getView().goToDashboard();
        }else{
            getView().goToMain();
        }
    }
}
