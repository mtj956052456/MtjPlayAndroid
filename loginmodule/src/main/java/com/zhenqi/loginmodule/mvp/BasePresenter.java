package com.zhenqi.loginmodule.mvp;

import android.content.Context;

/**
 * @author mtj
 * @time 2019/6/8 2019 06
 * @des
 */
public abstract class BasePresenter{

    private static final String TAG = "BasePresenter";
    protected BaseModel mBaseModel;

    protected Context mContext;

    public BasePresenter() {
        mBaseModel = createModel();
        mBaseModel.setBasePresenter(this);
    }

    protected abstract BaseModel createModel();

    public void setContext(Context context) {
        mContext = context;
    }

    public void onDestory() {
        if (mBaseModel != null) {
            mBaseModel.onDestory();
            mBaseModel = null;
        }
    }
}
