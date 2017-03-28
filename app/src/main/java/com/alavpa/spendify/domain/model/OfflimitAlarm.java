package com.alavpa.spendify.domain.model;

import android.os.Parcel;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.Alarm;

import java.util.Calendar;

public class OfflimitAlarm extends Alarm {

    private Category category;
    public OfflimitAlarm(){

    }
    public OfflimitAlarm(Category category){
        this.category = category;
        date = calculateDate(Calendar.getInstance().getTimeInMillis());
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

    public void set(AlarmManager alarmManager){
        setAlarm(alarmManager,AlarmManager.ACTION_ALARM_OFFLIMIT, getRequest());
    }

    public void cancel(AlarmManager alarmManager){
        cancelAlarm(alarmManager,AlarmManager.ACTION_ALARM_OFFLIMIT,getRequest());
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

    protected OfflimitAlarm(Parcel in) {
        super(in);
        this.category = in.readParcelable(Category.class.getClassLoader());
    }

    public static final Creator<OfflimitAlarm> CREATOR = new Creator<OfflimitAlarm>() {
        @Override
        public OfflimitAlarm createFromParcel(Parcel source) {
            return new OfflimitAlarm(source);
        }

        @Override
        public OfflimitAlarm[] newArray(int size) {
            return new OfflimitAlarm[size];
        }
    };
}
