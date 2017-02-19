package com.alavpa.spendify.ui;

import android.content.Context;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by alavpa on 19/02/17.
 */

@Singleton
public class ResourceProvider {

    Context context;

    @Inject
    public ResourceProvider(Context context){
        this.context = context;
    }

    public String getString(int stringResId){
        return context.getString(stringResId);
    }

}
