package com.alavpa.spendify.domain.mapper;

import com.alavpa.spendify.data.db.model.AmountDb;
import com.alavpa.spendify.data.db.model.CategoryDb;
import com.alavpa.spendify.domain.model.Category;
import com.alavpa.spendify.domain.model.Amount;
import com.alavpa.spendify.domain.model.Period;

/**
 * Created by alavpa on 19/02/17.
 */

public class AmountMapper {

    public static Amount map(AmountDb source){
        Amount amount = new Amount();

        amount.setId(source.getId());
        amount.setIncome(source.isIncome());
        amount.setDescription(source.getDescription());
        amount.setAmount(source.getAmount());
        Period period = new Period(source.getDate(),source.getTimes(),source.getPeriod());
        amount.setPeriod(period);
        if(source.getCategoryDb()!=null) {
            Category category = CategoryMapper.map(source.getCategoryDb());
            amount.setCategory(category);
        }
        return amount;
    }

    public static AmountDb map(Amount source){
        AmountDb amount = new AmountDb();

        amount.setId(source.getId());
        amount.setIncome(source.isIncome());
        amount.setDescription(source.getDescription());
        amount.setAmount(source.getAmount());
        amount.setPeriod(source.getPeriod().getPeriod());
        amount.setDate(source.getPeriod().getDate());
        amount.setTimes(source.getPeriod().getTimes());

        if(source.getCategory()!=null) {
            CategoryDb categoryDb = CategoryMapper.map(source.getCategory());
            amount.setCategoryDb(categoryDb);
        }

        return amount;
    }
}
