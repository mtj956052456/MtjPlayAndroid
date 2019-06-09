package com.zhenqi.pm2_5.share;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhenqi.baselibrary.util.Lg;
import com.zhenqi.baselibrary.util.SPWXUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;

import java.util.Map;


/**
 * 创建者: 孟腾蛟
 * 时间: 2019/3/20
 * 描述:
 */
public class ShareLoginUtil {

    /**
     * 微信登录
     *
     * @param activity
     */
    public static void WXLogin(Activity activity) {
        WXLogin(activity, null);
    }

    /**
     * 微信登录并加载图片
     *
     * @param activity
     */
    public static void WXLogin(final Activity activity, final ImageView iv) {
        UmengUtil.WeiXinLogin(activity, new WXLoginCallBack() {
            @Override
            public void onComplete(final Map<String, String> data) {
                Lg.e("OpenId: " + data.get("openid") + "  screen_name : " + data.get("screen_name")
                        + " profile_image_url : " + data.get("profile_image_url"));
                SPWXUtil.save(SPWXUtil.WX_OPENID, data.get("openid"));
                SPWXUtil.save(SPWXUtil.WX_USER_NAME, data.get("screen_name"));
                SPWXUtil.save(SPWXUtil.WX_USER_HEAD_URL, data.get("profile_image_url"));
                //sendToService(data);
                if (iv != null) {  //iv
                    Glide.with(activity).load(data.get("profile_image_url")).into(iv);
                }
            }

            @Override
            public void onError(String errMsg) {
                Toast.makeText(activity, "登录失败: " + errMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "取消登录", Toast.LENGTH_LONG).show();
            }
        });
    }

//    /**
//     * @param data
//     */
//    private static void sendToService(Map<String, String> data) {
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("openid", data.get("openid"));
//        map.put("unionid", data.get("unionid"));
//        map.put("nickname", data.get("screen_name"));
//        map.put("headimgurl", data.get("profile_image_url"));
//        map.put("city", data.get("city"));
//        map.put("clienttype", "Android");
//        map.put("sex", data.get("gender"));
//        OkHttpManager.getInstance().postPalmapi("USERAPI_WXAUTHLOGIN", map, API.Palm2_palmapi, new OkHttpCallBack.okCallBack<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    JSONObject object = new JSONObject(result);
//                    String usergid = object.getString("usergid");
//                    SPWXUtil.save(SPWXUtil.WX_USER_GID, usergid);
//                    Toast.makeText(BaseApp.getApp(), "登录成功", Toast.LENGTH_LONG).show();
//                    getUserInfo(usergid);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//            }
//        });
//
//    }
//
//    /**
//     * 登录成功后查看并存储历史提交信息
//     *
//     * @param usergid
//     */
//    private static void getUserInfo(String usergid) {
//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("usergid", usergid);
//        OkHttpManager.getInstance().postPalmapi("USERAPI_QUERYUSERINFO", map, API.Palm2_palmapi, new OkHttpCallBack.okCallBack<String>() {
//            @Override
//            public void onSuccess(String result) {
//                try {
//                    JSONObject jsonObject = new JSONObject(result);
//                    String username = jsonObject.getString("username");
//                    String realname = jsonObject.getString("realname");
//                    String phone = jsonObject.getString("phone");
//                    String email = jsonObject.getString("email");
//                    String company = jsonObject.getString("company");
//                    String position = jsonObject.getString("position");
//                    SPWXUtil.save(SPWXUtil.PALM_USER_NAME, TextUtil.nullToStr(username));
//                    SPWXUtil.save(SPWXUtil.PALM_REAL_NAME, TextUtil.nullToStr(realname));
//                    SPWXUtil.save(SPWXUtil.PALM_PHONE, TextUtil.nullToStr(phone));
//                    SPWXUtil.save(SPWXUtil.PALM_EMAIL, TextUtil.nullToStr(email));
//                    SPWXUtil.save(SPWXUtil.PALM_COMPANY, TextUtil.nullToStr(company));
//                    SPWXUtil.save(SPWXUtil.PALM_POSITION, TextUtil.nullToStr(position));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                super.onError(e);
//            }
//        });
//    }


    /**
     * 微信分享图片测试
     * SHARE_MEDIA.WEIXIN 可更改
     *
     * @param activity
     */
    public static void ShareImage(final Activity activity, SHARE_MEDIA share_media, Bitmap bitmap) {
        UmengUtil.callShare(activity, share_media, bitmap, new ShareCallBack() {
            @Override
            public void onSuccess() {
                Toast.makeText(activity, "分享成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errMsg) {
                Toast.makeText(activity, "分享失败: " + errMsg, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(activity, "分享登录", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 微信分享图片测试
     * SHARE_MEDIA.WEIXIN 可更改
     *
     * @param activity
     */
    public static void ShareWeb(final Activity activity, SHARE_MEDIA share_media, UMWeb web) {
        new ShareAction(activity)
                .withMedia(web)
                .setPlatform(share_media)//传入平台
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Toast.makeText(activity, "分享成功", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Toast.makeText(activity, "分享失败" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Toast.makeText(activity, "取消了分享", Toast.LENGTH_LONG).show();
                    }
                }).share();
    }

}
