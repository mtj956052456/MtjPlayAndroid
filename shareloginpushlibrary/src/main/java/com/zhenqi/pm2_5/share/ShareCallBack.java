package com.zhenqi.pm2_5.share;

/**
 * 友盟分享接口
 */
public interface ShareCallBack {

    void onSuccess();           //分享成功

    void onError(String errMsg);//分享失败

    void onCancel();            //取消了分享
}