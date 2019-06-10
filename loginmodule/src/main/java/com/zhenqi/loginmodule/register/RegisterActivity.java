package com.zhenqi.loginmodule.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.zhenqi.baselibrary.base.ActivityHolder;
import com.zhenqi.baselibrary.mvp.BaseMvpActivity;
import com.zhenqi.baselibrary.mvp.BasePresenter;
import com.zhenqi.loginmodule.R;
import com.zhenqi.loginmodule.R2;
import com.zhenqi.loginmodule.bean.LoginRegisterBean;
import com.zhenqi.loginmodule.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建人:孟腾蛟
 * 时间: 2019/06/10
 * 描述:
 */
public class RegisterActivity extends BaseMvpActivity<RegisterPresent> {

    @BindView(R2.id.et_username)
    EditText etUsername;
    @BindView(R2.id.et_userpassword)
    EditText etUserpassword;
    @BindView(R2.id.et_repassword)
    EditText etRepassword;
    @BindView(R2.id.bt_register)
    Button btRegister;
    @BindView(R2.id.tv_login)
    TextView tvLogin;

    public static final String USERNAME = "USERNAME";
    public static final String USERPASSWORD = "USERPASSWORD";
    private LoginRegisterBean loginRegisterBean;

    @Override
    protected int setView() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresent createPresenter() {
        return new RegisterPresent();
    }

    @Override
    protected void afterBinder() {
        super.afterBinder();
    }

    @OnClick({R2.id.bt_register, R2.id.tv_login})
    public void onViewClicked(View view) {
        int i = view.getId();
        if (i == R.id.bt_register) {
            String username = etUsername.getText().toString().trim();
            String userpassword = etUserpassword.getText().toString().trim();
            String repassword = etRepassword.getText().toString().trim();
            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(userpassword) || TextUtils.isEmpty(repassword)) {
                ToastUtils.showShort("请输入用户名或密码");
            }
            if(!userpassword.equals(repassword)){
                ToastUtils.showShort("密码不一致,请检查.");
            }
            mBasePresenter.register(username, userpassword, repassword);
        } else if (i == R.id.tv_login) {
            finish();//去登陆 不进行任何操作
        }
    }

    public void registerSucceed(LoginRegisterBean loginRegisterBean) {
        this.loginRegisterBean = loginRegisterBean;
        startLogin();
    }

    private void startLogin() {
        Intent intent = new Intent();
        if (loginRegisterBean != null) {
            intent.putExtra(USERNAME, loginRegisterBean.getData().getUsername());
            intent.putExtra(USERPASSWORD, loginRegisterBean.getData().getPassword());
        }
        setResult(LoginActivity.RESULTCODE, intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
