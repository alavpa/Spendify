package com.alavpa.spendify.domain.usecases;

import com.alavpa.spendify.data.preferences.PrefsDatasource;
import com.alavpa.spendify.domain.DateUtils;
import com.alavpa.spendify.domain.Repository;
import com.alavpa.spendify.domain.usecases.base.UseCase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.functions.BiFunction;

public class GetMonthList extends UseCase<List<Long>> {

    Repository repository;

    @Inject
    DateUtils dateUtils;
    @Inject
    PrefsDatasource preferences;

    @Inject
    public GetMonthList(Repository repository) {
        this.repository = repository;
    }


    @Override
    public Single<List<Long>> build() {

        return Single.zip(repository.getMinDate(),
                repository.getMaxDate(),
                new BiFunction<Long, Long, List<Long>>() {
                    @Override
                    public List<Long> apply(Long min, Long max) throws Exception {
                        List<Long> froms = new ArrayList<>();

                        if (min == null && max == null) {
                            return froms;
                        }

                        Calendar from = dateUtils.calculateFrom(min, preferences.getMonthDay());
                        Calendar to = dateUtils.calculateTo(max, preferences.getMonthDay());

                        while (from.getTimeInMillis() < to.getTimeInMillis()) {
                            froms.add(from.getTimeInMillis());
                            from.add(Calendar.MONTH, 1);
                        }


                        return froms;
                    }
                });

    }
}
