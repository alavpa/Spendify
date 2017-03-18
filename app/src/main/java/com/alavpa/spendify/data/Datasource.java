package com.alavpa.spendify.data;

import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.data.db.model.SectorDb;

import java.util.List;

/**
 * Created by alavpa on 19/02/17.
 */

public interface Datasource {

    List<CategoryDb> getCategories(boolean income);

    CategoryDb insertCategory(CategoryDb categoryDb);

    AmountDb insertAmount(AmountDb amountDb);

    double getSumBy(boolean income, long from, long to);

    List<AmountDb> getAmountBy(boolean income, long from, long to);

    List<SectorDb> getSectors(boolean income, long from, long to);

    List<AmountDb> getAmountsByCategoryId(long id, long from, long to);

}
