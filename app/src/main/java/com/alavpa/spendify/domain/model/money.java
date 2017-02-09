package com.alavpa.spendify.domain.model;

import java.util.List;

/**
 * Created by alavpa on 9/02/17.
 */

public class money {
    long id;
    boolean income;
    double amount;
    String description;
    List<Category> categories;
}
