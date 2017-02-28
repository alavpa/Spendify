package com.alavpa.spendify.data;

import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;

import java.util.List;
import java.util.Map;

/**
 * Created by alavpa on 19/02/17.
 */

public interface Datasource {

    List<CategoryDb> getCategories(boolean income);

    Map<Long,CategoryDb> getHashCategories(boolean income);

    CategoryDb insertCategory(CategoryDb categoryDb);

    AmountDb insertAmount(AmountDb amountDb);

    double getSumByCategory(long categoryId);

    List<AmountDb> getAmountBy(boolean income, long from, long to);

    Map<Long, AmountDb> getHashAmountBy(boolean income, long from, long to);

}
