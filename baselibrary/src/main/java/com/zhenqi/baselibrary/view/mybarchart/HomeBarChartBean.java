package com.zhenqi.baselibrary.view.mybarchart;

/**
 * @author mtj
 * @time 2019/5/8 2019 05
 * @des
 */
public class HomeBarChartBean {
    private float num;
    private String city;

    public HomeBarChartBean() {

    }

    public HomeBarChartBean(float num, String city) {
        this.num = num;
        this.city = city;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public String getCity() {
        return city == null ? "" : city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
