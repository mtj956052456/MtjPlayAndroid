package com.zhenqi.loginmodule.login;

import android.content.Intent;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhenqi.baselibrary.mvp.BaseMvpActivity;
import com.zhenqi.baselibrary.util.VersionUtil;
import com.zhenqi.loginmodule.R;
import com.zhenqi.loginmodule.R2;
import com.zhenqi.loginmodule.register.RegisterActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresent> {


    @BindView(R2.id.et_username)
    EditText mEtUsername;
    @BindView(R2.id.et_userpassword)
    EditText mEtUserPassword;
    @BindView(R2.id.bt_login)
    Button mBtLogin;
    @BindView(R2.id.tv_register)
    TextView mTvRegister;

    public static final int REQUEST = 1;
    public static final int RESULTCODE = 2;

    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST && resultCode == RESULTCODE && data != null) {
            registerResult(data);
        }
    }

    @Override
    protected void afterBinder() {
        super.afterBinder();
    }

    @Override
    protected LoginPresent createPresenter() {
        return new LoginPresent();
    }

    public String getUsername() {
        return mEtUsername.getText().toString().trim();
    }

    public String getPassword() {
        return mEtUserPassword.getText().toString().trim();
    }

    @OnClick({R2.id.bt_login, R2.id.tv_register})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_login) {
            mBasePresenter.login(getUsername(),getPassword());
        } else if (id == R.id.tv_register) {
            startActivityForResult(new Intent(this, RegisterActivity.class), REQUEST);
        }
    }

    /**
     * 注册返回结果
     *
     * @param data
     */
    public void registerResult(Intent data) {
        mEtUsername.setText(data.getStringExtra(RegisterActivity.USERNAME));
        mEtUserPassword.setText(data.getStringExtra(RegisterActivity.USERPASSWORD));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
