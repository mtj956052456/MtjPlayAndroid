package com.zhenqi.baselibrary.mvp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.zhenqi.baselibrary.base.LazyBaseFragment;


/**
 * Created by wsf on 2019/1/2.
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends LazyBaseFragment {

    protected P mBasePresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("cesi---->", "BaseMvpFragment onCreate");
        mBasePresenter = createPresenter();
        if (mBasePresenter != null) {
            mBasePresenter.setBaseView(this);
            mBasePresenter.setContext(mContext);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mBasePresenter != null) {
            mBasePresenter.onDestroy();
            mBasePresenter = null;
        }
    }
}
