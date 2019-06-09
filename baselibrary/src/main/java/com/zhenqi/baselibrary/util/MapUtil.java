package com.zhenqi.baselibrary.util;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/3/19
 * 描述:
 */
public class MapUtil {

    /**
     * 内容框居中向下偏移经度显示
     *
     * @return
     */
    public static double getOffSet(float zoom ) {
        if (zoom >= 19) {
            return 0.0004;
        } else if (zoom >= 18) {
            return 0.0007;
        } else if (zoom >= 17) {
            return 0.002;
        } else if (zoom >= 16) {
            return 0.003;
        } else if (zoom >= 15.5) {
            return 0.004;
        } else if (zoom >= 15) {
            return 0.005;
        } else if (zoom >= 14) {
            return 0.01;
        } else if (zoom >= 13) {
            return 0.02;
        } else if (zoom >= 12) {
            return 0.03;
        } else if (zoom >= 11) {
            return 0.07;
        } else if (zoom >= 10) {
            return 0.2;
        } else if (zoom >= 9.5) {
            return 0.25;
        } else if (zoom >= 9) {
            return 0.3;
        } else if (zoom >= 8.5) {
            return 0.4;
        }else if (zoom >= 8) {
            return 0.6;
        } else if (zoom >= 7) {
            return 1.0;
        } else if (zoom >= 6.5) {
            return 1.6;
        } else if (zoom >= 6) {
            return 2;
        } else if (zoom >= 5) {
            return 4.5;
        } else if (zoom >= 4.5) {
            return 6;
        } else if (zoom >= 4) {
            return 8;
        } else {
            return 20;
        }
    }

}
