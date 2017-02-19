package com.alavpa.spendify.data;

import com.alavpa.spendify.data.db.model.Category;

import java.util.List;

/**
 * Created by alavpa on 19/02/17.
 */

public interface Datasource {

    List<Category> getCategories(boolean income);
}
