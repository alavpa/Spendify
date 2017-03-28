package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.data.alarm.AlarmManager;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class OfflimitAlarmTest {

    @Test
    public void checkDate() {

        OfflimitAlarm offlimitAlarm = new OfflimitAlarm();

        Calendar calendar = Calendar.getInstance();
        long calculated = offlimitAlarm.calculateDate(calendar.getTimeInMillis());
        calendar.add(Calendar.MINUTE,5);
        Assert.assertEquals(calendar.getTimeInMillis(),calculated);

    }

    @Test
    public void checkRequest(){
        Category category = new Category();
        category.setId(1);
        OfflimitAlarm offlimitAlarm = new OfflimitAlarm(category);

        int expected = AlarmManager.REQUEST_ALARM_OFFLIMIT + 1;
        int actual = offlimitAlarm.getRequest();
        Assert.assertEquals(expected,actual);
    }
}