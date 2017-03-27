package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.CategoryDb;

/**
 * Created by alavpa on 9/02/17.
 */

public class Category implements Parcelable {

    private
    long id;

    private
    boolean income;

    private
    String name;

    private
    int color;

    private
    double limit;

    private
    boolean deleted;

    protected Category(Parcel in) {
        this.id = in.readLong();
        this.income = in.readByte() == 1;
        this.name = in.readString();
        this.color = in.readInt();
        this.limit = in.readDouble();
        this.deleted = in.readByte() == 1;
    }

    public Category(){
        this.id = 0;
        this.income = false;
        this.name = "";
        this.color = 0;
        this.limit = 0;
        this.deleted = false;
    }

    public Category(String name, boolean income, int color, double limit){
        this();
        this.income = income;
        this.name = name;
        this.color = color;
        this.limit = limit;
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.income ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
        dest.writeInt(color);
        dest.writeDouble(limit);
        dest.writeByte(this.deleted ? (byte) 1 : (byte) 0);
    }

    public static final Parcelable.Creator<Category> CREATOR = new Parcelable.Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public Category insert(Datasource datasource){
        CategoryDb categoryDb = datasource.insertCategory(toCategoryDb());
        return fromCategoryDb(categoryDb);
    }

    public Category fromCategoryDb(CategoryDb categoryDb){
        this.setId(categoryDb.getId());
        this.setName(categoryDb.getName());
        this.setIncome(categoryDb.isIncome());
        this.setColor(categoryDb.getColor());
        this.setLimit(categoryDb.getLimit());
        this.setDeleted(categoryDb.isDeleted());
        return this;
    }

    public CategoryDb toCategoryDb(){
        CategoryDb categoryDb = new CategoryDb();
        categoryDb.setId(this.getId());
        categoryDb.setName(this.getName());
        categoryDb.setIncome(this.isIncome());
        categoryDb.setColor(this.getColor());
        categoryDb.setLimit(this.getLimit());
        categoryDb.setDeleted(this.isDeleted());
        return categoryDb;
    }

    public Category update(Datasource datasource) {
        CategoryDb categoryDb = datasource.updateCategory(toCategoryDb());
        return fromCategoryDb(categoryDb);
    }

    public boolean isOverLimit(double amount){
        if(limit>0) {
            return amount > limit;
        }
        return false;
    }
}
