package com.zhenqi.mtjdemo.main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.TextView;

import com.zhenqi.baselibrary.base.BaseActivity;
import com.zhenqi.baselibrary.mvp.BaseMvpActivity;
import com.zhenqi.baselibrary.util.SPUtil;
import com.zhenqi.baselibrary.util.TextUtil;
import com.zhenqi.baselibrary.view.DialogUtil;
import com.zhenqi.loginmodule.login.LoginActivity;
import com.zhenqi.mtjdemo.R;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseMvpActivity<MainPresent> {

    @BindView(R.id.tv_hello)
    TextView mTvHello;

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterBinder() {
        super.afterBinder();
        mBasePresenter.checkLoginStatus();

    }


    @OnClick(R.id.tv_hello)
    public void onViewClicked() {
        //startActivity(new Intent(this, LoginActivity.class));

    }


    @Override
    protected MainPresent createPresenter() {
        return new MainPresent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
