package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.data.alarm.AlarmManager;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class AmountAlarmTest {
    @Test
    public void calculateDate() throws Exception {

        Calendar current = Calendar.getInstance();
        current.set(Calendar.DATE,8);
        current.set(Calendar.MONTH,5);
        current.set(Calendar.YEAR,2017);
        current.set(Calendar.HOUR_OF_DAY,23);
        current.set(Calendar.MINUTE,30);
        current.set(Calendar.SECOND,53);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,18);
        calendar.set(Calendar.MONTH,5);
        calendar.set(Calendar.YEAR,2017);
        calendar.set(Calendar.HOUR_OF_DAY,21);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,53);

        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.DATE,18);
        expected.set(Calendar.MONTH,5);
        expected.set(Calendar.YEAR,2017);
        expected.set(Calendar.HOUR_OF_DAY,21);
        expected.set(Calendar.MINUTE,30);
        expected.set(Calendar.SECOND,53);

        Period period = new Period(calendar.getTimeInMillis(),Period.PER_MONTH,1);
        Amount amount = new Amount();
        amount.setPeriod(period);

        AmountAlarm amountAlarm = new AmountAlarm();
        amountAlarm.setAmount(amount);

        long date = amountAlarm.calculateDate(current.getTimeInMillis(),amount.getPeriod().getDate());

        Assert.assertEquals(expected.getTimeInMillis(),date);

        current.set(Calendar.DATE,21);
        expected.set(Calendar.MONTH,6);

        date = amountAlarm.calculateDate(current.getTimeInMillis(),amount.getPeriod().getDate());

        Assert.assertEquals(expected.getTimeInMillis(),date);

    }

    @Test
    public void getRequest() throws Exception {
        Amount amount = new Amount();
        amount.setId(5);

        int expected = AlarmManager.REQUEST_ALARM_AMOUNT + (int)amount.getId();

        AmountAlarm amountAlarm = new AmountAlarm(amount);
        Assert.assertEquals(expected,amountAlarm.getRequest());
    }

    @Test
    public void getNextAlarm(){
        Calendar current = Calendar.getInstance();
        current.set(Calendar.DATE,21);
        current.set(Calendar.MONTH,5);
        current.set(Calendar.YEAR,2017);
        current.set(Calendar.HOUR_OF_DAY,23);
        current.set(Calendar.MINUTE,30);
        current.set(Calendar.SECOND,53);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,18);
        calendar.set(Calendar.MONTH,5);
        calendar.set(Calendar.YEAR,2017);
        calendar.set(Calendar.HOUR_OF_DAY,21);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,53);

        Calendar expected = Calendar.getInstance();
        expected.set(Calendar.DATE,18);
        expected.set(Calendar.MONTH,7);
        expected.set(Calendar.YEAR,2017);
        expected.set(Calendar.HOUR_OF_DAY,21);
        expected.set(Calendar.MINUTE,30);
        expected.set(Calendar.SECOND,53);

        Period period = new Period(calendar.getTimeInMillis(),Period.PER_MONTH,1);
        Amount amount = new Amount();
        amount.setPeriod(period);

        AmountAlarm amountAlarm = new AmountAlarm();
        amountAlarm.setAmount(amount);

        long date = amountAlarm.calculateDate(current.getTimeInMillis(),amount.getPeriod().getDate());
        Period periodAlarm = new Period(date,amount.getPeriod().getPeriod(),amount.getPeriod().getTimes());

        amountAlarm.setDate(date);
        amountAlarm.setPeriod(periodAlarm);

        Assert.assertEquals(expected.getTimeInMillis(),amountAlarm.getNextAlarm().getDate());
    }

}