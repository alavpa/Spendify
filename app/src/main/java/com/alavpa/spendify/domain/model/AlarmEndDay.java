package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AlarmEndDay extends Alarm implements Parcelable {

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
        super(AlarmManager.ACTION_ALARM_ENDDAY);
    }

    public AlarmEndDay(long time){
        this();
        setPeriod(new Period(time,Period.PER_DAY,1));
    }

    protected AlarmEndDay(Parcel in) {
        super(in);
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
