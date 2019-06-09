package com.zhenqi.baselibrary.view.crossprogressbar;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/14
 * 描述:
 */
public class CrossProgressbarBean {

    private String type;         //类型

    private float leftValue;    //

    private float rightValue;   //


    public CrossProgressbarBean() {
    }

    public CrossProgressbarBean(String type, float leftValue,float rightValue) {
        this.type = type;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(float leftValue) {
        this.leftValue = leftValue;
    }

    public float getRightValue() {
        return rightValue;
    }

    public void setRightValue(float rightValue) {
        this.rightValue = rightValue;
    }
}
