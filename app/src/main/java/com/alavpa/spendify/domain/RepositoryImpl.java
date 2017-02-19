package com.alavpa.spendify.domain;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.domain.mapper.CategoryMapper;
import com.alavpa.spendify.domain.model.Category;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

/**
 * Created by alavpa on 19/02/17.
 */
@Singleton
public class RepositoryImpl implements Repository {

    Datasource datasource;

    @Inject
    public RepositoryImpl(Datasource datasource){
        this.datasource = datasource;
    }

    @Override
    public Single<List<Category>> getCategories(boolean income) {
        return Single.just(income)
                .flatMap(new Function<Boolean, SingleSource<? extends List<Category>>>() {
                    @Override
                    public SingleSource<? extends List<Category>> apply(Boolean income) throws Exception {
                        return Observable.fromIterable(datasource.getCategories(income))
                                .map(new Function<com.alavpa.spendify.data.db.model.Category, Category>() {
                                    @Override
                                    public Category apply(com.alavpa.spendify.data.db.model.Category category) throws Exception {
                                        return CategoryMapper.map(category);
                                    }
                                })
                                .toList();
                    }
                });
    }
}
