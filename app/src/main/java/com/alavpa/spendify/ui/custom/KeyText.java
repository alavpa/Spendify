package com.alavpa.spendify.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.alavpa.spendify.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by alavpa on 9/02/17.
 */

public class KeyText extends FrameLayout {

    @BindView(R.id.tv_key)
    TextView tvKey;

    public KeyText(Context context) {
        super(context);
        init(null);
    }

    public KeyText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public KeyText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);

    }

    private void init(AttributeSet attrs){
        LayoutInflater.from(getContext())
                .inflate(R.layout.key_text,this,true);
        ButterKnife.bind(this);
        setAttrs(attrs);
    }

    public void setAttrs(AttributeSet attrs){
        TypedArray a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.KeyText);

        String text;
        try {
            text = a.getString(R.styleable.KeyText_text);
        } finally {
            a.recycle();
        }

        tvKey.setText(text);
    }
}
