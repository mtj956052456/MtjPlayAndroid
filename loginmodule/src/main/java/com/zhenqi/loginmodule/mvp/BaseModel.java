package com.zhenqi.loginmodule.mvp;

/**
 * @author mtj
 * @time 2019/6/8 2019 06
 * @des
 */
public class BaseModel {

    private static final String TAG = "BaseModel";

    protected BasePresenter mBasePresenter;

    public BasePresenter getBasePresenter() {
        return mBasePresenter;
    }

    public void setBasePresenter(BasePresenter basePresenter) {
        mBasePresenter = basePresenter;
    }

    public BaseModel() {

    }

    public void onDestory() {
        mBasePresenter = null;
    }
}
