package com.zhenqi.baselibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import com.zhenqi.baselibrary.util.Lg;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/9
 * 描述: 进度条
 */
public class MyProgressBar extends View {


    public MyProgressBar(Context context) {
        this(context, null);
    }

    public MyProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mRoundRectPaint;   //画笔
    private Paint mRoundRectBgPaint;   //文字边框画笔

    private void init() {
        mRoundRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mRoundRectBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRoundRectBgPaint.setColor(0xFFF6F8F9);
    }

    private Shader shader;

    @SuppressLint("DrawAllocation")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float pbWidth = getWidth() * curValue / maxValue;//进度条长度
        shader = new LinearGradient(-pbWidth / 2, getHeight() / 2, pbWidth, getHeight() / 2, Color.parseColor("#FFFFFF"), color, Shader.TileMode.CLAMP);
        mRoundRectPaint.setShader(shader);
        canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30, mRoundRectBgPaint);
        if (curValue > maxValue) {
            canvas.drawRoundRect(0, 0, getWidth(), getHeight(), 30, 30, mRoundRectPaint);
        } else {
            canvas.drawRoundRect(0, 0, getWidth() * curValue / maxValue, getHeight(), 30, 30, mRoundRectPaint);
        }
    }

    private int color = Color.parseColor("#43ce17");

    public void setShader(int color) {
        this.color = color;
        invalidate();
    }

    private float maxValue = 100;
    private float curValue = 0;

    /**
     * 设置最大值
     *
     * @param maxValue
     */
    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * 设置值
     *
     * @param curValue
     */
    public void setCurValue(float curValue) {
        this.curValue = curValue;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Lg.e("2");
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
