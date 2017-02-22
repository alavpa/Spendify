package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.income ? (byte) 1 : (byte) 0);
        dest.writeString(this.name);
    }

    protected Category(Parcel in) {
        this.id = in.readLong();
        this.income = in.readByte() != 0;
        this.name = in.readString();
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
}
