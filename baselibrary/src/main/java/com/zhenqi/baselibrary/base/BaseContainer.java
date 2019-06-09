package com.zhenqi.baselibrary.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhenqi.baselibrary.util.Lg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyz on 2017/9/25.
 * chenyz@yizhekeji.com
 */

public abstract class BaseContainer extends BaseActivity {
    /**
     * fragment 容器类
     */
    protected FragmentManager fm;                   //fm管理器
    protected int mContainerId;                     //容器id,必须的
    protected Fragment mCurrentFragment;            //获取当前Fragment,非必要情况无需
    protected TextView mCurrentTv;                  //获取当前TextView,非必要情况无需
    protected ImageView mCurrentImg;                //获取当前imgView,非必要情况无序
    protected List<View> mLists;                    //预留的可能有多个view的情况

    protected abstract int setContainerId();

    @Override
    protected void afterBinder() {
        super.afterBinder();
        mContainerId = setContainerId();
        fm = getSupportFragmentManager();
    }

    /**
     * 相关的操作最基本也需要知道相关的containerId,所以这个操作只能放在binderView之后
     */
    protected void initFragmentKey() {
        if (null != getIntent().getStringExtra("fragmentKey")) {
            addFragment(getIntent().getStringExtra("fragmentKey"));
        } else {
            Lg.e("未传递有效的fragmentKey,请传入");
        }
    }

    protected void setFm(Fragment fragment) {
        fm.beginTransaction().add(mContainerId, fragment, fragment.getClass().getSimpleName()).commit();
    }

    /**
     * 一个fragment容易应该有两种方法
     * 一种是隐藏显示的show,另外一钟就是霸道的replace()
     * show适用于主界面的那种隐藏显示,包含add()和hide()方法
     * replace则适用与fragment的跳转以及回退栈的工作,如果想要实现回退栈的效果,就应该用replace
     * 返回事件的点击则需要按照回退栈是否具有fragment
     *
     * @param fragmentKey
     */
    protected void showFm(String fragmentKey) {
        FragmentTransaction transaction = fm.beginTransaction();
        if (!TextUtils.equals(fragmentKey, mCurrentFragment.getClass().getSimpleName())) {         //如果相同,说明是当前的fragment,无需操作
            if (null == fm.findFragmentByTag(fragmentKey)) {
                addFragment(fragmentKey, transaction);
            } else {
                hasFragment(transaction, fragmentKey);
            }
        } else {
            Lg.e("展示的是当前界面");
        }
    }


    /**
     * 只是显示隐藏已有的fragment 的话直接写在抽象类里面也能实现
     */
    protected void hasFragment(FragmentTransaction transaction, String fragmentKey) {
        Fragment fragmentByTag = fm.findFragmentByTag(fragmentKey);                                 //从栈中通过tag寻找fragment
        Lg.e("mCurrentFragment--------" + mCurrentFragment.getClass().getSimpleName());
        Lg.e("fragmentByTag-----------" + fragmentByTag.getClass().getSimpleName());
        transaction.hide(mCurrentFragment).show(fragmentByTag).commit();                            //隐藏显示操作
        mCurrentFragment = fragmentByTag;                                                           //初始化当前fragment
        Lg.e("mCurrentFragment--------" + mCurrentFragment.getClass().getSimpleName());
    }


    /**
     * 根据传进来的fragmentKey,来判断replace到哪一个fragment,同时存储tag以便需要的时候获取
     *
     * @param fragmentKey replace fragment的simpleClassName(fragment的简单类名)
     * @param transaction
     */
    protected void initReplace(String fragmentKey, FragmentTransaction transaction) {
    }

    /**
     * 在展示新的fragment的时候比如主界面的时候使用
     */
    protected void addFragment(String fragmentKey, FragmentTransaction transaction) {
    }

    /**
     * 在跳转到一个新容器并且该容器还没有fragment的时候使用
     *
     * @param fragmentKey
     */
    protected void addFragment(String fragmentKey) {
    }

    /**
     * 设置当前界面view的selector方法,方便切换
     *
     * @param tv
     * @param img
     */
    protected void initSelector(TextView tv, ImageView img) {  //初始化当前项和选中项的图片和文字颜色
        mCurrentTv.setSelected(false);                       //将当前项的文字颜色变为未选中
        mCurrentImg.setSelected(false);                      //将当前项的图片颜色变为未选中
        tv.setSelected(true);                                //将选中项的文字改为选中
        img.setSelected(true);                               //将选中项的图片改为选中
        mCurrentTv = tv;                                     //将当前项改为目标对象文字
        mCurrentImg = img;                                   //将当前项改为目标对象图片
    }

    public BaseContainer setCurrentTv(TextView tv) {
        mCurrentTv = tv;
        return this;
    }

    public BaseContainer setCurrentImg(ImageView imgV) {
        mCurrentImg = imgV;
        return this;
    }

    public BaseContainer setViews(View views) {
        if (null == mLists) {
            mLists = new ArrayList<>();
            mLists.add(views);
        } else {
            mLists.add(views);
        }
        return this;
    }

    /**
     * 触发按钮的返回点击时,检查回退栈是否为空,空的话直接finish,不为空的话弹出当前fragment替换为回退栈里面的顶部fragment
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (fm.getBackStackEntryCount() > 0) {
                fm.popBackStack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
