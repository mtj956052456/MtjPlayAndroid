package com.zhenqi.baselibrary.base;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/3/21
 * 描述:
 */
public class BaseApp extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Utils.init(this);
    }

    public static Context getApp() {
        return context;
    }
}
