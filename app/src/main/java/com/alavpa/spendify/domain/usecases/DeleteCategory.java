package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.Function;

/**
 * Created by alavpa on 19/02/17.
 */

public class DeleteCategory extends UseCase<Boolean>{

    Repository repository;
    private Category category;

    @Inject
    public DeleteCategory(Repository repository){
        this.repository = repository;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public Single<Boolean> build() {

        category.setDeleted(true);
        return repository.updateCategory(category)
                .map(new Function<Category, Boolean>() {
                    @Override
                    public Boolean apply(Category category) throws Exception {
                        return category.isDeleted();
                    }
                });

    }
}
