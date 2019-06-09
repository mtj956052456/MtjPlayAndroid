package com.zhenqi.baselibrary.view.myprogressbar;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/10
 * 描述:
 */
public class MyProgressBarBean {

    private int maxValue;   //总值

    private int value;      //当前值

    private int[] colors;   //颜色

    private String type;    //类型

    private int radius;    //半径

    private int arcRadius;    //圆环的宽度

    public MyProgressBarBean() {

    }

    public MyProgressBarBean(int maxValue, int value, int[] colors, String type, int radius, int arcRadius) {
        this.maxValue = maxValue;
        this.value = value;
        this.colors = colors;
        this.type = type;
        this.radius = radius;
        this.arcRadius = arcRadius;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int[] getColors() {
        return colors;
    }

    public void setColors(int[] colors) {
        this.colors = colors;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getArcRadius() {
        return arcRadius;
    }

    public void setArcRadius(int arcRadius) {
        this.arcRadius = arcRadius;
    }
}
