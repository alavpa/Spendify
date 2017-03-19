package com.alavpa.spendify.ui.custom.widgets;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Build;
import android.util.AttributeSet;

import com.alavpa.spendify.ui.model.AmountBarPart;

/**
 * Created by alavpa on 24/02/17.
 */

public class AmountBar extends AmountView {

    public AmountBar(Context context) {
        super(context);
    }

    public AmountBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AmountBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AmountBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void drawParts(Canvas canvas) {
        int width = getWidth() - SHADOW_DISTANCE;
        int height = getHeight()-SHADOW_DISTANCE;


        float offsetWidth = SHADOW_DISTANCE;
        for (AmountBarPart part : parts) {

            part.calculateX(offsetWidth, width);
            part.calculateY(0, height);
            offsetWidth = part.getEndX();

            paint.setColor(part.getColor());
            canvas.drawRect(part.getStartX()- SHADOW_DISTANCE,
                    part.getStartY() + SHADOW_DISTANCE,
                    part.getEndX() - SHADOW_DISTANCE,
                    part.getEndY() + SHADOW_DISTANCE,
                    shadow);

            canvas.drawRect(part.getStartX(),
                    part.getStartY(),
                    part.getEndX(),
                    part.getEndY(),
                    paint);

        }

    }
}
