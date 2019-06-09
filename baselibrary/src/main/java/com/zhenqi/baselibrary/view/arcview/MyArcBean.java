package com.zhenqi.baselibrary.view.arcview;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/10
 * 描述:
 */
public class MyArcBean {

    private float value;      //当前值

    private int color;      //颜色

    private String name;


    public MyArcBean() {

    }

    public MyArcBean(float value, int color,String name) {
        this.value = value;
        this.color = color;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

}
