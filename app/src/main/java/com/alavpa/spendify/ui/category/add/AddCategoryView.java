package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.ui.base.BaseView;

/**
 * Created by alavpa on 28/02/17.
 */

public interface AddCategoryView extends BaseView {

    void populateColors(int[] colors, int selected);
    
    void onSendSuccess();

    void showName(String name);

    void setSelected(int color);

    void showLimit(String limit);
}
