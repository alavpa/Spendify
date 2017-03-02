package com.alavpa.spendify.data.resources;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.alavpa.spendify.R;

import java.util.ArrayList;
import java.util.List;

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

    public int[] getArrayInt(int intArrayResId){
        return context.getResources().getIntArray(intArrayResId);
    }

    public TypedArray getTypedArray(int intArrayResId){
        return context.getResources().obtainTypedArray(intArrayResId);
    }

    public List<Drawable> getDrawableArray(int intArrayResId){
        List<Drawable> list = new ArrayList<>();
        TypedArray typedArray = getTypedArray(intArrayResId);
        for(int i=0;i<typedArray.length();i++){
            list.add(typedArray.getDrawable(i));
        }
        typedArray.recycle();
        return list;
    }

    public int[] getCategoryColorsArray(){
        return new int[]{R.drawable.bkg_category1,
                R.drawable.bkg_category2,
                R.drawable.bkg_category3,
                R.drawable.bkg_category4,
                R.drawable.bkg_category5,
                R.drawable.bkg_category6,
                R.drawable.bkg_category7,
                R.drawable.bkg_category8,
                R.drawable.bkg_category9};
    }
}
