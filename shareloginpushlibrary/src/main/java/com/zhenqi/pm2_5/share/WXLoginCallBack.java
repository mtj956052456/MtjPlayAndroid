package com.zhenqi.pm2_5.share;

import java.util.Map;

/**
 * 微信登录接口
 */
public interface WXLoginCallBack {

    void onComplete(Map<String, String> data);  //登录成功

    void onError(String errMsg);                 //登录失败

    void onCancel();                            //取消登录
}