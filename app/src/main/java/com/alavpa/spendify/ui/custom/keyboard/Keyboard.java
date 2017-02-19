package com.alavpa.spendify.ui.custom.keyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.alavpa.spendify.R;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 9/02/17.
 */

public class Keyboard extends FrameLayout {

    private
    String value = "0";
    private
    DecimalFormat decimalFormat = new DecimalFormat();
    private OnPressKey onPressKey;
    private int decimals;

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
        ButterKnife.bind(this);

        TypedArray a = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.Keyboard);

        int decimals;
        try {
            decimals = a.getInteger(R.styleable.Keyboard_decimals,0);
        } finally {
            a.recycle();
        }

        decimalFormat.setMaximumFractionDigits(decimals);
        decimalFormat.setMinimumFractionDigits(decimals);

        setDecimals(decimals);

    }

    public String getValue() {
        return decimalFormat.format(Double.parseDouble(value));
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    @OnClick({R.id.key_one,
            R.id.key_two,
            R.id.key_three,
            R.id.key_four,
            R.id.key_five,
            R.id.key_six,
            R.id.key_seven,
            R.id.key_eight,
            R.id.key_nine,
            R.id.key_zero})
    public void onKeyClick(View v) {

        KeyText keyText = (KeyText)v;

        String text = keyText.getText();

        if(value.contains(".")){
            int size = value.length();
            int pos = value.indexOf(".");
            if(size-pos-1>=decimals){
                return;
            }
        }
        value += text;

        if(onPressKey!=null){
            onPressKey.onPress(getValue());
        }
    }

    @OnClick(R.id.key_dot)
    public void onDotClick(View v){

        String separator = ((KeyText)v).getText();
        if(decimals>0 && !value.contains(separator)){
            value+=separator;
            if(onPressKey!=null){
                onPressKey.onPress(getValue());
            }
        }

    }

    @OnClick(R.id.key_action)
    public void onActionClick(View v){
        if(!value.isEmpty()) {
            value = value.substring(0, value.length() - 1);

            if(value.isEmpty()){
                value = "0";
            } else if(value.indexOf(".")==value.length()-1){
                value = value.substring(0, value.length() - 1);
            }

            if(value.isEmpty()){
                value = "0";
            }

            if(onPressKey!=null){
                onPressKey.onPress(getValue());
            }
        }
    }

    public void setOnPressKey(OnPressKey onPressKey){
        this.onPressKey = onPressKey;
    }

    public interface OnPressKey{
        void onPress(String value);
    }

}
