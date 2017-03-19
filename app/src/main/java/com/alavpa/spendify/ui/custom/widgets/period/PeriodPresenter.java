package com.alavpa.spendify.ui.custom.widgets.period;

import com.alavpa.spendify.domain.model.Period;

public class PeriodPresenter {

    Period period;
    PeriodView view;

    public PeriodPresenter(PeriodView view){
        this.view = view;
        period = new Period();
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
        if (period.getPeriod() != Period.NO_PERIOD) {
            view.setPeriod(period.getPeriod());
        }
    }

    public void onChangePeriod(int position) {
        period.setPeriod(position);
        view.setTimes(period.getTimes()-1);
    }

    public void setTimes(int position) {
        period.setTimes(position+1);
    }
}
