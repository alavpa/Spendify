package com.alavpa.spendify.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.alavpa.spendify.R;

/**
 * Created by alavpa on 9/02/17.
 */

public class Keyboard extends FrameLayout {

    public Keyboard(Context context) {
        super(context);
        init(null);
    }

    public Keyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public Keyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Keyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext())
                .inflate(R.layout.keyboard,this,true);
    }
}
