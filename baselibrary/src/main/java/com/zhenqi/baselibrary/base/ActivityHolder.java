package com.zhenqi.baselibrary.base;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;

public class ActivityHolder {
    private static ArrayList<Activity> activityArray = new ArrayList<Activity>();

    public static void addActivity(Context context) {
        activityArray.add((Activity) context);
    }

    public static void removeActivity(Context context) {
        activityArray.remove((Activity) context);
    }

    public static void removeAllActivity() {
        for (Activity activity : activityArray) {
            activity.finish();
        }
    }
}
