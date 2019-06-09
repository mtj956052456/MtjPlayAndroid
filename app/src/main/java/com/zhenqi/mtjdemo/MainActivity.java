package com.zhenqi.mtjdemo;

import android.content.Intent;
import android.widget.TextView;

import com.zhenqi.baselibrary.base.BaseActivity;
import com.zhenqi.loginmodule.LoginActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv_hello)
    TextView mTvHello;

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterBinder() {
        super.afterBinder();
    }


    @OnClick(R.id.tv_hello)
    public void onViewClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
