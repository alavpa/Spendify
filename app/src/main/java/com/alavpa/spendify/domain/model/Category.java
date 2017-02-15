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
    String text;

    public Category(){
        this.id = 0;
        this.income = false;
        this.text = "";
    }
}
