package com.alavpa.spendify.domain;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.domain.mapper.AmountMapper;
import com.alavpa.spendify.domain.mapper.CategoryMapper;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;

import java.util.List;
import java.util.concurrent.Callable;

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
                                .map(new Function<CategoryDb, Category>() {
                                    @Override
                                    public Category apply(CategoryDb categoryDb) throws Exception {
                                        return CategoryMapper.map(categoryDb);
                                    }
                                })
                                .toList();
                    }
                });
    }

    @Override
    public Single<Category> insertCategory(Category category) {
        return Single.just(category)
                .map(new Function<Category, Category>() {
                    @Override
                    public Category apply(Category category) throws Exception {

                        CategoryDb categoryData =
                                datasource.insertCategory(CategoryMapper.map(category));

                        return CategoryMapper.map(categoryData);
                    }
                });
    }

    @Override
    public Single<Amount> insertAmount(Amount amount) {
        return Single.just(amount)
                .map(new Function<Amount, Amount>() {
                    @Override
                    public Amount apply(Amount amount) throws Exception {
                        AmountDb amountData =
                                datasource.insertAmount(AmountMapper.map(amount));

                        return AmountMapper.map(amountData);
                    }
                });
    }

    @Override
    public Single<Double> getSumByCategory(Category category) {
        return Single.just(category.getId())
                .map(new Function<Long, Double>() {
                    @Override
                    public Double apply(Long categoryId) throws Exception {
                        return datasource.getSumByCategory(categoryId);
                    }
                });
    }

    @Override
    public Single<List<Amount>> getAmountByCategories(final boolean income, final long from, final long to) {
        return Single.fromCallable(new Callable<List<AmountDb>>() {
            @Override
            public List<AmountDb> call() throws Exception {
                return datasource.getAmountBy(income,from,to);
            }
        })
                .flatMap(new Function<List<AmountDb>, SingleSource<List<Amount>>>() {
                    @Override
                    public SingleSource<List<Amount>> apply(List<AmountDb> amountDbs) throws Exception {
                        return Observable.fromIterable(amountDbs)
                                .map(new Function<AmountDb, Amount>() {
                                    @Override
                                    public Amount apply(AmountDb amountDb) throws Exception {
                                        return AmountMapper.map(amountDb);
                                    }
                                })
                                .toList();
                    }
                });
    }


}
