package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import javax.inject.Inject;

import io.reactivex.Single;

public class GetSector extends UseCase<Sector> {

    private Repository repository;
    private Category category;
    private long from;
    private long to;

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Inject
    public GetSector(Repository repository){
        this.repository = repository;
    }
    @Override
    public Single<Sector> build() {
        return repository.getSector(category,from,to);
    }
}
