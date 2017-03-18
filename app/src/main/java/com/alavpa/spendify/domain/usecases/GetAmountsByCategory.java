package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public class GetAmountsByCategory extends UseCase<List<Amount>>{

    Repository repository;
    private Category category;
    private long from;
    private long to;

    @Inject
    public GetAmountsByCategory(Repository repository){
        this.repository = repository;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Override
    public Single<List<Amount>> build() {
        return repository.getAmountsByCategoryId(category.getId(),from,to);
    }
}
