package com.zhenqi.baselibrary.util;

/**
 * @author 孟腾蛟
 * @time 2018/3/25 2018 03
 * @des
 */

public class TextUtil {

    /**
     * 将 null 转为 ""
     *
     * @param text
     * @return
     */
    public static String nullToStr(String text) {
        if (text == null) {
            return "";
        } else if ("null".equals(text)) {
            return "";
        }
        return text;
    }
}
