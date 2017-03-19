package com.alavpa.spendify.ui.menu;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.ui.base.BasePresenter;

import javax.inject.Inject;

/**
 * Created by alavpa on 23/02/17.
 */

@PerActivity
public class MenuPresenter extends BasePresenter<MenuView> {

    String[] menu;

    @Inject
    public MenuPresenter(){

    }

    public void showMenu(){
        menu = resources.getArrayString(R.array.menu);
        getView().populateMenu(menu);
    }

    public void goTo(int position) {
        if(position == 0){
            navigator.openDashboard();
        }

        if(position == 0){
            navigator.openMonths();
        }

        if(position == 2){
            navigator.openStartCategories();
        }

        if(position == 3){
            navigator.openSelectDay();
        }

        if(position == 5){
            navigator.openReminder();
        }

        getView().hideMenu();
    }
}
