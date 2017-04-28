package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.Alarm;

import java.util.Calendar;

public class AlarmOfflimit extends Alarm implements Parcelable {

    public static final Creator<AlarmOfflimit> CREATOR = new Creator<AlarmOfflimit>() {
        @Override
        public AlarmOfflimit createFromParcel(Parcel source) {
            return new AlarmOfflimit(source);
        }

        @Override
        public AlarmOfflimit[] newArray(int size) {
            return new AlarmOfflimit[size];
        }
    };
    private Category category;

    public AlarmOfflimit(){

    }


    public AlarmOfflimit(Category category){
        this.category = category;
        date = calculateDate(Calendar.getInstance().getTimeInMillis());
    }

    protected AlarmOfflimit(Parcel in) {
        super(in);
        this.category = in.readParcelable(Category.class.getClassLoader());
    }

    public long calculateDate(long time){
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(time);
        timeCalendar.add(Calendar.MINUTE,5);

        return timeCalendar.getTimeInMillis();
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getRequest(){
        return AlarmManager.REQUEST_ALARM_OFFLIMIT + (int)category.getId();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.category, flags);
    }
}
