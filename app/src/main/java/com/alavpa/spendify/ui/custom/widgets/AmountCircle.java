package com.alavpa.spendify.ui.custom.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;

import com.alavpa.spendify.ui.model.AmountBarPart;

/**
 * Created by alavpa on 24/02/17.
 */

public class AmountCircle extends AmountView {

    final RectF oval = new RectF();
    final RectF ovalShadow = new RectF();

    public AmountCircle(Context context) {
        super(context);
    }

    public AmountCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AmountCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AmountCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void drawParts(Canvas canvas) {

        int width = getWidth();
        int height = getHeight();

        int min = Math.min(width,height);
        int max = Math.max(width,height);
        int offset = (max-min)/2;

        int r = min/2;
        int cx;
        int cy = height/2;

        if(width>=height) {
            oval.set(offset, 0, min + offset - SHADOW_DISTANCE, min -SHADOW_DISTANCE);
            cx = width/2 - SHADOW_DISTANCE;
            cy = height/2;
        }else{
            oval.set(SHADOW_DISTANCE, offset, min, min + offset - SHADOW_DISTANCE);
            cx = width/2;
            cy = height/2 + SHADOW_DISTANCE;
        }

        canvas.drawCircle(cx,cy,r,shadow);

        float angleOffset = 0;
        for(AmountBarPart amountBarPart : parts){
            amountBarPart.calculateAngle();
            paint.setColor(amountBarPart.getColor());
            canvas.drawArc(oval, angleOffset, amountBarPart.getAngle(), true, paint);
            angleOffset += amountBarPart.getAngle();
        }
    }
}
