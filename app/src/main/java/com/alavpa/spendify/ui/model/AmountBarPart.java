package com.alavpa.spendify.ui.model;

import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.model.Sector;

import java.util.ArrayList;
import java.util.List;

public class AmountBarPart{

    private float startX, endX;
    private float startY, endY;
    private float angle;

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

    public void calculateAngle() {
        angle = percent * 360.0f;
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

    public float getAngle() {
        return angle;
    }

    public static List<AmountBarPart> getParts(ResDatasource resDatasource, List<Sector> sectors, double total){

        List<AmountBarPart> parts = new ArrayList<>();

        for (Sector sector : sectors){

            int rgb = resDatasource.getColor(resDatasource.getCategoryColorsArray()[sector.getCategory().getColor()]);
            Double percent = Double.valueOf(sector.getAmount()/total);
            parts.add(new AmountBarPart(percent.floatValue(),rgb));
        }

        return parts;
    }
}
