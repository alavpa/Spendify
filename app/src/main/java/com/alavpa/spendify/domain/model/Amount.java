package com.alavpa.spendify.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alavpa on 9/02/17.
 */

public class Amount {

    private
    long id;

    private
    boolean income;

    private
    double amount;

    private
    String description;

    private
    List<Category> categories;

    private
    Period period;

    public Amount(){
        this.id = 0;
        this.income = false;
        this.description = "";
        this.amount = 0.0f;
        categories = new ArrayList<>();
        period = new Period();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isIncome() {
        return income;
    }

    public void setIncome(boolean income) {
        this.income = income;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
