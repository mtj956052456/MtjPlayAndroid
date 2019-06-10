package com.zhenqi.mtjdemo.main;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import com.zhenqi.baselibrary.mvp.BasePresenter;
import com.zhenqi.baselibrary.util.SPUtil;
import com.zhenqi.loginmodule.bean.LoginRegisterBean;
import com.zhenqi.loginmodule.login.LoginActivity;
import com.zhenqi.loginmodule.register.RegisterActivity;

/**
 * @author mtj
 * @time 2019/6/9 2019 06
 * @des
 */
public class MainPresent extends BasePresenter<MainActivity, MainModel> {

    protected MainModel mMainModel;
    protected Context mContext;

    MainPresent() {
        mMainModel = new MainModel();
        mMainModel.setBasePresenter(this);
    }

    private String username = "";
    private String password = "";

    /**
     * 检查登录状态
     */
    public void checkLoginStatus() {
        username = SPUtil.get(SPUtil.USERNAME);
        password = SPUtil.get(SPUtil.PASSWORD);
        mMainModel.login(username, password);
    }

    /**
     * 登录成功
     *
     * @param bean
     */
    public void loginSuccess(LoginRegisterBean bean) {
        Toast.makeText(mContext, "欢迎来到玩Android", Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录失败
     *
     * @param message
     */
    public void loginError(String message) {
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            new AlertDialog.Builder(mBaseView).setTitle("提示").setMessage("尚未登录玩Android用户,是否前往登录?"
            ).setNegativeButton("去登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mBaseView.startActivity(new Intent(mBaseView, LoginActivity.class));
                }
            }).setPositiveButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();
        } else {
            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public MainModel createModel() {
        return new MainModel();
    }


    public void setContext(Context context) {
        mContext = context;
    }

    public void onDestory() {
        if (mMainModel != null) {
            mMainModel.onDestroy();
            mMainModel = null;
        }
    }


}
