package com.zhenqi.loginmodule;

import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zhenqi.baselibrary.mvp.BaseMvpActivity;
import com.zhenqi.baselibrary.util.VersionUtil;

import java.util.WeakHashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresent> {


    @BindView(R2.id.et_username)
    EditText mEtUsername;
    @BindView(R2.id.et_userpassword)
    EditText mEtUserpassword;
    @BindView(R2.id.bt_login)
    Button mBtLogin;
    @BindView(R2.id.tv_register)
    TextView mTvRegister;

    @Override
    protected int setView() {
        return R.layout.activity_login;
    }

    private float screenWidth, screenHeight;

    @Override
    protected void afterBinder() {
        super.afterBinder();
        //获得屏幕的宽和高
        Display display = getWindowManager().getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
    }

    @Override
    protected LoginPresent createPresenter() {
        return new LoginPresent();
    }

    public String getUsername() {
        return mEtUsername.getText().toString().trim();
    }

    public String getPassword() {
        return mEtUserpassword.getText().toString().trim();
    }

    @OnClick({R2.id.bt_login, R2.id.tv_register})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.bt_login) {
            WeakHashMap<String, String> map = new WeakHashMap<>();
            map.put("username", getUsername());
            map.put("password", getPassword());
            map.put("clienttype", "Android");
            map.put("clientversion", VersionUtil.getVersion(this));
            map.put("screen", screenWidth + "*" + screenHeight);
            mBasePresenter.login(map);
        } else if (id == R.id.tv_register) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
