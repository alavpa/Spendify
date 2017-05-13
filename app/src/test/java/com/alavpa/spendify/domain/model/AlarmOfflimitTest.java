package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.data.alarm.AlarmManager;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class AlarmOfflimitTest {

    @Test
    public void checkDate() {

        AlarmOfflimit alarmOfflimit = new AlarmOfflimit();

        Calendar calendar = Calendar.getInstance();
        long calculated = alarmOfflimit.calculateDate(calendar.getTimeInMillis());
        calendar.add(Calendar.MINUTE,5);
        Assert.assertEquals(calendar.getTimeInMillis(),calculated);

    }
}