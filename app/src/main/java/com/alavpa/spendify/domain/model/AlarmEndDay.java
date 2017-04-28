package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AlarmEndDay extends AlarmRepeat implements Parcelable {

    public static final int NOTIFICATION_ENDDAY_ID = 1;
    public static final Creator<AlarmEndDay> CREATOR = new Creator<AlarmEndDay>() {
        @Override
        public AlarmEndDay createFromParcel(Parcel source) {
            return new AlarmEndDay(source);
        }

        @Override
        public AlarmEndDay[] newArray(int size) {
            return new AlarmEndDay[size];
        }
    };

    public AlarmEndDay(){

    }

    public AlarmEndDay(long time){
        date = calculateDate(Calendar.getInstance().getTimeInMillis(),time);
        period = new Period(date,Period.PER_DAY,1);
    }

    protected AlarmEndDay(Parcel in) {
        super(in);
    }

    public long calculateDate(long currentTime, long time){
        Calendar timeCalendar = Calendar.getInstance();
        timeCalendar.setTimeInMillis(time);

        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(currentTime);

        alarmCalendar.set(Calendar.HOUR_OF_DAY,timeCalendar.get(Calendar.HOUR_OF_DAY));
        alarmCalendar.set(Calendar.MINUTE,timeCalendar.get(Calendar.MINUTE));
        alarmCalendar.set(Calendar.SECOND,0);

        long date = alarmCalendar.getTimeInMillis();
        while(date<currentTime){
            alarmCalendar.add(Calendar.DATE,1);
            date = alarmCalendar.getTimeInMillis();
        }

        return date;
    }

    public AlarmEndDay getNextAlarm() {
        AlarmEndDay alarmEndDay = new AlarmEndDay();
        alarmEndDay.setDate(period.getNextDateInMillis());
        alarmEndDay.setPeriod(new Period(date,period.getPeriod(),period.getTimes()));
        return alarmEndDay;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }
}
