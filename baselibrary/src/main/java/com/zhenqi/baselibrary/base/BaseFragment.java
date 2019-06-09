package com.zhenqi.baselibrary.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zhenqi.baselibrary.util.SPWXUtil;
import com.zhenqi.baselibrary.view.DialogUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

/**
 * @author 孟腾蛟
 * @time 2019/3/23
 * @des 碎片基类
 */
public abstract class BaseFragment extends Fragment {
    protected View rootView;
    protected Bundle savedInstanceState;
    private Unbinder unBinder;
    private FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        if (null == rootView) {
            rootView = inflater.inflate(setView(), container, false);
            unBinder = ButterKnife.bind(this, rootView);
            firstBinder();
        } else {
            if (null != rootView.getParent()) {
                ViewGroup parent = (ViewGroup) rootView.getParent();
                parent.removeAllViews();
            }
            unBinder = ButterKnife.bind(this, rootView);
            otherBinder();
        }
        fm = getActivity().getSupportFragmentManager();
        EventBus.getDefault().register(this);
        return rootView;
    }

    private int isShown; //是否显示过

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isShown == 0)
                setUserVisibleHintData();
            isShown++;

            setSwipeRefreshLayout(swipe);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void themeColor(EventBusBean busBean) {

    }

    /**
     * 初始化数据
     *
     * @return
     */
    protected void setUserVisibleHintData() {

    }

    /**
     * 传入需要绑定的fragment布局
     */
    protected abstract int setView();

    /**
     * 首次用黄油刀绑定
     */
    protected abstract void firstBinder();

    /**
     * 黄油刀绑定过一次后,界面destroy恢复后触发的方法
     */
    private void otherBinder() {
    }


    /**
     * 给控件添加一个状态栏的高度,同时用padding把这段高度给占掉,让布局正产显示
     */
    protected void setHeadView(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (statusId > 0) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                int height = layoutParams.height;
                int dimensionPixelSize = getResources().getDimensionPixelSize(statusId);
                height = height + dimensionPixelSize;
                layoutParams.height = height;
                int left = view.getPaddingLeft();
                int top = view.getPaddingTop();
                int right = view.getPaddingRight();
                int bottom = view.getPaddingBottom();
                top = top + dimensionPixelSize;
                view.setPadding(left, top, right, bottom);
                view.setLayoutParams(layoutParams);
            }
        }
    }

    protected void intoActivity(Class<?> cls) {
        startActivity(new Intent(getActivity(), cls));
    }

    @Override
    public void onDestroyView() {
        if (unBinder != null) {
            unBinder.unbind();
            unBinder = null;
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyView();
    }

    protected boolean isEmpty(String msg) {
        return TextUtils.isEmpty(msg);
    }

    protected void setTv(String msg, TextView tv) {
        if (!TextUtils.isEmpty(msg)) {
            if (null != tv) {
                tv.setText(msg);
            } else {
                Log.e("MTJ", "控件空指针");
            }
        } else {
            Log.e("MTJ", "msg的数据为空");
        }
    }

    protected boolean isEmpty(List<?> list) {
        return null == list || list.isEmpty();
    }

    protected void setVisible(View view, boolean isVisible) {
        if (null != view) {
            if (isVisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    public Dialog mDialog;
    public SwipeRefreshLayout swipe;
    private int DISMISS_DIALOG = 1;
    private int DISMISS_SWIPE = 2;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == DISMISS_DIALOG) {
                if (mDialog != null && mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            } else if (msg.what == DISMISS_SWIPE) {
                if (swipe != null && swipe.isRefreshing()) {
                    swipe.setRefreshing(false);
                }
            }
        }
    };

    /**
     * 初始化Dialog
     *
     * @param title
     */
    public void initDialog(String title) {
        mDialog = DialogUtil.CreateLoadingDialog(getContext(), title);
    }

    /**
     * 初始化SWipe
     *
     * @param refreshLayout
     */
    public void initSwipe(SwipeRefreshLayout refreshLayout) {
        swipe = refreshLayout;
        setSwipeRefreshLayout(swipe);
    }

    public void showDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    public void dismissDialog() {
        mHandler.sendEmptyMessageDelayed(DISMISS_DIALOG,500);
    }

    public void showSwip() {
        if (swipe != null && !swipe.isRefreshing()) {
            swipe.setRefreshing(true);
        }
    }

    public void dismissSiwp() {
        mHandler.sendEmptyMessage(DISMISS_SWIPE);
    }

    /**
     * 微信是否登录
     *
     * @return
     */
    public boolean wxIsLogin() {
        return !TextUtils.isEmpty(SPWXUtil.get(SPWXUtil.WX_OPENID)) && !TextUtils.isEmpty(SPWXUtil.get(SPWXUtil.WX_USER_GID));
    }

    /**
     * 设置下拉刷新颜色
     *
     * @param refreshLayout
     */
    public void setSwipeRefreshLayout(SwipeRefreshLayout refreshLayout) {
        if (refreshLayout != null) {
            refreshLayout.setColorSchemeColors(getResources().getColor(BaseConstant.toolBarColor));
        }
    }


    /**
     * 权限检查
     */
    public void LocationPermission() {
        if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) ||
                (checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE}, 1);
        }
    }


}
