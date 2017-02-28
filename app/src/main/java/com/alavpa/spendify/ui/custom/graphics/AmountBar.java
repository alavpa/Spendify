package com.alavpa.spendify.ui.custom.graphics;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

/**
 * Created by alavpa on 24/02/17.
 */

public class AmountBar extends View {

    List<AmountBarPart> parts;
    Paint paint = new Paint();

    public AmountBar(Context context) {
        super(context);
        init();
    }

    public AmountBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AmountBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AmountBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();

        if (parts != null) {
            float offsetWidth = 0;
            for (AmountBarPart part : parts) {

                part.calculateX(offsetWidth,width);
                part.calculateY(0,height);
                offsetWidth = part.getEndX();

                paint.setColor(part.getColor());
                canvas.drawRect(part.getStartX(),
                        part.getStartY(),
                        part.getEndX(),
                        part.getEndY(), paint);

            }
        }

    }

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

    public List<AmountBarPart> getParts() {
        return parts;
    }

    public void setParts(List<AmountBarPart> parts) {
        this.parts = parts;
    }

    public static class AmountBarPart{

        private float startX, endX;
        private float startY, endY;

        private
        float percent;

        private
        int color;

        public AmountBarPart(float percent, int color){
            this.percent = percent;
            this.color = color;
        }

        public float getPercent() {
            return percent;
        }

        public int getColor() {
            return color;
        }

        public void calculateX(float offsetWidth, float width) {
            startX = offsetWidth;
            endX = offsetWidth + percent * width;
        }

        public void calculateY(float offsetHeight, float height) {
            startY = offsetHeight;
            endY = offsetHeight + height;
        }

        public float getStartX() {
            return startX;
        }

        public float getEndX() {
            return endX;
        }

        public float getStartY() {
            return startY;
        }

        public float getEndY() {
            return endY;
        }
    }
}
