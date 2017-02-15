package com.alavpa.spendify.domain.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by alavpa on 14/02/17.
 */

public class Period {

    @IntDef({NO_PERIOD, PER_DAY, PER_WEEK, PER_MONTH, PER_YEAR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface PeriodMode{}

    public static final int NO_PERIOD = -1;
    public static final int PER_DAY = 0;
    public static final int PER_WEEK = 1;
    public static final int PER_MONTH = 2;
    public static final int PER_YEAR = 3;

    @PeriodMode
    private
    int mode;

    private
    List<Date> dates;

    public Period(){
        this.mode = NO_PERIOD;
        dates = new ArrayList<>();
    }

}
