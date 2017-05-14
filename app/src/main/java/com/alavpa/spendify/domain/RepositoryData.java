package com.alavpa.spendify.domain;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.AlarmDb;
import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.data.db.model.SectorDb;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Sector;

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
public class RepositoryData implements Repository {

    Datasource datasource;

    @Inject
    public RepositoryData(Datasource datasource) {
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
                                        return new Category().fromCategoryDb(categoryDb);
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
                        return category.insert(datasource);
                    }
                });
    }

    @Override
    public Single<Category> updateCategory(Category category) {
        return Single.just(category)
                .map(new Function<Category, Category>() {
                    @Override
                    public Category apply(Category category) throws Exception {
                        return category.update(datasource);
                    }
                });
    }

    @Override
    public Single<Amount> insertAmount(Amount amount) {
        return Single.just(amount)
                .map(new Function<Amount, Amount>() {
                    @Override
                    public Amount apply(Amount amount) throws Exception {
                        return amount.insert(datasource);
                    }
                });
    }

    @Override
    public Single<Amount> updateAmount(Amount amount) {
        return Single.just(amount)
                .map(new Function<Amount, Amount>() {
                    @Override
                    public Amount apply(Amount amount) throws Exception {
                        return amount.update(datasource);
                    }
                });
    }

    @Override
    public Single<Amount> getAmount(final long id) {
        return Single.fromCallable(new Callable<AmountDb>() {
                    @Override
                    public AmountDb call() throws Exception {
                        return datasource.getAmountDb(id);
                    }
                })
                .map(new Function<AmountDb, Amount>() {
                    @Override
                    public Amount apply(AmountDb amountDb) throws Exception {
                        return new Amount().fromAmountDb(amountDb);
                    }
                });
    }

    @Override
    public Single<Double> getSumBy(final boolean income, final long from, final long to) {
        return Single.fromCallable(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return datasource.getSumBy(income, from, to);
            }
        });
    }

    @Override
    public Single<List<Amount>> getAmountBy(final boolean income, final long from, final long to) {
        return Single.fromCallable(new Callable<List<AmountDb>>() {
            @Override
            public List<AmountDb> call() throws Exception {
                return datasource.getAmountBy(income, from, to);
            }
        })
                .flatMap(new Function<List<AmountDb>, SingleSource<List<Amount>>>() {
                    @Override
                    public SingleSource<List<Amount>> apply(List<AmountDb> amountDbs) throws Exception {
                        return Observable.fromIterable(amountDbs)
                                .map(new Function<AmountDb, Amount>() {
                                    @Override
                                    public Amount apply(AmountDb amountDb) throws Exception {
                                        return new Amount().fromAmountDb(amountDb);
                                    }
                                })
                                .toList();
                    }
                });
    }

    @Override
    public Single<List<Sector>> getSectorsBy(final boolean income, final long from, final long to) {

        return Single.fromCallable(new Callable<List<SectorDb>>() {
            @Override
            public List<SectorDb> call() throws Exception {
                return datasource.getSectors(income, from, to);
            }
        })
                .flatMap(new Function<List<SectorDb>, SingleSource<List<Sector>>>() {
                    @Override
                    public SingleSource<List<Sector>> apply(List<SectorDb> sectors) throws Exception {
                        return Observable.fromIterable(sectors)
                                .map(new Function<SectorDb, Sector>() {
                                    @Override
                                    public Sector apply(SectorDb sectorDb) throws Exception {
                                        return new Sector().fromSectorDb(sectorDb);
                                    }
                                })
                                .toList();
                    }
                });
    }

    @Override
    public Single<List<Amount>> getAmountsByCategoryId(final long id, final long from, final long to) {
        return Single.fromCallable(new Callable<List<AmountDb>>() {
            @Override
            public List<AmountDb> call() throws Exception {
                return datasource.getAmountsByCategoryId(id, from, to);
            }
        })
                .flatMap(new Function<List<AmountDb>, SingleSource<List<Amount>>>() {
                    @Override
                    public SingleSource<List<Amount>> apply(List<AmountDb> amountDbs) throws Exception {
                        return Observable.fromIterable(amountDbs)
                                .map(new Function<AmountDb, Amount>() {
                                    @Override
                                    public Amount apply(AmountDb amountDb) throws Exception {
                                        return new Amount().fromAmountDb(amountDb);
                                    }
                                })
                                .toList();
                    }
                });
    }

    @Override
    public Single<Long> getMaxDate() {

        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return datasource.getMaxDate();
            }
        });
    }

    @Override
    public Single<Long> getMinDate() {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return datasource.getMinDate();
            }
        });
    }

    @Override
    public Single<Double> getSumByCategory(final Category category, final long from, final long to) {
        return Single.fromCallable(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return datasource.getSumByCategoryId(category.getId(), from, to);
            }
        });
    }

    @Override
    public Single<Sector> getSector(final Category category, final long from, final long to) {
        return Single.fromCallable(new Callable<Sector>() {
            @Override
            public Sector call() throws Exception {
                SectorDb sectorDb = datasource.getSectorDb(category.getId(), from, to);
                return new Sector().fromSectorDb(sectorDb);
            }
        });
    }

    @Override
    public Single<List<Amount>> getRepeatAmounts() {
        return Single.fromCallable(new Callable<List<AmountDb>>() {
            @Override
            public List<AmountDb> call() throws Exception {
                return datasource.getRepeatAmountDbs();
            }
        })
                .flatMap(new Function<List<AmountDb>, SingleSource<? extends List<Amount>>>() {
                    @Override
                    public SingleSource<? extends List<Amount>> apply(List<AmountDb> amountDbs) throws Exception {
                        return Observable.fromIterable(amountDbs)
                                .map(new Function<AmountDb, Amount>() {
                                    @Override
                                    public Amount apply(AmountDb amountDb) throws Exception {
                                        return new Amount().fromAmountDb(amountDb);
                                    }
                                })
                                .toList();
                    }
                });
    }

    @Override
    public Single<List<Alarm>> getAlarms() {
        return Single.fromCallable(new Callable<List<AlarmDb>>() {
            @Override
            public List<AlarmDb> call() throws Exception {
                return datasource.getAlarmDbs();
            }
        })
                .flatMap(new Function<List<AlarmDb>, SingleSource<? extends List<Alarm>>>() {
                    @Override
                    public SingleSource<? extends List<Alarm>> apply(List<AlarmDb> alarmDbs) throws Exception {
                        return Observable.fromIterable(alarmDbs)
                                .map(new Function<AlarmDb, Alarm>() {
                                    @Override
                                    public Alarm apply(AlarmDb alarmDb) throws Exception {
                                        return new Alarm(alarmDb.getAction()).fromAlarmDb(alarmDb);
                                    }
                                })
                                .toList();
                    }
                });
    }

    @Override
    public Single<Alarm> insertAlarm(final Alarm alarm) {
        return Single.fromCallable(new Callable<Alarm>() {
            @Override
            public Alarm call() throws Exception {
                return alarm.insertAlarm(datasource);
            }
        });
    }

    @Override
    public Single<Alarm> updateAlarm(final Alarm alarm) {

        return Single.fromCallable(new Callable<Alarm>() {
            @Override
            public Alarm call() throws Exception {
                return alarm.updateAlarm(datasource);
            }
        });
    }

    @Override
    public Single<Alarm> getAlam(final String action) {
        return Single.fromCallable(new Callable<AlarmDb>() {
            @Override
            public AlarmDb call() throws Exception {
                return datasource.getAlam(action);
            }
        })
                .map(new Function<AlarmDb, Alarm>() {
                    @Override
                    public Alarm apply(AlarmDb alarmDb) throws Exception {
                        return new Alarm().fromAlarmDb(alarmDb);
                    }
                });
    }

    @Override
    public Single<Alarm> getAlam(final String action, final long refId) {
        return Single.fromCallable(new Callable<AlarmDb>() {
            @Override
            public AlarmDb call() throws Exception {
                return datasource.getAlam(action, refId);
            }
        })
                .map(new Function<AlarmDb, Alarm>() {
                    @Override
                    public Alarm apply(AlarmDb alarmDb) throws Exception {
                        return new Alarm().fromAlarmDb(alarmDb);
                    }
                });
    }

    @Override
    public Single<Category> getCategory(final long id) {
        return Single.fromCallable(new Callable<CategoryDb>() {
            @Override
            public CategoryDb call() throws Exception {
                return datasource.getCategoryDb(id);
            }
        })
                .map(new Function<CategoryDb, Category>() {
                    @Override
                    public Category apply(CategoryDb categoryDb) throws Exception {
                        return new Category().fromCategoryDb(categoryDb);
                    }
                });
    }


}
