package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.data.db.model.AlarmDb;

/**
 * Created by alavpa on 8/05/17.
 */

public class Alarm {
    private long id;
    private Period period;
    private Amount amount;

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }

    public Alarm fromAlarmDb(AlarmDb alarmDb) {
        setId(alarmDb.getId());
        setAmount(new Amount().fromAmountDb(alarmDb.getAmountDb()));
        setPeriod(new Period(alarmDb.getDate(), alarmDb.getPeriod(), alarmDb.getTimes()));
        return this;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
