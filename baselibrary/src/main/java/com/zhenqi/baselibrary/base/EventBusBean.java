package com.zhenqi.baselibrary.base;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/22
 * 描述:  EventBus传递数据
 */
public class EventBusBean {

    private int mMainColor;//主页的颜色
    private boolean mFlushData;//刷新数据

    public void setFlushData(boolean flushData) {
        mFlushData = flushData;
    }

    public boolean isFlushData() {
        return mFlushData;
    }

    public int getMainColor() {
        return mMainColor;
    }

    public void setMainColor(int mainColor) {
        mMainColor = mainColor;
    }
}
