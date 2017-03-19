package com.alavpa.spendify.data.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefsDatasource {

    public static final String KEY_MONTHDAY = "KEY_MONTHDAY";
    private static final String KEY_INIT = "KEY_INIT";
    private SharedPreferences sharedPreferences;

    @Inject
    public PrefsDatasource(Context context){
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public int getMonthDay(){
        return sharedPreferences.getInt(KEY_MONTHDAY,1);
    }

    public void setMonthDay(int day){
        sharedPreferences.edit()
                .putInt(KEY_MONTHDAY,day)
                .commit();
    }

    public boolean isInitialized(){
        return sharedPreferences.getBoolean(KEY_INIT,false);
    }

    public void setInitialized(boolean initialized){
        sharedPreferences.edit()
                .putBoolean(KEY_INIT,initialized)
                .commit();
    }
}
