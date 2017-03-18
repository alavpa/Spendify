package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.db.model.SectorDb;

public class Sector implements Parcelable{
    private
    double amount;

    private
    Category category;

    public Sector(){
        amount = 0.0;
        category = null;
    }

    protected Sector(Parcel in) {
        amount = in.readDouble();
        category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<Sector> CREATOR = new Creator<Sector>() {
        @Override
        public Sector createFromParcel(Parcel in) {
            return new Sector(in);
        }

        @Override
        public Sector[] newArray(int size) {
            return new Sector[size];
        }
    };

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Sector fromSectorDb(SectorDb sectorDb){
        this.amount = sectorDb.getAmount();
        this.category = new Category().fromCategoryDb(sectorDb.getCategoryDb());
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeDouble(amount);
        parcel.writeParcelable(category,flags);
    }
}
