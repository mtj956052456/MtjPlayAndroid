package com.zhenqi.baselibrary.view.crossprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/9
 * 描述: 交叉进度条
 */
public class MyCrossProgressBar extends View {


    public MyCrossProgressBar(Context context) {
        this(context, null);
    }

    public MyCrossProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCrossProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mBlueRoundRectPaint;   //蓝色画笔
    private Paint mYellowRoundRectPaint; //黄色画笔
    private Paint mRoundRectBgPaint;     //文字边框画笔

    private void init() {
        mBlueRoundRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBlueRoundRectPaint.setColor(0xFF76B3FF);

        mYellowRoundRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mYellowRoundRectPaint.setColor(0xFF5BB8FF);

        mRoundRectBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundRectBgPaint.setColor(0xFFFFFFFF);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (leftValue != 0 || rightValue != 0) {
            canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30, mRoundRectBgPaint);
            if(leftValue > rightValue){
                mBlueRoundRectPaint.setColor(0xFFFF793E);
            }else {
                mBlueRoundRectPaint.setColor(0xFF71DE95);
            }
            canvas.drawRoundRect(0, 0, getWidth() * leftValue / maxValue, getHeight(), 30, 30, mBlueRoundRectPaint);
            canvas.drawRect(30, 0, getWidth() * leftValue / maxValue, getHeight(), mBlueRoundRectPaint);

            canvas.drawRoundRect(getWidth(), 0, getWidth() * leftValue / maxValue, getHeight(), 30, 30, mYellowRoundRectPaint);
            canvas.drawRect(getWidth() - 30, 0, getWidth() * leftValue / maxValue, getHeight(), mYellowRoundRectPaint);

            canvas.drawRect(getWidth() * leftValue / maxValue - 1, 0, getWidth() * leftValue / maxValue + 1, getHeight(), mRoundRectBgPaint);//中间的白色
            //canvas.drawRoundRect(0, 0, getWidth() * leftValue / maxValue, getHeight(), 30, 30, mBlueRoundRectPaint);
        }
    }

    private float maxValue = 100;
    private float leftValue = 0;
    private float rightValue = 0;

    /**
     * 设置左 右值
     *
     * @param leftValue
     * @param rightValue
     */
    public void setLeftRightValue(float leftValue, float rightValue) {
        this.maxValue = leftValue + rightValue;
        this.leftValue = leftValue;
        this.rightValue = rightValue;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width, height);
        }
    }

}
