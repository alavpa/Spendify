package com.alavpa.spendify.ui.model;

import com.alavpa.spendify.data.resources.ResDatasource;
import com.alavpa.spendify.domain.model.Amount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AmountBarPart{

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

    public static List<AmountBarPart> getParts(ResDatasource resDatasource, List<Amount> amounts, float total){
        Map<Integer, Double> valuesByColor = new HashMap<>();
        List<AmountBarPart> parts = new ArrayList<>();

        for(Amount amount : amounts){
            double value = 0;
            if(valuesByColor.keySet().contains(amount.getCategory().getColor())){
                value = valuesByColor.get(amount.getCategory().getColor());
                valuesByColor.remove(amount.getCategory().getColor());
            }

            value +=amount.getAmount();
            valuesByColor.put(amount.getCategory().getColor(),value);
        }

        for (Integer color : valuesByColor.keySet()){
            float value = valuesByColor.get(color).floatValue();

            int rgb = resDatasource.getColor(resDatasource.getCategoryColorsArray()[color]);
            parts.add(new AmountBarPart(value/total,rgb));
        }
        return parts;
    }
}
