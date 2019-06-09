package com.zhenqi.baselibrary.net;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/3/20
 * 描述:  列子
 */
public class Example {

    /**
     * palm3请求例子
     * @param context
     */
    public static void EGPalm3Post(final Context context) {
        Map<String, String> map = new HashMap<>();
        map.put("city", "郑州");
        OkHttpManager.getInstance().Palm3Post("SUPERVISEAPI_GETQUESTIONANDTYPE", map, API.Palm3_Superviseapi, new OkHttpCallBack.okCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                Log.e("MTJ", "onSuccess: " + result);
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                Log.e("MTJ", "onError: " + t.getMessage());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
