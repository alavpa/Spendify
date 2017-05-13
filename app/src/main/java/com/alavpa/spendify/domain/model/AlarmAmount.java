package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AlarmAmount extends Alarm implements Parcelable {

    public static final Creator<AlarmAmount> CREATOR = new Creator<AlarmAmount>() {
        @Override
        public AlarmAmount createFromParcel(Parcel source) {
            return new AlarmAmount(source);
        }

        @Override
        public AlarmAmount[] newArray(int size) {
            return new AlarmAmount[size];
        }
    };

    public AlarmAmount(){
        super(AlarmManager.ACTION_ALARM_AMOUNT);
    }

    public AlarmAmount(Amount amount){
        this();
        long date = calculateDate(Calendar.getInstance().getTimeInMillis(), amount.getPeriod());
        setPeriod(new Period(date,amount.getPeriod().getPeriod(),amount.getPeriod().getTimes()));
        setRefId(amount.getId());
    }

    protected AlarmAmount(Parcel in) {
        super(in);
    }

    public long calculateDate(long current, Period period){
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(period.getDate());

        long date = alarmCalendar.getTimeInMillis();

        if(period.getPeriod() != Period.NO_PERIOD) {
            while (date < current) {
                date = period.getNextDateInMillis();
            }
        }

        return date;
    }

    public AlarmAmount getNextAlarm() {
        setPeriod(getPeriod().getNextPeriod());
        return this;
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
