package com.alavpa.spendify.domain.model.base;


import android.os.Parcel;
import android.os.Parcelable;

public class Alarm implements Parcelable {

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel in) {
            return new Alarm(in);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
    protected long date;

    public Alarm(){

    }

    protected Alarm(Parcel in) {
        this.date = in.readLong();
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.date);
    }
}
