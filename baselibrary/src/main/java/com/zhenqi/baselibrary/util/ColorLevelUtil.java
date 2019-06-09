package com.zhenqi.baselibrary.util;


import com.zhenqi.baselibrary.R;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class ColorLevelUtil {
    /**
     * PM10color获取
     */
    public static int getPm10Color(float value) {
        if (value <= 0) {
            return R.color.valueIllegal;
        } else if (value <= 50) {
            return R.color.excellent;
        } else if (value <= 150) {
            return R.color.good;
        } else if (value <= 250) {
            return R.color.mild;
        } else if (value <= 350) {
            return R.color.moderate;
        } else if (value <= 420) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }
    }

    /**
     * PM25color获取
     */
    public static int getPm25Color(float value) {
        if (value <= 0) {
            return R.color.valueIllegal;
        } else if (value <= 35) {
            return R.color.excellent;
        } else if (value <= 75) {
            return R.color.good;
        } else if (value <= 115) {
            return R.color.mild;
        } else if (value <= 150) {
            return R.color.moderate;
        } else if (value <= 250) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }
    }

    /**
     * 根据so2得到对应color
     */
    public static int getSO2color(float so2) {
        if (so2 <= 0) {
            return R.color.valueIllegal;
        } else if (so2 <= 50) {
            return R.color.excellent;
        } else if (so2 <= 150) {
            return R.color.good;
        } else if (so2 <= 475) {
            return R.color.mild;
        } else if (so2 <= 800) {
            return R.color.moderate;
        } else {
            return R.color.heavy;
        }

    }

    public static int getAQIcolor(float aqi) {
        if (aqi <= 0) {
            return R.color.valueIllegal;
        } else if (aqi <= 50) {
            return R.color.excellent;
        } else if (aqi <= 100) {
            return R.color.good;
        } else if (aqi <= 150) {
            return R.color.mild;
        } else if (aqi <= 200) {
            return R.color.moderate;
        } else if (aqi <= 300) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }
    }

    // 根据no2得到对应
    public static int getNO2color(float no2) {
        if (no2 <= 0) {
            return R.color.valueIllegal;
        } else if (no2 <= 40) {
            return R.color.excellent;
        } else if (no2 <= 80) {
            return R.color.good;
        } else if (no2 <= 180) {
            return R.color.mild;
        } else if (no2 <= 280) {
            return R.color.moderate;
        } else if (no2 <= 565) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }

    }

    // 根据o3得到对应污染等级
    public static int getO3color(float o3) {
        if (o3 <= 0) {
            return R.color.valueIllegal;
        } else if (o3 <= 160) {
            return R.color.excellent;
        } else if (o3 <= 200) {
            return R.color.good;
        } else if (o3 <= 300) {
            return R.color.mild;
        } else if (o3 <= 400) {
            return R.color.moderate;
        } else if (o3 <= 800) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }

    }

    // 根据co得到对应污染等级
    public static int getCOcolor(double co) {
        if (co <= 0) {
            return R.color.valueIllegal;
        } else if (co <= 2) {
            return R.color.excellent;
        } else if (co <= 4) {
            return R.color.good;
        } else if (co <= 14) {
            return R.color.mild;
        } else if (co <= 24) {
            return R.color.moderate;
        } else if (co <= 36) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }

    }

    // 根据综合指数得到对应
    public static int getZongZhicolor(float zongzhi) {
        if (zongzhi <= 0) {
            return R.color.valueIllegal;
        } else if (zongzhi <= 5) {
            return R.color.excellent;
        } else if (zongzhi <= 6) {
            return R.color.good;
        } else if (zongzhi <= 8) {
            return R.color.mild;
        } else if (zongzhi <= 9) {
            return R.color.moderate;
        } else if (zongzhi <= 10) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }

    }

    // 根据综合指数得到对应
    public static int getTempicolor(float temp) {
        if (temp <= 0) {
            return R.color.valueIllegal;
        } else if (temp <= 15) {
            return R.color.excellent;
        } else if (temp <= 20) {
            return R.color.good;
        } else if (temp <= 25) {
            return R.color.mild;
        } else if (temp <= 30) {
            return R.color.moderate;
        } else if (temp <= 35) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }

    }

    // 根据s指数得到对应
    public static int getHumicolor(float humi) {
        if (humi <= 0) {
            return R.color.valueIllegal;
        } else if (humi <= 20) {
            return R.color.excellent;
        } else if (humi <= 40) {
            return R.color.good;
        } else if (humi <= 60) {
            return R.color.mild;
        } else if (humi <= 80) {
            return R.color.moderate;
        } else if (humi <= 100) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }

    }

    // 根据s指数得到对应
    public static int getWindcolor(float speed) {
        if (speed < 1) {
            return R.color.valueIllegal;
        } else if (speed <= 11) {
            return R.color.excellent;
        } else if (speed <= 28) {
            return R.color.good;
        } else if (speed <= 49) {
            return R.color.mild;
        } else if (speed <= 74) {
            return R.color.moderate;
        } else if (speed <= 102) {
            return R.color.heavy;
        } else {
            return R.color.severe;
        }

    }

    /*
     * 根据风速得到风力等级
     */
    public static int getWindLevel(float speed) {
        if (speed < 1) {
            return 0;
        } else if (speed <= 5) {
            return 1;
        } else if (speed <= 11) {
            return 2;
        } else if (speed <= 19) {
            return 3;
        } else if (speed <= 28) {
            return 4;
        } else if (speed <= 38) {
            return 5;
        } else if (speed <= 49) {
            return 6;
        } else if (speed <= 61) {
            return 7;
        } else if (speed <= 74) {
            return 8;
        } else if (speed <= 88) {
            return 9;
        } else if (speed <= 102) {
            return 10;
        } else if (speed <= 117) {
            return 11;
        } else {//>117
            return 12;
        }
    }

    /**
     * PM10color获取
     */
    public static int getPm10level(float value) {
        if (value <= 0) {
            return 0;
        } else if (value <= 50) {
            return 1;
        } else if (value <= 150) {
            return 2;
        } else if (value <= 250) {
            return 3;
        } else if (value <= 350) {
            return 4;
        } else if (value <= 420) {
            return 5;
        } else {
            return 6;
        }
    }

    /**
     * PM25color获取
     */
    public static int getPm25level(float value) {
        if (value <= 0) {
            return 0;
        } else if (value <= 35) {
            return 1;
        } else if (value <= 75) {
            return 2;
        } else if (value <= 115) {
            return 3;
        } else if (value <= 150) {
            return 4;
        } else if (value <= 250) {
            return 5;
        } else {
            return 6;
        }
    }

    /**
     * 根据so2得到对应color
     */
    public static int getSO2level(float so2) {
        if (so2 <= 0) {
            return 0;
        } else if (so2 <= 150) {
            return 1;
        } else if (so2 <= 500) {
            return 2;
        } else if (so2 <= 650) {
            return 3;
        } else if (so2 <= 800) {
            return 4;
        } else {
            return 5;
        }

    }

    public static int getAQIlevel(float aqi) {

        if (aqi <= 0) {
            return 0;

        } else if (aqi <= 50) {
            return 1;
        } else if (aqi <= 100) {
            return 2;
        } else if (aqi <= 150) {
            return 3;
        } else if (aqi <= 200) {
            return 4;
        } else if (aqi <= 300) {
            return 5;
        } else {
            return 6;
        }

    }

    public static String getAQIDescerption(float aqi) {

        if (aqi <= 0) {
            return "无";

        } else if (aqi <= 50) {
            return "优";
        } else if (aqi <= 100) {
            return "良";
        } else if (aqi <= 150) {
            return "轻度";
        } else if (aqi <= 200) {
            return "中度";
        } else if (aqi <= 300) {
            return "重度";
        } else {
            return "严重";
        }

    }

    // 根据no2得到对应
    public static int getNO2level(float no2) {
        if (no2 <= 0) {
            return 0;
        } else if (no2 <= 100) {
            return 1;
        } else if (no2 <= 200) {
            return 2;
        } else if (no2 <= 700) {
            return 3;
        } else if (no2 <= 1200) {
            return 4;
        } else if (no2 <= 2340) {
            return 5;
        } else {
            return 6;
        }

    }

    // 根据o3得到对应污染等级
    public static int getO3level(float o3) {
        if (o3 <= 0) {
            return 0;
        } else if (o3 <= 160) {
            return 1;
        } else if (o3 <= 200) {
            return 2;
        } else if (o3 <= 300) {
            return 3;
        } else if (o3 <= 400) {
            return 4;
        } else if (o3 <= 800) {
            return 5;
        } else {
            return 6;
        }

    }
    // 根据o3得到对应污染等级
    public static int getO3Level8Hour(float o3) {
        int level = 0;
        if (o3 <= 100) {
            level = 1;
        } else if (o3 <= 160) {
            level = 2;
        } else if (o3 <= 215) {
            level = 3;
        } else if (o3 <= 265) {
            level = 4;
        } else if (o3 <= 800) {
            level = 5;
        } else {
            level = 6;
        }
        return level;
    }


    // 根据co得到对应污染等级
    public static int getCOlevel(double co) {
        if (co <= 0) {
            return 0;
        } else if (co <= 5) {
            return 1;
        } else if (co <= 10) {
            return 2;
        } else if (co <= 35) {
            return 3;
        } else if (co <= 60) {
            return 4;
        } else if (co <= 90) {
            return 5;
        } else {
            return 6;
        }

    }

    // 根据综合指数得到对应
    public static int getZongZhilevel(float zongzhi) {
        if (zongzhi <= 0) {
            return 0;
        } else if (zongzhi <= 5) {
            return 1;
        } else if (zongzhi <= 6) {
            return 2;
        } else if (zongzhi <= 8) {
            return 3;
        } else if (zongzhi <= 9) {
            return 4;
        } else if (zongzhi <= 10) {
            return 5;
        } else {
            return 6;
        }

    }

    // 根据综合指数得到对应
    public static int getTempilevel(float temp) {
        if (temp <= 0) {
            return 0;
        } else if (temp <= 15) {
            return 1;
        } else if (temp <= 20) {
            return 2;
        } else if (temp <= 25) {
            return 3;
        } else if (temp <= 30) {
            return 4;
        } else if (temp <= 35) {
            return 5;
        } else {
            return 6;
        }

    }

    // 根据s指数得到对应
    public static int getHumilevel(float humi) {
        if (humi <= 0) {
            return 0;
        } else if (humi <= 20) {
            return 1;
        } else if (humi <= 40) {
            return 2;
        } else if (humi <= 60) {
            return 3;
        } else if (humi <= 80) {
            return 4;
        } else if (humi <= 100) {
            return 5;
        } else {
            return 6;
        }

    }

    // 根据s指数得到对应
    public static int getWindsixlevel(float speed) {
        if (speed < 1) {
            return 0;
        } else if (speed <= 11) {
            return 1;
        } else if (speed <= 28) {
            return 2;
        } else if (speed <= 49) {
            return 3;
        } else if (speed <= 74) {
            return 4;
        } else if (speed <= 102) {
            return 5;
        } else {
            return 6;
        }

    }

    /**
     * 计算综合指数
     */
    public static float getZongZhiIndex(float so2, float no2, float co, float o3, float pm10, float pm2_5) {
        return so2 / 60 + no2 / 40 + co / 4 + o3 / 160 + pm10 / 70 + pm2_5 / 35;
    }

    /**
     * 根据角度
     * 获取风向
     */
    public static String getWindDirectionStr(float wd) {
        String wdstr = "无";
        if (wd == 0) {
            wdstr = "无";
        } else if (wd <= 22.5 || wd >= 337.5) {
            wdstr = "北风";
        } else if (wd > 22.5 && wd <= 67.5) {
            wdstr = "东北风";
        } else if (wd > 67.5 && wd <= 112.5) {
            wdstr = "东风";
        } else if (wd > 112.5 && wd <= 157.5) {
            wdstr = "东南风";
        } else if (wd > 157.5 && wd <= 202.5) {
            wdstr = "南风";
        } else if (wd > 202.5 && wd <= 247.5) {
            wdstr = "西南风";
        } else if (wd > 247.5 && wd <= 292.5) {
            wdstr = "西风";
        } else if (wd > 292.5 && wd <= 337.5) {
            wdstr = "西北风";
        }
        return wdstr;
    }

    public static int getWaterColor(String lev) {
        switch (lev) {
            case "I":
                return 0xFFCAFCFF;
            case "II":
                return 0xFF36C3F9;
            case "III":
                return 0xFF17FA13;
            case "IV":
                return 0xFFF7FC19;
            case "劣V":
                return 0xFFFF9000;
        }
        return 0;
    }


    public static int getColorLevel(int level) {
        switch (level) {
            case 1:
                return R.drawable.corners_excellent;
            case 2:
                return R.drawable.corners_good;
            case 3:
                return R.drawable.corners_mild;
            case 4:
                return R.drawable.corners_moderate;
            case 5:
                return R.drawable.corners_heavy;
            case 6:
            case 7:
                return R.drawable.corners_severe;
            default:
                return R.drawable.corners_vauleillegal;
        }
    }


}
