package com.zhenqi.loginmodule;

import com.zhenqi.baselibrary.mvp.BaseModel;
import com.zhenqi.baselibrary.net.API;
import com.zhenqi.baselibrary.net.OkHttpCallBack;
import com.zhenqi.baselibrary.net.OkHttpManager;

import org.json.JSONObject;

import java.util.WeakHashMap;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class LoginModel extends BaseModel<LoginPresent> {

    public void login(WeakHashMap<String, String> map) {
        OkHttpManager.getInstance().Palm2Post("USERAPI_LOGIN", map, API.Palm2, new OkHttpCallBack.okCallBack<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject userobj = jsonObject.getJSONObject("userobj");
                    //Constant.City = userobj.getString("city");
                    //SPUtil.save(SPUtil.CITY, userobj.getString("city"));
                    //SPUtil.save(SPUtil.REGION, userobj.getString("region"));
                    //SPUtil.save(SPUtil.POINT, userobj.getString("point"));
                    //SPUtil.save(SPUtil.CONCERNEDCITY, userobj.getString("concernedcity"));
                    //SPUtil.save(SPUtil.NICKNAME, userobj.getString("nickname"));
                    //SPUtil.save(SPUtil.USERNAME, userobj.getString("username"));
                    //SPUtil.save(SPUtil.SCORE, userobj.getString("score"));
                    //SPUtil.save(SPUtil.ROLENAME, userobj.getString("rolename"));
                    //SPUtil.save(SPUtil.PROVINCE, userobj.getString("province"));
                    //SPUtil.save(SPUtil.RANKFLAG_169, userobj.getString("rankflag_169"));
                    //SPUtil.save(SPUtil.PROVINCECITYFLAG, userobj.getString("provincecityflag"));
                    //SPUtil.save(SPUtil.GID, userobj.getString("gid"));
                    //String approleid = userobj.getString("approleid");
                    //SPUtil.save(SPUtil.APPROLEID, approleid);
                    //if (!"0".equals(approleid)) {
                    //    JSONArray array = userobj.getJSONArray("mids");
                    //    String mids = "";
                    //    for (int i = 0; i < array.length(); i++) {
                    //        mids = mids + array.getString(i) + ",";
                    //    }
                    //    mids.substring(0, mids.length() - 1);
                    //    SPUtil.save(SPUtil.MIDS, mids);
                    //} else {
                    //    SPUtil.save(SPUtil.MIDS, "");
                    //}
                    mBasePresenter.loginSuccess(userobj.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    mBasePresenter.loginError(e.getMessage());
                }
            }


            @Override
            public void onError(Throwable t) {
                super.onError(t);
                mBasePresenter.loginError(t.getMessage());
            }
        });
    }

}
