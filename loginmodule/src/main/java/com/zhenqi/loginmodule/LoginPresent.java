package com.zhenqi.loginmodule;

import android.content.Context;
import android.widget.Toast;

import com.zhenqi.baselibrary.mvp.BasePresenter;

import java.util.WeakHashMap;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class LoginPresent extends BasePresenter<LoginActivity,LoginModel> {

    protected LoginModel mLoginModel;

    protected Context mContext;

    public LoginPresent() {
        mLoginModel = new LoginModel();
        mLoginModel.setBasePresenter(this);
    }

    public void login(WeakHashMap<String,String> map){
        mLoginModel.login(map);
    }

    public void loginSuccess(String result) {
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }

    public void loginError(String message){
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
