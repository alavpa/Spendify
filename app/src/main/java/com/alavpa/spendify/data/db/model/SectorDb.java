package com.alavpa.spendify.data.db.model;

import android.database.Cursor;

public class SectorDb {
    private
    double amount;

    private
    CategoryDb categoryDb;

    public SectorDb(){
        amount = 0;
        categoryDb = new CategoryDb();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public CategoryDb getCategoryDb() {
        return categoryDb;
    }

    public void setCategoryDb(CategoryDb category) {
        this.categoryDb = category;
    }

    public SectorDb fromCursor(Cursor cursor){
        this.amount = cursor.getDouble(0);
        return this;
    }
}
