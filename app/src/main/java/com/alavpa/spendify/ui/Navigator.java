package com.alavpa.spendify.ui;

import android.content.Context;
import android.content.Intent;

import com.alavpa.spendify.ui.details.DetailsActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.alavpa.spendify.ui.details.DetailsActivity.EXTRA_AMOUNT;
import static com.alavpa.spendify.ui.details.DetailsActivity.EXTRA_ISINCOME;

/**
 * Created by alavpa on 18/02/17.
 */
@Singleton
public class Navigator {

    @Inject
    public Navigator(){}

    private Intent getIntent(Context context, Class activityClass){
        Intent intent = new Intent(context, activityClass);
        return intent;
    }
    public void openDetails(Context context, String amount, boolean isIncome){
        Intent intent = getIntent(context, DetailsActivity.class);
        intent.putExtra(EXTRA_AMOUNT,amount);
        intent.putExtra(EXTRA_ISINCOME,isIncome);
        context.startActivity(intent);
    }
}
