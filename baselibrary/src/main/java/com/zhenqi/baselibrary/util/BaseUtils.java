package com.zhenqi.baselibrary.util;

import android.content.Context;
import android.os.Looper;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.zhenqi.baselibrary.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.WeakHashMap;

public class BaseUtils {

    public static final class GsonHolder{
        private static final Gson GSON=new Gson();
    }

    public static Gson getGsonInstance(){
        return GsonHolder.GSON;
    }

    /**
     * 请求参数转Json
     *
     * @return String
     */
    public static String toJson(WeakHashMap<String, Object> params) {
        JSONObject json = new JSONObject();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            try {
                json.put(entry.getKey(), entry.getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return json.toString();
    }

    /**
     * 当前线程是否是主线程
     * @return
     */
    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }
}
