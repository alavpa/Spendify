package com.alavpa.spendify.domain.model;

/**
 * Created by alavpa on 9/02/17.
 */

public class Category {

    private
    long id;

    private
    boolean income;

    private
    String name;

    public Category(){
        this.id = 0;
        this.income = false;
        this.name = "";
    }

    public Category(String name, boolean income){
        this.id = 0;
        this.income = income;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
