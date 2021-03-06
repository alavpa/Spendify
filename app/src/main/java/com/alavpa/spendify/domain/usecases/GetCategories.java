package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public class GetCategories extends UseCase<List<Category>>{

    Repository repository;
    private boolean income;

    @Inject
    public GetCategories(Repository repository){
        this.repository = repository;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    @Override
    public Single<List<Category>> build() {
        return repository.getCategories(income);
    }
}
