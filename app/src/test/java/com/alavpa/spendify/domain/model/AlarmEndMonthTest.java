package com.alavpa.spendify.domain.model;

import com.alavpa.spendify.ui.utils.AlarmUtils;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;

public class AlarmEndMonthTest {
    @Test
    public void calculateDate() throws Exception {
        AlarmEndMonth alarmEndMonth = new AlarmEndMonth();

        Calendar calendar = Calendar.getInstance();
        Calendar expectedCalendarPost = Calendar.getInstance();
        expectedCalendarPost.setTimeInMillis(calendar.getTimeInMillis());
        Calendar expectedCalendarPre = Calendar.getInstance();
        expectedCalendarPre.setTimeInMillis(calendar.getTimeInMillis());

        calendar.set(Calendar.DATE,13);
        calendar.set(Calendar.MONTH,2);
        calendar.set(Calendar.YEAR,2017);


        expectedCalendarPost.set(Calendar.DATE,14);
        expectedCalendarPost.set(Calendar.MONTH,2);
        expectedCalendarPost.set(Calendar.YEAR,2017);

        expectedCalendarPre.set(Calendar.DATE,9);
        expectedCalendarPre.set(Calendar.MONTH,3);
        expectedCalendarPre.set(Calendar.YEAR,2017);

        long calculatedPost = new AlarmUtils().calculateEndMonth(15
                ,expectedCalendarPre.getTimeInMillis());
        long calculatedPre = new AlarmUtils().calculateEndMonth(10
                ,expectedCalendarPre.getTimeInMillis());

        Assert.assertEquals("Post", expectedCalendarPost.getTimeInMillis(),calculatedPost);
        Assert.assertEquals("Pre",expectedCalendarPre.getTimeInMillis(),calculatedPre);

    }

    @Test
    public void getNextAlarm(){
        AlarmEndMonth alarmEndMonth = new AlarmEndMonth();

        Calendar calendar = Calendar.getInstance();
        Calendar nextCalendar = Calendar.getInstance();
        nextCalendar.setTimeInMillis(calendar.getTimeInMillis());

        calendar.set(Calendar.DATE,13);
        calendar.set(Calendar.MONTH,2);
        calendar.set(Calendar.YEAR,2017);

        nextCalendar.set(Calendar.DATE,4);
        nextCalendar.set(Calendar.MONTH,4);
        nextCalendar.set(Calendar.YEAR,2017);

        long date = new AlarmUtils().calculateEndMonth(5, calendar.getTimeInMillis());
        alarmEndMonth.getPeriod().setDate(date);
        alarmEndMonth.setPeriod(new Period(date,Period.PER_MONTH,1));

        long next = alarmEndMonth.getNextAlarm().getPeriod().getDate();

        Assert.assertEquals(nextCalendar.getTimeInMillis(),next);

    }

}