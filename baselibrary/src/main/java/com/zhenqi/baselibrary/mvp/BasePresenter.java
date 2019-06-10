package com.zhenqi.baselibrary.mvp;

import android.content.Context;

/**
 * @author mtj
 * @time 2019/6/8 2019 06
 * @des
 */
public abstract class BasePresenter<V extends IBaseView, M extends BaseModel> implements BindViewModel<M> {

    private static final String TAG = "BasePresenter";
    protected M mBaseModel;
    protected V mBaseView;
    protected Context mContext;

    public BasePresenter() {
        mBaseModel = createModel();
        mBaseModel.setBasePresenter(this);
    }

    public abstract M createModel();

    public void setBaseView(V baseView) {
        mBaseView = baseView;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void onDestroy() {
        if (mBaseModel != null) {
            mBaseModel.onDestroy();
            mBaseModel = null;
        }
        mBaseView = null;
    }

}
