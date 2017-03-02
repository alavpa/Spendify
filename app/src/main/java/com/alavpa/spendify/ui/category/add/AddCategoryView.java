package com.alavpa.spendify.ui.category.add;

import android.graphics.drawable.Drawable;

import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

/**
 * Created by alavpa on 28/02/17.
 */

public interface AddCategoryView extends BaseView {

    void populateColors(List<Drawable> colors);

    String name();

    boolean income();

    int color();

    void onSendSuccess();
}
