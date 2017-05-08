package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;

/**
 * Created by alavpa on 19/02/17.
 */

public class GetAlarms extends UseCase<List<Alarm>> {

    Repository repository;

    @Inject
    public GetAlarms(Repository repository) {
        this.repository = repository;
    }

    @Override
    public Single<List<Alarm>> build() {
        return repository.getAlarms();
    }
}
