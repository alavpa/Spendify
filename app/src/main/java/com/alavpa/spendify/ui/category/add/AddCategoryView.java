package com.alavpa.spendify.ui.category.add;

import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

/**
 * Created by alavpa on 28/02/17.
 */

public interface AddCategoryView extends BaseView {

    void populateColors(List<Integer> colors, int selected);
    
    void onSendSuccess();

    void showName(String name);

    void setSelected(int color);

    void showLimit(double limit);

    void setDeletable(boolean deletable);

    void hasLimit(boolean has);
}
