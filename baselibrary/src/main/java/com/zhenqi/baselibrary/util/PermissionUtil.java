package com.zhenqi.baselibrary.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;


/**
 * 孟腾蛟
 * 20180408
 * des:
 */

public class PermissionUtil {

    //     String[] strs = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
    //     Manifest.permission.ACCESS_FINE_LOCATION,
    //     Manifest.permission.READ_EXTERNAL_STORAGE,
    //     Manifest.permission.WRITE_EXTERNAL_STORAGE,
    //     Manifest.permission.READ_PHONE_STATE};

    /**
     * 检查多个权限
     *
     * @param activity
     * @param permissions
     * @return
     */
    public static boolean checkPermission(Activity activity, @io.reactivex.annotations.NonNull String[] permissions, int requesCode) {
        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(activity, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, permissions, requesCode);
                return false;
            }
        }
        return true;
    }

    /**
     * 检查单个权限
     * @param activity
     * @param permission
     * @return
     */
    public static boolean checkPermission(Activity activity, String permission) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, 1);
            return false;
        }
        return true;
    }


}
