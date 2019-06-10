package com.zhenqi.baselibrary.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.zhenqi.baselibrary.base.BaseActivity;

/**
 * @author mtj
 * @time 2019/6/8 2019 06
 * @des
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity  implements IBaseView{

    protected P mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mBasePresenter = createPresenter();
        if(mBasePresenter != null){
            mBasePresenter.setBaseView(this);
            mBasePresenter.setContext(this);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mBasePresenter != null){
            mBasePresenter.onDestroy();
            mBasePresenter = null;
        }
    }

    protected abstract P createPresenter();
}
