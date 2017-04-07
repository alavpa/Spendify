package com.alavpa.spendify.domain.model.base;


import android.os.Parcel;

import com.alavpa.spendify.domain.model.Period;

public class AlarmRepeat extends Alarm{

    protected Period period;

    public AlarmRepeat(){

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
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.period, flags);
    }

    protected AlarmRepeat(Parcel in) {
        super(in);
        this.period = in.readParcelable(Period.class.getClassLoader());
    }

}
