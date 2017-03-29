package com.alavpa.spendify.data.resources;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import com.alavpa.spendify.R;

import java.util.ArrayList;
import java.util.Arrays;
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

    public int getColor(int colorResId){
        return ContextCompat.getColor(context,colorResId);
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

    public List<Integer> getCategoryBackgroundsArray(){
        return Arrays.asList(R.drawable.bkg_category1,
                R.drawable.bkg_category2,
                R.drawable.bkg_category3,
                R.drawable.bkg_category4,
                R.drawable.bkg_category5,
                R.drawable.bkg_category6,
                R.drawable.bkg_category7,
                R.drawable.bkg_category8,
                R.drawable.bkg_category9);
    }

    public List<Integer> getCategoryColorsArray(){
        return Arrays.asList(R.color.red,
                R.color.pink,
                R.color.purple,
                R.color.deep_purple,
                R.color.indigo,
                R.color.blue,
                R.color.light_blue,
                R.color.cyan,
                R.color.teal);
    }
}
