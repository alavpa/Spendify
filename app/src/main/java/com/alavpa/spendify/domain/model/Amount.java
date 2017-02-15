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
}
