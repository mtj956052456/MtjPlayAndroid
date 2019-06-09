package com.zhenqi.pm2_5;

import android.util.Log;

import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.socialize.PlatformConfig;
import com.zhenqi.baselibrary.base.BaseApp;

import cn.jpush.android.api.JPushInterface;

/**
 * @author mtj
 * @time 2019/6/7 2019 06
 * @des
 */
public class ShareLoginPushApp extends BaseApp {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);

        UMConfigure.init(this, "586480c8f29d980d13000ea1", "zqw", UMConfigure.DEVICE_TYPE_PHONE, "237f137e6522385857a47f9367a31a5e");
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMConfigure.setLogEnabled(true);
        UMConfigure.setEncryptEnabled(true);
        //InAppMessageManager.getInstance(getApplicationContext()).setInAppMsgDebugMode(true); 打开内应用推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);

        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.i("MyToken", "device token: " + deviceToken);
                //注册成功会返回device token
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("MyToken", "s: " + s + "  s1: " + s1);
            }
        });
        PlatformConfig.setWeixin("wxcd5646806cba72b7", "dc6ccac0d1db4db112db41c87e3d0e34");
        //        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        //        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setQQZone("1107227930", "LBSGvmRpd9BilnHI");//QQAPPID和AppSecret
        PlatformConfig.setSinaWeibo("3146847499", "f6c99e8dc41442cf6ef1a5c404d29365", "https://www.zq12369.com/");
    }
}
