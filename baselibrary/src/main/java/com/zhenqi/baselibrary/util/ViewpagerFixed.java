package com.zhenqi.baselibrary.util;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/17
 * 描述:  相册选择 异常捕获
 */
public class ViewpagerFixed extends ViewPager {

    public ViewpagerFixed(Context context) {
        super(context);
    }

    public ViewpagerFixed(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try {
            return super.onTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            //ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException ex) {
            //ex.printStackTrace();
        }
        return false;
    }

}
