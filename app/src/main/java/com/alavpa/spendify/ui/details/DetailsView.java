package com.alavpa.spendify.ui.details;

import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

/**
 * Created by alavpa on 14/02/17.
 */

public interface DetailsView extends BaseView {
    void populateCategories(List<Category> categories);

    void setAmount(String amount);

    String description();
    boolean repeat();
    int every();
    int period();
    Category category();

    void setDescription(String description);
    void setDate(String date);
    void selectCategory(Category category);
    void setRepeat(boolean repeat);
    void setTimes(int times);
    void setPeriod(int period);
    void showDatePickerDialog(long date);

    void goToMain(Amount amount);

    void goToAddCategory(boolean income);
}
