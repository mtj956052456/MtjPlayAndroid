package com.zhenqi.pm2_5.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.zhenqi.baselibrary.util.Lg;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/3/18
 * 描述:  友盟的 分享 微信登录 封装
 */
public class UmengUtil {

    /**
     * @param activity
     */
    public static void callShare(final Activity activity, SHARE_MEDIA share_media, Bitmap bitmap, final ShareCallBack shareCallBack) {
        final UMImage umImage = getUMImage(activity, bitmap);
        new ShareAction(activity)
                //.withExtra(image)
                .withMedia(umImage)
                .setPlatform(share_media)//传入平台
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        shareCallBack.onSuccess();

                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        shareCallBack.onError(throwable.getMessage());
                        Toast.makeText(activity, "分享失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        shareCallBack.onCancel();
                        Toast.makeText(activity, "取消了分享", Toast.LENGTH_LONG).show();
                    }
                }).share();
    }

    /**
     * 友盟分享Web
     *
     * @param umImage //图片
     * @param title   //标题
     * @param url     //分享的链接必须是https开头
     * @param des     //描述
     *                eg:  UMWeb umWeb = getShareUMWeb(umImage,"真气网",web,"一家专门做数据分析的网站");
     * @return
     */
    public static UMWeb getShareUMWeb(UMImage umImage, String title, String url, String des) {
        UMWeb web = new UMWeb(url); // eg: https://www.zq12369.com/
        web.setTitle(title);        //标题
        web.setThumb(umImage);      //缩略图
        web.setDescription(des);    //描述
        return web;
    }

    /**
     * 友盟分享图片
     *
     * @param activity
     * @param bitmap
     * @return
     */
    public static UMImage getUMImage(Activity activity, Bitmap bitmap) {
        UMImage umImage = new UMImage(activity, bitmap);
        UMImage umImageTh = new UMImage(activity, bitmap);
        umImage.setThumb(umImageTh);
        return umImage;
    }


    /**
     * 微信登录
     *
     * @param activity
     * @param wxCallBack
     */
    public static void WeiXinLogin(Activity activity, final WXLoginCallBack wxCallBack) {
        UMShareAPI umShareAPI = UMShareAPI.get(activity);
        umShareAPI.getPlatformInfo(activity, SHARE_MEDIA.WEIXIN, new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param platform 平台名称
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {
                Lg.e("platform: "+platform.getName());
            }

            /**
             * @desc 授权成功的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param data 用户资料返回
             */
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                //Lg.e("OpenId: " + data.get("openid") + "  screen_name : " + data.get("screen_name")
                //        + " profile_image_url : " + data.get("profile_image_url"));
                //SPUtil.save(SPUtil.OpenId, data.get("openid"));
                //SPUtil.save(SPUtil.ScreenName, data.get("screen_name"));
                //SPUtil.save(SPUtil.ImageUrl, data.get("profile_image_url"));
                //Lg.i("登录成功");
                wxCallBack.onComplete(data);
            }

            /**
             * @desc 授权失败的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                //Lg.e(t.getMessage());
                wxCallBack.onError(t.getMessage());
            }

            /**
             * @desc 授权取消的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             */
            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                //Lg.i("取消了");
                wxCallBack.onCancel();
            }
        });
    }
}
