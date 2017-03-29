package com.alavpa.spendify.ui.init.categories;

import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.ui.base.BaseView;

import java.util.List;

interface StartCategoriesView extends BaseView{
    void populateCategoriesIncome(List<Category> categories, List<Integer> colors);

    void populateCategoriesOutcome(List<Category> categories, List<Integer> colors);
}
