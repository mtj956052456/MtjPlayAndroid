package com.zhenqi.baselibrary.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

public class ServiceUtil {

    //本方法判断自己Service是否已经运行
    public static boolean serviceIsRuning(Context context, String serviceName) {
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(100);
        for (int i = 0; i < runningService.size(); i++) {
            //String className = runningService.get(i).service.getClassName();
            //Log.e("MTJ", "serviceIsRuning: service"+className);
            if (runningService.get(i).service.getClassName().equals(serviceName)) {
                return true;
            }
        }
        return false;
    }
}
