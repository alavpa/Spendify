package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.ui.utils.AlarmUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class AlarmEndDayTest {

    @Test
    public void calculateDate() throws Exception {

        AlarmEndDay alarmEndDay = new AlarmEndDay();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,5);
        calendar.set(Calendar.MONTH,5);
        calendar.set(Calendar.YEAR,2017);
        calendar.set(Calendar.HOUR_OF_DAY,21);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,53);

        Calendar expected = Calendar.getInstance();
        expected.setTimeInMillis(calendar.getTimeInMillis());
        expected.set(Calendar.DATE,6);
        expected.set(Calendar.MONTH,5);
        expected.set(Calendar.YEAR,2017);
        expected.set(Calendar.HOUR_OF_DAY,20);
        expected.set(Calendar.MINUTE,0);
        expected.set(Calendar.SECOND,0);

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(0);
        time.set(Calendar.HOUR_OF_DAY,20);
        time.set(Calendar.MINUTE,0);
        time.set(Calendar.SECOND,0);

        long date = new AlarmUtils().calculateNextEndDay(time.getTimeInMillis());

        Assert.assertEquals(expected.getTimeInMillis(),date);

        calendar.set(Calendar.HOUR_OF_DAY,18);
        expected.set(Calendar.DATE,5);

        date = new AlarmUtils().calculateNextEndDay(time.getTimeInMillis());
        Assert.assertEquals(expected.getTimeInMillis(),date);

    }

    @Test
    public void getNextAlarm() throws Exception {

        AlarmEndDay alarmEndDay = new AlarmEndDay();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE,5);
        calendar.set(Calendar.MONTH,5);
        calendar.set(Calendar.YEAR,2017);
        calendar.set(Calendar.HOUR_OF_DAY,21);
        calendar.set(Calendar.MINUTE,30);
        calendar.set(Calendar.SECOND,53);
        calendar.set(Calendar.MILLISECOND,0);

        Calendar expected = Calendar.getInstance();
        expected.setTimeInMillis(calendar.getTimeInMillis());
        expected.set(Calendar.DATE,7);
        expected.set(Calendar.MONTH,5);
        expected.set(Calendar.YEAR,2017);
        expected.set(Calendar.HOUR_OF_DAY,20);
        expected.set(Calendar.MINUTE,0);
        expected.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        Calendar time = Calendar.getInstance();
        time.setTimeInMillis(0);
        time.set(Calendar.HOUR_OF_DAY,20);
        time.set(Calendar.MINUTE,0);
        time.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);

        long date = new AlarmUtils().calculateNextEndDay(time.getTimeInMillis());
        alarmEndDay.getPeriod().setDate(date);
        alarmEndDay.setPeriod(new Period(date,Period.PER_DAY,1));

        Assert.assertEquals(expected.getTimeInMillis(),
                alarmEndDay.getNextAlarm().getPeriod().getDate());

    }

}