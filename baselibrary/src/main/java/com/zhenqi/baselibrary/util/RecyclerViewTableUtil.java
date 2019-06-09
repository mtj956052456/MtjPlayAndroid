package com.zhenqi.baselibrary.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @author 孟腾蛟
 * @time 2018/11/9 2018 11
 * @des
 */

public class RecyclerViewTableUtil {

    private final static RecyclerViewTableUtil INSTANCE = new RecyclerViewTableUtil();

    private RecyclerViewTableUtil() {
    }

    public static RecyclerViewTableUtil getInstance() {
        return INSTANCE;
    }

    private RecyclerView mLeftRecycler, mRightRecycler;

    public void init(Context context, RecyclerView leftRecycler, BaseQuickAdapter leftRecyclerAdapter, RecyclerView rightRecycler, BaseQuickAdapter rightRecyclerAdapter) {
        mLeftRecycler = leftRecycler;
        mRightRecycler = rightRecycler;
        leftRecycler.setAdapter(leftRecyclerAdapter);
        rightRecycler.setAdapter(rightRecyclerAdapter);
        leftRecycler.setLayoutManager(new LinearLayoutManager(context));
        rightRecycler.setLayoutManager(new LinearLayoutManager(context));
        leftRecycler.addOnScrollListener(mOnLeftListener);
        rightRecycler.addOnScrollListener(mOnRightListener);
    }


    private RecyclerView.OnScrollListener mOnLeftListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                mRightRecycler.removeOnScrollListener(mOnRightListener);
                mRightRecycler.scrollBy(0, dy);
                mRightRecycler.addOnScrollListener(mOnRightListener);
            }
        }
    };

    private RecyclerView.OnScrollListener mOnRightListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (recyclerView.getScrollState() != RecyclerView.SCROLL_STATE_IDLE) {
                mLeftRecycler.removeOnScrollListener(mOnLeftListener);
                mLeftRecycler.scrollBy(0, dy);
                mLeftRecycler.addOnScrollListener(mOnLeftListener);
            }
        }
    };
}
