package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Sector;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public class GetSectorsBy extends UseCase<List<Sector>>{

    Repository repository;
    private boolean income;
    private long from;
    private long to;

    @Inject
    public GetSectorsBy(Repository repository){
        this.repository = repository;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public void setTo(long to) {
        this.to = to;
    }

    @Override
    public Single<List<Sector>> build() {
        return repository.getSectorsBy(income,from,to);
    }
}
