package com.alavpa.spendify.ui.details;

import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

/**
 * Created by alavpa on 14/02/17.
 */

public interface DetailsView extends BaseView {
    void populateCategories(List<Category> categories);

    void setAmount(String amount);
    String getDescription();
    boolean repeat();
    int every();
    int period();
    Category selectedCategory();
    void showDate(String date);

    void initDatePicker(long date);
}
