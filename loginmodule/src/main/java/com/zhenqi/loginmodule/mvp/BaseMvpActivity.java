package com.zhenqi.loginmodule.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhenqi.baselibrary.base.BaseActivity;

/**
 * @author mtj
 * @time 2019/6/8 2019 06
 * @des
 */
public abstract class BaseMvpActivity extends BaseActivity {

    protected BasePresenter mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBasePresenter = createPresenter();
        if(mBasePresenter != null){
            mBasePresenter.setContext(this);
        }
    }

    protected abstract BasePresenter createPresenter();
}
