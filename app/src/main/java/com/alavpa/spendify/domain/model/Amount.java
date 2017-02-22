package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alavpa on 9/02/17.
 */

public class Amount implements Parcelable {

    private
    long id;

    private
    boolean income;

    private
    double amount;

    private
    String description;

    private
    Category category;

    private
    Period period;

    public Amount(){
        this.id = 0;
        this.income = false;
        this.description = "";
        this.amount = 0.0f;
        this.category = null;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeByte(this.income ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.amount);
        dest.writeString(this.description);
        dest.writeParcelable(this.category, flags);
        dest.writeParcelable(this.period, flags);
    }

    protected Amount(Parcel in) {
        this.id = in.readLong();
        this.income = in.readByte() != 0;
        this.amount = in.readDouble();
        this.description = in.readString();
        this.category = in.readParcelable(Category.class.getClassLoader());
        this.period = in.readParcelable(Period.class.getClassLoader());
    }

    public static final Parcelable.Creator<Amount> CREATOR = new Parcelable.Creator<Amount>() {
        @Override
        public Amount createFromParcel(Parcel source) {
            return new Amount(source);
        }

        @Override
        public Amount[] newArray(int size) {
            return new Amount[size];
        }
    };
}
