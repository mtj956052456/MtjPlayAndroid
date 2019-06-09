package com.zhenqi.baselibrary.util;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/14
 * 描述:
 */
public class ZhenqiStandardUtil {

    /**
     * 获取年需控制值
     *
     * @param pm2_5
     * @param pm2_5_target
     * @return
     */
    public static float getYearControlValue(float pm2_5, float pm2_5_target) {
        float yearDay = DateUtil.getYearDay();
        float pastDay = DateUtil.getPastDay()-1;  //已过天数不包括今天
        return (pm2_5_target * yearDay - pm2_5 * pastDay) / (yearDay - pastDay);
    }

    /**
     * 获取小时需控制值
     *
     * @param pm2_5
     * @param pm2_5_target
     * @return
     */
    public static int getHourControlValue(int pm2_5, int pm2_5_target) {
        int thisHour = Integer.parseInt(DateUtil.getNow24HourDate());
        return (pm2_5_target * 24 - pm2_5 * thisHour) / (24 - thisHour);
    }

    public static float getHourControlValue(float pm2_5, float pm2_5_target) {
        int thisHour = Integer.parseInt(DateUtil.getNow24HourDate());
        return (pm2_5_target * 24 - pm2_5 * thisHour) / (24 - thisHour);
    }
}
