package com.alavpa.spendify.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.alavpa.spendify.data.Datasource;
import com.alavpa.spendify.data.db.model.AlarmDb;

/**
 * Created by alavpa on 8/05/17.
 */

public class Alarm implements Parcelable{
    protected long id;
    protected Period period;
    protected String action;
    protected boolean active;
    protected long refId;

    public Alarm(){}

    public Alarm(String action){
        this.action = action;
        this.active = true;
    }

    public Alarm fromAlarmDb(AlarmDb alarmDb) {
        setId(alarmDb.getId());
        setPeriod(new Period(alarmDb.getDate(), alarmDb.getPeriod(), alarmDb.getTimes()));
        setAction(alarmDb.getAction());
        setActive(alarmDb.isActive());
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getRefId() {
        return refId;
    }

    public void setRefId(long refId) {
        this.refId = refId;
    }

    public Alarm getNextAlarm() {
        setPeriod(new Period(getPeriod().getNextDateInMillis(),getPeriod().getPeriod(), getPeriod().getTimes()));
        return this;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeParcelable(this.period, flags);
        dest.writeString(this.action);
        dest.writeByte(this.active ? (byte) 1 : (byte) 0);
        dest.writeLong(refId);
    }

    protected Alarm(Parcel in) {
        this.id = in.readLong();
        this.period = in.readParcelable(Period.class.getClassLoader());
        this.action = in.readString();
        this.active = in.readByte() != 0;
        this.refId = in.readLong();
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

    public Alarm insertAlarm(Datasource datasource) {
        AlarmDb alarmDb = datasource.insertAlarmDb(toAlarmDb());
        return new Alarm().fromAlarmDb(alarmDb);
    }

    public Alarm updateAlarm(Datasource datasource) {
        AlarmDb alarmDb = datasource.updateAlarmDb(toAlarmDb());
        return new Alarm().fromAlarmDb(alarmDb);
    }

    private AlarmDb toAlarmDb() {
        AlarmDb alarmDb = new AlarmDb();
        alarmDb.setAction(getAction());
        alarmDb.setActive(isActive());
        alarmDb.setRefId(getRefId());
        alarmDb.setId(getId());
        alarmDb.setDate(getPeriod().getDate());
        alarmDb.setPeriod(getPeriod().getPeriod());
        alarmDb.setTimes(getPeriod().getTimes());
        return alarmDb;
    }
}
