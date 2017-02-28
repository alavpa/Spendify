package com.alavpa.spendify.data.resources;

import android.content.Context;
import android.os.Build;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alavpa on 23/02/17.
 */

@Singleton
public class ResDatasource {

    Context context;

    @Inject
    public ResDatasource(Context context){
        this.context = context;
    }

    public String getString(int stringResId){
        return context.getString(stringResId);
    }

    public String[] getArrayString(int stringArrayResId){
        return context.getResources().getStringArray(stringArrayResId);
    }

    @SuppressWarnings("deprecation")
    public int getColor(int colorResId){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(colorResId,null);
        }else {
            return context.getResources().getColor(colorResId);
        }
    }
}
