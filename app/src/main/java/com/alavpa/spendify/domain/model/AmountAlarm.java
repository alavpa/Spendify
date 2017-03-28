package com.alavpa.spendify.domain.model;

import android.os.Parcel;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AmountAlarm extends AlarmRepeat{

    private Amount amount;

    public AmountAlarm(){}

    public AmountAlarm(Amount amount){
        this.amount = amount;
        date = calculateDate(Calendar.getInstance().getTimeInMillis(), amount.getPeriod().getDate());
        period = new Period(date,amount.getPeriod().getPeriod(),amount.getPeriod().getTimes());
    }

    public long calculateDate(long current, long time){
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(time);

        long date = alarmCalendar.getTimeInMillis();
        while(date<current){
            date = amount.getPeriod().getNextDateInMillis();
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

    protected AmountAlarm(Parcel in) {
        super(in);
        this.amount = in.readParcelable(Amount.class.getClassLoader());
    }

    public static final Creator<AmountAlarm> CREATOR = new Creator<AmountAlarm>() {
        @Override
        public AmountAlarm createFromParcel(Parcel source) {
            return new AmountAlarm(source);
        }

        @Override
        public AmountAlarm[] newArray(int size) {
            return new AmountAlarm[size];
        }
    };
}
