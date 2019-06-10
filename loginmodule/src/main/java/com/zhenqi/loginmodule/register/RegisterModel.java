package com.zhenqi.loginmodule.register;

import com.blankj.utilcode.util.ToastUtils;
import com.zhenqi.baselibrary.api.BaseApiService;
import com.zhenqi.baselibrary.mvp.BaseModel;
import com.zhenqi.baselibrary.net.RxClient;
import com.zhenqi.baselibrary.net.callback.RxCallBack;
import com.zhenqi.baselibrary.zq_net.API;
import com.zhenqi.baselibrary.zq_net.OkHttpCallBack;
import com.zhenqi.baselibrary.zq_net.OkHttpManager;
import com.zhenqi.loginmodule.bean.LoginRegisterBean;

import java.util.WeakHashMap;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class RegisterModel extends BaseModel<RegisterPresent> {

    public void requestRegister(String username, String userpassword, String repassword) {
        RxClient.builder()
                .addParams("username", username)
                .addParams("password", userpassword)
                .addParams("repassword", repassword)
                .build()
                .rxPost(BaseApiService.REGISTER, new RxCallBack<LoginRegisterBean>() {
                    @Override
                    public void rxOnNext(LoginRegisterBean response) {
                        if (response.getData() != null && response.getErrorCode() == 0) {
                            mBasePresenter.onRegisterSucceed(response);
                        } else {
                            mBasePresenter.onRegisterFailure(response.getErrorMsg());
                        }
                    }

                    @Override
                    public void rxOnError(Throwable e) {
                        mBasePresenter.onRegisterFailure(e.toString());
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
