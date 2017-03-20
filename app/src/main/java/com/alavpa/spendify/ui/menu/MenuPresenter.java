package com.alavpa.spendify.ui.menu;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by alavpa on 23/02/17.
 */

@PerActivity
public class MenuPresenter extends BasePresenter<MenuView> {

    @Inject
    DateUtils dateUtils;

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
            Calendar from = dateUtils.calculateFrom(Calendar.getInstance().getTimeInMillis(),preferences.getMonthDay());
            navigator.openDashboard(from.getTimeInMillis());
        }

        if(position == 1){
            navigator.openMonths();
        }

        if(position == 2){
            navigator.openManageCategories();
        }

        if(position == 3){
            navigator.openSelectDayMonth();
        }

        if(position == 5){
            navigator.openNotifications();
        }

        getView().hideMenu();
    }
}
