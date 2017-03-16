package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public class InsertCategory extends UseCase<Category>{

    Repository repository;
    private Category category;

    @Inject
    public InsertCategory(Repository repository){
        this.repository = repository;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Single<Category> build() {
        return repository.insertCategory(category);
    }
}
