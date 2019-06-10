package com.zhenqi.loginmodule.login;

import com.zhenqi.baselibrary.api.BaseApiService;
import com.zhenqi.baselibrary.mvp.BaseModel;
import com.zhenqi.baselibrary.net.RxClient;
import com.zhenqi.baselibrary.net.callback.RxCallBack;
import com.zhenqi.baselibrary.zq_net.API;
import com.zhenqi.baselibrary.zq_net.OkHttpCallBack;
import com.zhenqi.baselibrary.zq_net.OkHttpManager;
import com.zhenqi.loginmodule.bean.LoginRegisterBean;

import org.json.JSONObject;

import java.util.WeakHashMap;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class LoginModel extends BaseModel<LoginPresent> {

    public void login(String username, String password) {
        RxClient.builder()
                .addCookies(true)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .rxPost(BaseApiService.LOGIN, new RxCallBack<LoginRegisterBean>() {
                    @Override
                    public void rxOnNext(LoginRegisterBean response) {
                        if (response.getData() != null && response.getErrorCode() == 0) {
                            mBasePresenter.loginSuccess(response);
                        } else {
                            mBasePresenter.loginError(response.getErrorMsg() + "    " + response.getErrorCode());
                        }
                    }

                    @Override
                    public void rxOnError(Throwable e) {
                        mBasePresenter.loginError(e.toString());
                    }
                });
    }

}
