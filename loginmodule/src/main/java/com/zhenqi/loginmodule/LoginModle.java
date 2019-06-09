package com.zhenqi.loginmodule;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class LoginModle {

    LoginPresent mLoginPresent;

    public void setLoginPresent(LoginPresent present) {
        this.mLoginPresent = present;

    }

    public void onDestory() {
        mLoginPresent = null;
    }

}
