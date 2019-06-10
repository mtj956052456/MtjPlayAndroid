package com.zhenqi.mtjdemo.main;

import com.zhenqi.baselibrary.api.BaseApiService;
import com.zhenqi.baselibrary.mvp.BaseModel;
import com.zhenqi.baselibrary.net.RxClient;
import com.zhenqi.baselibrary.net.callback.RxCallBack;
import com.zhenqi.baselibrary.util.SPUtil;
import com.zhenqi.loginmodule.bean.LoginRegisterBean;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class MainModel extends BaseModel<MainPresent> {

    public void login(final String username, final String password) {
        RxClient.builder()
                .addCookies(true)
                .addParams("username", username)
                .addParams("password", password)
                .build()
                .rxPost(BaseApiService.LOGIN, new RxCallBack<LoginRegisterBean>() {
                    @Override
                    public void rxOnNext(LoginRegisterBean response) {
                        if (response.getData() != null && response.getErrorCode() == 0) {
                            SPUtil.save(SPUtil.USERNAME,username);
                            SPUtil.save(SPUtil.PASSWORD,password);
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
