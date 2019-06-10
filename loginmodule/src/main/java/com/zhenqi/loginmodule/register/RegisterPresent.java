package com.zhenqi.loginmodule.register;

import android.content.Context;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.zhenqi.baselibrary.mvp.BasePresenter;
import com.zhenqi.loginmodule.bean.LoginRegisterBean;
import com.zhenqi.loginmodule.login.LoginModel;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class RegisterPresent extends BasePresenter<RegisterActivity, RegisterModel> {

    protected RegisterModel mLoginModel;

    protected Context mContext;

    public RegisterPresent() {
        mLoginModel = new RegisterModel();
        mLoginModel.setBasePresenter(this);
    }

    /**
     * 注册
     *
     * @param username
     * @param userpassword
     * @param repassword
     */
    public void register(String username, String userpassword, String repassword) {
        mBaseModel.requestRegister(username, userpassword, repassword);
    }

    /**
     * 注册成功
     */
    public void onRegisterSucceed(LoginRegisterBean bean) {
        mBaseView.registerSucceed(bean);
    }

    /**
     * 注册失败
     */
    public void onRegisterFailure(String mes) {
        ToastUtils.showShort("注册失败: " + mes);
    }

    @Override
    public RegisterModel createModel() {
        return new RegisterModel();
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
