package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.alarm.AlarmManager;
import com.alavpa.spendify.domain.model.base.AlarmRepeat;

import java.util.Calendar;

public class AlarmAmount extends AlarmRepeat implements Parcelable {

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
    private Amount amount;

    public AlarmAmount(){}

    public AlarmAmount(Amount amount){
        this.amount = amount;
        date = calculateDate(Calendar.getInstance().getTimeInMillis(), amount.getPeriod().getDate());
        period = new Period(date,amount.getPeriod().getPeriod(),amount.getPeriod().getTimes());
    }

    protected AlarmAmount(Parcel in) {
        super(in);
        this.amount = in.readParcelable(Amount.class.getClassLoader());
    }

    public long calculateDate(long current, long time){
        Calendar alarmCalendar = Calendar.getInstance();
        alarmCalendar.setTimeInMillis(time);

        long date = alarmCalendar.getTimeInMillis();

        if(amount.getPeriod().getPeriod() != Period.NO_PERIOD) {
            while (date < current) {
                date = amount.getPeriod().getNextDateInMillis();
            }
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

    public AlarmAmount getNextAlarm() {
        AlarmAmount alarmAmount = new AlarmAmount(amount);
        alarmAmount.setDate(period.getNextDateInMillis());
        alarmAmount.setPeriod(new Period(date, period.getPeriod(), period.getTimes()));
        return alarmAmount;
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
}
