package com.alavpa.spendify.ui.category.add;

import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

import com.alavpa.spendify.R;
import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.di.PerActivity;
import com.alavpa.spendify.ui.base.BasePresenter;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by alavpa on 28/02/17.
 */

@PerActivity
public class AddCategoryPresenter extends BasePresenter<AddCategoryView> {

    ResDatasource resDatasource;

    @Inject
    public AddCategoryPresenter(ResDatasource resDatasource){
        this.resDatasource = resDatasource;
    }
    public void showColors(){

        List<Drawable> colors = resDatasource.getDrawableArray(R.array.category_colors);

        getView().populateColors(colors);
    }
}
