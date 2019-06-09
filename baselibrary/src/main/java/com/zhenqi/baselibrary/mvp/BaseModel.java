package com.zhenqi.baselibrary.mvp;

/**
 * @author mtj
 * @time 2019/6/8 2019 06
 * @des
 */
public class BaseModel<P extends BasePresenter> {

    private static final String TAG = "BaseModel";

    protected P mBasePresenter;

    public P getBasePresenter() {
        return mBasePresenter;
    }

    public void setBasePresenter(P basePresenter) {
        mBasePresenter = basePresenter;
    }

    public BaseModel() {

    }

    public void onDestroy() {
        mBasePresenter = null;
    }
}
