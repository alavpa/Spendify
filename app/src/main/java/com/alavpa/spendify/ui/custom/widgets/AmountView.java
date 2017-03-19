package com.alavpa.spendify.ui.custom.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.alavpa.spendify.ui.model.AmountBarPart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alavpa on 24/02/17.
 */

public abstract class AmountView extends View {

    protected static final int SHADOW_COLOR = 0x99000000;
    protected static final int SHADOW_DISTANCE = 1;

    List<AmountBarPart> parts;
    Paint paint = new Paint();
    Paint shadow = new Paint();

    public AmountView(Context context) {
        super(context);
        init();
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AmountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AmountView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        parts = new ArrayList<>();
        paint.setStyle(Paint.Style.FILL);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);


        shadow.setStyle(Paint.Style.FILL);
        shadow.setColor(SHADOW_COLOR);
        shadow.setFlags(Paint.ANTI_ALIAS_FLAG);
        shadow.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawParts(canvas);
    }

    protected abstract void drawParts(Canvas canvas);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = getPaddingBottom() + getPaddingTop() + getSuggestedMinimumHeight();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setParts(List<AmountBarPart> parts) {
        this.parts = parts;
    }


}
