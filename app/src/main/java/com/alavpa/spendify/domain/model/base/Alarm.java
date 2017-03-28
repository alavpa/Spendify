package com.alavpa.spendify.domain.model.base;


import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.alarm.AlarmManager;

public class Alarm implements Parcelable {

    protected long date;

    public Alarm(){

    }
    public Alarm(long date){
        this.date = date;
    }

    public void setAlarm(AlarmManager alarmManager, String action, int request){
        alarmManager.setAlarm(action,request,date);
    }

    public void cancelAlarm(AlarmManager alarmManager, String action, int request){
        alarmManager.cancelAlarm(action,request);
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

    protected Alarm(Parcel in) {
        this.date = in.readLong();
    }

    public static final Creator<Alarm> CREATOR = new Creator<Alarm>() {
        @Override
        public Alarm createFromParcel(Parcel source) {
            return new Alarm(source);
        }

        @Override
        public Alarm[] newArray(int size) {
            return new Alarm[size];
        }
    };
}
