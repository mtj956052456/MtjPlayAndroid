package com.zhenqi.baselibrary.view.calender;

/**
 * Created by C4BOM on 2018/5/3.
 * GoodLuck No Bug
 */
public class CalenderBean {
    private int id;
    private int day;
    private int year;
    private int month;
    private String memo;
    private int cord;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getCord() {
        return cord;
    }

    public void setCord(int cord) {
        this.cord = cord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
