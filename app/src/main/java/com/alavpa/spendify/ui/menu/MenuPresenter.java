package com.alavpa.spendify.ui.menu;

import com.alavpa.spendify.R;
import com.alavpa.spendify.di.scopes.PerActivity;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.util.Calendar;

import javax.inject.Inject;

/**
 * Created by alavpa on 23/02/17.
 */

@PerActivity
public class MenuPresenter extends BasePresenter<MenuView> {

    private static final int POSITION_DASHBOARD = 0;
    private static final int POSITION_MONTHS = 1;
    private static final int POSITION_CATEGORIES = 2;
    private static final int POSITION_DAY = 3;
    private static final int POSITION_NOTIFICATIONS = 4;

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
        if(position == POSITION_DASHBOARD){
            Calendar from = dateUtils.calculateFrom(Calendar.getInstance().getTimeInMillis(),preferences.getMonthDay());
            navigator.openDashboard(from.getTimeInMillis());
        }

        if(position == POSITION_MONTHS){
            navigator.openMonths();
        }

        if(position == POSITION_CATEGORIES){
            navigator.openManageCategories();
        }

        if(position == POSITION_DAY){
            navigator.openSelectDayMonth();
        }

        if(position == POSITION_NOTIFICATIONS){
            navigator.openNotifications();
        }

        getView().hideMenu();
    }
}
