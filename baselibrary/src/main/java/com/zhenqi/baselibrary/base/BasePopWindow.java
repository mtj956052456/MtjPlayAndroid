package com.zhenqi.baselibrary.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.zhenqi.baselibrary.R;
import com.zhenqi.baselibrary.util.NavigationBarUtils;
import com.zhenqi.baselibrary.util.StatusBarUtil;


/**
 * Created by C4B on 2017/9/3.
 * GoodLuck No Bug
 */

public abstract class BasePopWindow extends PopupWindow {

    protected Activity mActivity;
    private WindowManager wm;
    private View maskView;
    private boolean isAddBack = false;   //添加灰色背景判断条件

    public BasePopWindow(Activity activity) {
        super(activity);
        mActivity = activity;
        wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        initStyle();
    }

    /**
     * 可点击
     **/
    protected void initCanTouch() {
        setContentView(SetView());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(mActivity.getResources().getDrawable(android.R.color.transparent));
        setTouchable(true);
        setFocusable(true);
        setOutsideTouchable(true);
    }

    /**
     * 不可点击
     **/
    protected void initNoTouch() {
        setContentView(SetView());
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(mActivity.getResources().getDrawable(android.R.color.transparent));
        setTouchable(false);
        setFocusable(true);
        setOutsideTouchable(false);
    }

    protected abstract View SetView();

    /**
     * 针对8.0版本
     * 让添加的view置于pop底部
     **/
    @TargetApi(Build.VERSION_CODES.M)
    private void initStyle() {
        setWindowLayoutType(WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        if (isAddBack) {
            if (null != parent && null != parent.getWindowToken()) {
                addBackView(parent.getWindowToken());
            }
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        if (isAddBack) {
            addBackView(anchor.getWindowToken());
        }
        //        if (Build.VERSION.SDK_INT >= 24) {
        //            Rect visibleFrame = new Rect();
        //            anchor.getGlobalVisibleRect(visibleFrame);
        //            int height = anchor.getResources().getDisplayMetrics().heightPixels - visibleFrame.bottom;
        //            setHeight(height);
        //        }
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    @Override
    public void dismiss() {
        if (isAddBack) {
            removeBackView();
            StatusBarUtil.setStatusBarColor(mActivity, mActivity.getResources().getColor(BaseConstant.toolBarColor));
        }
        super.dismiss();
    }

    /**
     * 添加灰色蒙层
     **/
    public void addBackView() {                                                                      //添加全屏蒙尘
        this.isAddBack = true;
    }

    private void addBackView(IBinder token) {                                                       //设置底层半透明view
        WindowManager.LayoutParams p = new WindowManager.LayoutParams();
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.format = PixelFormat.TRANSLUCENT;
        p.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
        p.token = token;
        p.windowAnimations = R.style.loadingDialog;
        //虚拟键做适配
        if (NavigationBarUtils.hasNavBar(mActivity)) {
            p.gravity = Gravity.TOP;
            p.height = NavigationBarUtils.getWindowHeight(mActivity);
        } else {
            p.height = WindowManager.LayoutParams.MATCH_PARENT;
        }
        maskView = new View(mActivity);
        maskView.setBackgroundColor(mActivity.getResources().getColor(R.color.window_bg));
        maskView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    removeBackView();
                    return true;
                }
                return false;
            }
        });
        wm.addView(maskView, p);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) { //大于等于27版本
            StatusBarUtil.setStatusBarColor(mActivity, mActivity.getColor(getStatusBarColor()));
        } else {
            StatusBarUtil.setStatusBarColor(mActivity, mActivity.getResources().getColor(BaseConstant.toolBarColor));
        }
    }

    private int getStatusBarColor() {
        int color = BaseConstant.toolBarColor;
        if (color == R.color.valueIllegal) {
            return R.color.grey_valueIllegal;
        } else if (color == R.color.excellent) {
            return R.color.grey_excellent;
        } else if (color == R.color.good) {
            return R.color.grey_good;
        } else if (color == R.color.mild) {
            return R.color.grey_mild;
        } else if (color == R.color.moderate) {
            return R.color.grey_moderate;
        } else if (color == R.color.heavy) {
            return R.color.grey_heavy;
        } else if (color == R.color.severe) {
            return R.color.grey_severe;
        }else if (color == R.color.colorPrimary) {
            return R.color.grey_blue;
        }
        return color;
    }

    private void removeBackView() {
        if (null != maskView) {
            wm.removeViewImmediate(maskView);
            maskView = null;
        }
    }

    /**
     * 居中显示
     **/
    public void showCenter() {
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }

    /**
     * 底部显示
     **/
    public void showBottom() {
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
    }

    /**
     * 底部显示
     **/
    public void showBottom(View view) {
        showAsDropDown(view, 0, 0, 0);
    }

}
