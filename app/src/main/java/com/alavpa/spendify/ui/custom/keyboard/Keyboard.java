package com.alavpa.spendify.ui.custom.keyboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alavpa.spendify.R;

import java.text.DecimalFormat;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by alavpa on 9/02/17.
 */

public class Keyboard extends FrameLayout {

    private
    String integerPart = "";

    private
    String decimalPart = "";

    private boolean writingIntegerPart;

    private
    double value;
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

        writingIntegerPart = true;

    }

    public String getFormattedValue(){
        return decimalFormat.format(value);
    }

    public void setValue(String formattedValue) {
        try {
            if(formattedValue.isEmpty()) formattedValue = "0";
            this.value = decimalFormat.parse(formattedValue).doubleValue();
        }catch (Exception e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
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

        if(writingIntegerPart){
            integerPart += text;
        }else if(decimalPart.length()<decimals){
            decimalPart += text;
        }

        setInternalValue();
        if(onPressKey!=null){
            onPressKey.onPress(getValue());
        }
    }

    @OnClick(R.id.key_dot)
    public void onDotClick(View v) {

        if(decimals>0) {
            writingIntegerPart = false;
        }

    }

    @OnClick(R.id.key_action)
    public void onActionClick(View v) {

        if(!writingIntegerPart){
            if(decimalPart.isEmpty()){
                writingIntegerPart = true;
            }else {
                decimalPart = decimalPart.substring(0, decimalPart.length() - 1);
            }
        }

        if(writingIntegerPart && !integerPart.isEmpty()){
            integerPart = integerPart.substring(0,integerPart.length()-1);
        }

        setInternalValue();
        if(onPressKey!=null){
            onPressKey.onPress(getValue());
        }
    }

    private void setInternalValue(){
        String internalValue;
        if(integerPart.isEmpty()){
            internalValue = "0";
        }else {
            internalValue = integerPart;
        }

        if(!decimalPart.isEmpty()){
             internalValue += decimalFormat.getDecimalFormatSymbols().getDecimalSeparator()
                     +decimalPart;
        }

        setValue(internalValue);
    }

    public void setOnPressKey(OnPressKey onPressKey){
        this.onPressKey = onPressKey;
    }

    public interface OnPressKey{
        void onPress(double value);
    }
}
