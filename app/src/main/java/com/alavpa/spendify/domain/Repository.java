package com.alavpa.spendify.domain;


import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public interface Repository {

    Single<List<Category>> getCategories(boolean income);

    Single<Category> insertCategory(Category category);

    Single<Amount> insertAmount(Amount amount);

    Single<Double> getSumByCategory(Category category);
}
