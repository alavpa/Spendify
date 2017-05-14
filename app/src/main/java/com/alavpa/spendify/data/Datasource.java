package com.alavpa.spendify.data;

import com.alavpa.spendify.data.db.model.AlarmDb;
import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.data.db.model.SectorDb;
import com.alavpa.spendify.domain.model.Alarm;
import com.alavpa.spendify.domain.model.Amount;

import java.util.List;

/**
 * Created by alavpa on 19/02/17.
 */

public interface Datasource {

    List<CategoryDb> getCategories(boolean income);

    CategoryDb insertCategory(CategoryDb categoryDb);
    CategoryDb updateCategory(CategoryDb categoryDb);

    AmountDb insertAmount(AmountDb amountDb);

    AmountDb updateAmount(AmountDb amountDb);

    double getSumBy(boolean income, long from, long to);

    List<AmountDb> getAmountBy(boolean income, long from, long to);

    List<SectorDb> getSectors(boolean income, long from, long to);

    List<AmountDb> getAmountsByCategoryId(long id, long from, long to);

    Long getMaxDate();

    Long getMinDate();

    double getSumByCategoryId(long catId, long from, long to);

    SectorDb getSectorDb(long catId, long from, long to);

    List<AmountDb> getRepeatAmountDbs();

    List<AlarmDb> getAlarmDbs();

    AlarmDb insertAlarmDb(AlarmDb alarmDb);

    AlarmDb updateAlarmDb(AlarmDb alarmDb);

    AmountDb getAmountDb(long id);

    AlarmDb getAlam(String action);

    AlarmDb getAlam(String action, long refId);

    CategoryDb getCategoryDb(long id);
}
