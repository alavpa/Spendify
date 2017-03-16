package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.ui.base.BaseView;

/**
 * Created by alavpa on 28/02/17.
 */

public interface AddCategoryView extends BaseView {

    void populateColors(int[] colors);

    String name();

    boolean income();

    int color();

    void onSendSuccess();
}
