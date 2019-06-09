package com.zhenqi.loginmodule;

import android.content.Context;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class LoginPresent {

    protected LoginModle mLoginModle;

    protected Context mContext;

    public LoginPresent() {
        mLoginModle = new LoginModle();
        mLoginModle.setLoginPresent(this);
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void onDestory() {
        if (mLoginModle != null) {
            mLoginModle.onDestory();
            mLoginModle = null;
        }
    }

}
