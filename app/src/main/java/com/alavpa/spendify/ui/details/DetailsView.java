package com.alavpa.spendify.ui.details;

import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Period;
import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

/**
 * Created by alavpa on 14/02/17.
 */

public interface DetailsView extends BaseView {

    void populateCategories(List<Category> categories,List<Integer> backgrouns);

    void showAmount(double amount);

    double amount();
    String description();
    boolean repeat();
    Period period();
    Category category();

    void setDescription(String description);
    void setDate(String date);
    void selectCategory(Category category);
    void setRepeat(boolean repeat);
    void setPeriod(Period period);
    void showDatePickerDialog(long date);
    void setDeletable(boolean deletable);
}
