package com.alavpa.spendify.domain.model;

import android.os.Parcel;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AmountPeriodicallyAlarm extends AlarmRepeat{

    private Amount amount;

    public AmountPeriodicallyAlarm(Amount amount){
        this.amount = amount;
        date = calculateDate(amount.getPeriod().getDate());
        period = new Period(date,period.getPeriod(),period.getTimes());
    }

    private long calculateDate(long time){
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(time);

        long date = alarmCalendar.getTimeInMillis();
        while(date<System.currentTimeMillis()){
            date = period.getNextDateInMillis();
        }

        return date;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public int getRequest(){
        return AlarmManager.REQUEST_ALARM_AMOUNT + (int)amount.getId();
    }
    public void set(AlarmManager alarmManager){
        setAlarm(alarmManager,AlarmManager.ACTION_ALARM_AMOUNT, getRequest());
    }

    public void cancel(AlarmManager alarmManager){
        cancelAlarm(alarmManager,AlarmManager.ACTION_ALARM_AMOUNT,getRequest());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.amount, flags);
    }

    protected AmountPeriodicallyAlarm(Parcel in) {
        super(in);
        this.amount = in.readParcelable(Amount.class.getClassLoader());
    }

    public static final Creator<AmountPeriodicallyAlarm> CREATOR = new Creator<AmountPeriodicallyAlarm>() {
        @Override
        public AmountPeriodicallyAlarm createFromParcel(Parcel source) {
            return new AmountPeriodicallyAlarm(source);
        }

        @Override
        public AmountPeriodicallyAlarm[] newArray(int size) {
            return new AmountPeriodicallyAlarm[size];
        }
    };
}
