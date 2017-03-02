package com.alavpa.spendify.domain.mapper;

import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.domain.model.Category;

/**
 * Created by alavpa on 19/02/17.
 */

public class CategoryMapper {

    public static Category map(CategoryDb source){
        Category category = new Category();
        category.setId(source.getId());
        category.setName(source.getName());
        category.setIncome(source.isIncome());
        category.setColor(source.getColor());

        return category;
    }

    public static CategoryDb map(Category source){
        CategoryDb categoryDb = new CategoryDb();
        categoryDb.setId(source.getId());
        categoryDb.setName(source.getName());
        categoryDb.setIncome(source.isIncome());
        categoryDb.setColor(source.getColor());

        return categoryDb;
    }
}
