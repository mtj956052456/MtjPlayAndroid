package com.zhenqi.baselibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.mikephil.charting.charts.BarChart;


public class BarChartInViewPager extends BarChart {

        PointF downPoint = new PointF();

        public BarChartInViewPager(Context context) {
            super(context);
        }

        public BarChartInViewPager(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public BarChartInViewPager(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouchEvent(MotionEvent evt) {
            switch (evt.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downPoint.x = evt.getX();
                    downPoint.y = evt.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (getScaleX() > 1 && Math.abs(evt.getX() - downPoint.x) > 5) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    break;
            }
            return super.onTouchEvent(evt);
        }
    }