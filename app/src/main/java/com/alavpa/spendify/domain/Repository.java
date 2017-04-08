package com.alavpa.spendify.domain;


import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Sector;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public interface Repository {

    Single<List<Category>> getCategories(boolean income);

    Single<Category> insertCategory(Category category);

    Single<Category> updateCategory(Category category);

    Single<Amount> insertAmount(Amount amount);

    Single<Amount> updateAmount(Amount amount);

    Single<Double> getSumBy(boolean income, long from, long to);

    Single<List<Amount>> getAmountBy(boolean income, long from, long to);

    Single<List<Sector>> getSectorsBy(boolean income, long from, long to);

    Single<List<Amount>> getAmountsByCategoryId(long id, long from, long to);

    Single<Long> getMaxDate();

    Single<Long> getMinDate();

    Single<Double> getSumByCategory(Category category, long from, long to);

    Single<Sector> getSector(Category category, long from, long to);

    Single<List<Amount>> getRepeatAmounts();
}
