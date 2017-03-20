package com.alavpa.spendify.domain;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DateUtils {

    @Inject
    public DateUtils(){

    }

    public Calendar calculateFrom(Long timeInMillis, int day){
        Calendar from = Calendar.getInstance();
        from.setTimeInMillis(timeInMillis);
        from.set(Calendar.HOUR_OF_DAY,0);
        from.set(Calendar.MINUTE,0);
        from.set(Calendar.SECOND,0);
        if(from.get(Calendar.DATE)<day){
            from.add(Calendar.MONTH,-1);
        }
        from.set(Calendar.DATE,day);

        return from;
    }

    public Calendar calculateTo(Long timeInMillis,int day){
        Calendar to = Calendar.getInstance();
        to.setTimeInMillis(timeInMillis);
        to.set(Calendar.HOUR_OF_DAY,23);
        to.set(Calendar.MINUTE,59);
        to.set(Calendar.SECOND,59);
        if(to.get(Calendar.DATE)>=day){
            to.add(Calendar.MONTH,1);
        }
        to.set(Calendar.DATE,day);
        to.add(Calendar.DATE,-1);

        return to;
    }
}
