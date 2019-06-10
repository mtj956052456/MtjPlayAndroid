package com.zhenqi.loginmodule.login;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.zhenqi.baselibrary.mvp.BasePresenter;
import com.zhenqi.loginmodule.bean.LoginRegisterBean;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class LoginPresent extends BasePresenter<LoginActivity, LoginModel> {

    protected LoginModel mLoginModel;

    protected Context mContext;

    public LoginPresent() {
        mLoginModel = new LoginModel();
        mLoginModel.setBasePresenter(this);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        mLoginModel.login(username, password);
    }

    /**
     * 登录成功
     *
     * @param bean
     */
    public void loginSuccess(LoginRegisterBean bean) {
        Toast.makeText(mContext, "登陆成功", Toast.LENGTH_SHORT).show();
        mBaseView.finish();
    }

    /**
     * 登录失败
     *
     * @param message
     */
    public void loginError(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public LoginModel createModel() {
        return new LoginModel();
    }


    public void setContext(Context context) {
        mContext = context;
    }

    public void onDestory() {
        if (mLoginModel != null) {
            mLoginModel.onDestroy();
            mLoginModel = null;
        }
    }


}
