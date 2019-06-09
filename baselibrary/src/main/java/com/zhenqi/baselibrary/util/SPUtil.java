package com.zhenqi.baselibrary.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.zhenqi.baselibrary.base.BaseApp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 创建人:孟腾蛟
 * 时间: 2017/03/11
 * 描述:
 */
public class SPUtil {
    private static final String TAG = "SPUtil";
    public static final String WARNING_FILTERS = "WARNING_FILTERS";
    public static final String WARNING_VALUE = "WARNING_VALUE";
    public static final String WARNING_FONTSIZE = "WARNING_FONTSIZE";

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";

    public static final String PROVINCE = "PROVINCE";            //省份
    public static final String CITY = "CITY";                    //城市
    public static final String REGION = "REGION";                //区域
    public static final String POINT = "POINT";                  //
    public static final String CONCERNEDCITY = "CONCERNEDCITY";  //关心城市
    public static final String NICKNAME = "NICKNAME";            //
    public static final String SCORE = "SCORE";                  //分数
    public static final String ROLENAME = "ROLENAME";            //
    public static final String APPROLEID = "APPROLEID";          //
    public static final String MIDS = "MIDS";                    //
    public static final String GID = "GID";                      //
    public static final String RANKFLAG_169 = "RANKFLAG_169";    //169城市区分
    public static final String PROVINCECITYFLAG = "PROVINCECITYFLAG";//省市区分

    public static final String REFUSEVERSION = "REFUSEVERSION"; //忽略此版本
    public static final String VIDEOKEY = "VIDEOKEY'";          //音频历史文件

    public static final String ERRORINFO="error_info";

    public static void save(String key, String value) {
        SharedPreferences sp = BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value).apply();
    }

    public static String get(String key) {
        SharedPreferences sp = BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void remove(String key) {
        SharedPreferences sp = BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key).apply();
    }

    public static void saveInt(String key, int value) {
        SharedPreferences sp = BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(key, value).apply();
    }

    public static int getInt(String key) {
        SharedPreferences sp = BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sp.getInt(key, 0);
    }

    public static void saveBoolean(String key, boolean value) {
        SharedPreferences sp = BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        SharedPreferences sp = BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
     * 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
     *
     * @param object 待加密的转换为String的对象
     * @return String   加密后的String
     */
    private static String Object2String(Object object) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(object);
            String string = new String(Base64.encode(byteArrayOutputStream.toByteArray(), Base64.DEFAULT));
            objectOutputStream.close();
            return string;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 使用Base64解密String，返回Object对象
     *
     * @param objectString 待解密的String
     * @return object      解密后的object
     */
    private static Object String2Object(String objectString) {
        byte[] mobileBytes = Base64.decode(objectString.getBytes(), Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(mobileBytes);
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Object object = objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 使用SharedPreference保存对象
     *
     * @param fileKey    储存文件的key
     * @param key        储存对象的key
     * @param saveObject 储存的对象
     */
    public static void save(String fileKey, String key, Object saveObject) {
        SharedPreferences sharedPreferences = BaseApp.getApp().getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String string = Object2String(saveObject);
        editor.putString(key, string);
        editor.apply();
    }

    /**
     * 获取SharedPreference保存的对象
     *
     * @param fileKey 储存文件的key
     * @param key     储存对象的key
     * @return object 返回根据key得到的对象
     */
    public static Object get(String fileKey, String key) {
        SharedPreferences sharedPreferences = BaseApp.getApp().getApplicationContext().getSharedPreferences(fileKey, Activity.MODE_PRIVATE);
        String string = sharedPreferences.getString(key, null);
        if (string != null) {
            Object object = String2Object(string);
            return object;
        } else {
            return null;
        }
    }

    /**
     * 清除缓存
     */
    public static void clearSp() {
        BaseApp.getApp().getSharedPreferences(TAG, Context.MODE_PRIVATE).edit().clear().apply();
    }

}
