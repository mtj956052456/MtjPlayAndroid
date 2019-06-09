package com.zhenqi.baselibrary.view.arcview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/5/10
 * 描述:  带有间隔的圆环
 */
public class MyArcView extends View {

    public MyArcView(Context context) {
        this(context, null);
    }

    public MyArcView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mCirclePaint;     //背景圆 画笔
    private Paint mLinePaint;       //线 画笔
    private Paint mTextPaint;       //值 画笔
    private Paint mCenterTextPaint; //中间文字 画笔
    private Paint mArcPaint;        //圆弧画笔
    private RectF rectF;            //圆的位置
    private Rect textRect;          //测量文字

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(0xFFF7F7F9);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(arcRadius); // 线条宽度为 20 像素

        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setColor(Color.parseColor("#FDD175"));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(arcRadius); // 线条宽度为 20 像素

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLACK);
        mLinePaint.setStrokeWidth(dpToPx(1));

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(spToPx(11));

        textRect = new Rect();
    }


    private List<MyArcBean> beans = new ArrayList<>();       //数据集
    private String centerDownValue = "";       //中下值
    private String centerUpValue = "";         //中上值
    private int radius = 150;                  //半径
    private int arcRadius = 30;                //圆环宽度
    private float maxValue = 100;              //总值
    private float arcOffset = 2;               //单位: 度  圆环的间隔
    private float line1Length = 15;            //第一根线的长度
    private float line2Length = 50;            //第二根线的长度
    private boolean showLineText = false;      //显示线和值
    private boolean showArcBg = false;         //显示圆环的背景
    private boolean showCenterValue = false;   //显示中间的字体

    public void setData(List<MyArcBean> beans) {
        this.beans = beans;
        invalidate();
    }

    /**
     * 设置中间下边字体
     *
     * @param centerDownValue
     */
    public void setCenterDownValue(String centerDownValue) {
        this.centerDownValue = centerDownValue;
    }

    /**
     * 设置中间上边字体
     *
     * @param centerUpValue
     */
    public void setCenterUpValue(String centerUpValue) {
        this.centerUpValue = centerUpValue;
    }

    /**
     * 设置圆环的宽度
     *
     * @param arcRadius
     */
    public void setArcRadius(int arcRadius) {
        this.arcRadius = arcRadius;
    }

    /**
     * 是否显示间字体
     *
     * @param showCenterValue
     */
    public void setShowCenterValue(boolean showCenterValue) {
        this.showCenterValue = showCenterValue;
    }

    /**
     * 设置半径
     *
     * @param radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
        //设置圆环的位置
        rectF = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius);
    }

    /**
     * 设置显示线和文字
     *
     * @param showLineText
     */
    public void setShowLineText(boolean showLineText) {
        this.showLineText = showLineText;
    }

    /**
     * 环与环的间距
     */
    public void setArcOffset(float arcOffset) {
        this.arcOffset = arcOffset;
    }

    /**
     * 设置是否显示圆环背景
     *
     * @param showArcBg
     */
    public void setShowArcBg(boolean showArcBg) {
        this.showArcBg = showArcBg;
    }

    /**
     * 设置第一根线的长度
     *
     * @param line1Length
     */
    public void setLine1Length(float line1Length) {
        this.line1Length = line1Length;
    }

    /**
     * 设置第二根线的长度
     *
     * @param line2Length
     */
    public void setLine2Length(float line2Length) {
        this.line2Length = line2Length;
    }

    /**
     * 清理数据
     */
    public void clearDatas() {
        beans.clear();
        invalidate();
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float tempAngle = 0;// 临时值
        float centerFlag = 0;// 中间计算值
        if (beans.isEmpty())
            return;
        for (int i = 0; i < beans.size(); i++) {
            MyArcBean bean = beans.get(i);
            float angle = (360 - arcOffset * beans.size()) * bean.getValue() / maxValue; //每个值的占百分比
            mArcPaint.setColor(bean.getColor());
            mArcPaint.setStrokeWidth(arcRadius); // 圆环宽度
            canvas.drawArc(rectF, tempAngle, angle, false, mArcPaint);
            tempAngle += angle;
            //开始画圆环间隔(白色)
            mArcPaint.setColor(Color.WHITE);
            canvas.drawArc(rectF, tempAngle, arcOffset, false, mArcPaint);//环间距
            mArcPaint.setColor(bean.getColor());
            tempAngle += arcOffset;
            //结束画圆环间隔(白色)
            if (showLineText && bean.getValue() != 0) {//值不为0时才画
                float radian;
                if (i == 0) {
                    radian = (float) Math.toRadians(tempAngle / 2);            //计算第一次计算弧度
                } else {
                    radian = (float) Math.toRadians(angle / 2 + centerFlag);   //叠加计算弧度
                }
                //Log.e("MTJ", "radian:  " + radian);
                float x = (float) (getWidth() / 2 + Math.cos(radian) * (radius + arcRadius / 2));   //半径为内半径和圆环宽度
                float y = (float) (getHeight() / 2 + Math.sin(radian) * (radius + arcRadius / 2));
                //画线
                centerFlag = tempAngle; //计算后预留上一次的弧度
                int w = getWidth() / 2;
                int h = getHeight() / 2;
                String value = String.valueOf(bean.getName());
                mLinePaint.setColor(bean.getColor());
                //测量文字宽高
                mTextPaint.getTextBounds(value, 0, value.length(), textRect);
                int textWidth = textRect.width() + 20; //加上20px 字与线 的间隔
                int textHeight = textRect.height();
                if (x > w && y < h) {     //第一象限
                    canvas.drawLine(x, y, x + line1Length, y - line1Length, mLinePaint);
                    canvas.drawLine(x + line1Length, y - line1Length, x + line2Length, y - line1Length, mLinePaint);
                    canvas.drawText(value, x + line2Length, y - line1Length + textHeight / 2, mTextPaint);
                } else if (x > w && y > h) { //第二象限
                    canvas.drawLine(x, y, x + line1Length, y + line1Length, mLinePaint);
                    canvas.drawLine(x + line1Length, y + line1Length, x + line2Length, y + line1Length, mLinePaint);
                    canvas.drawText(value, x + line2Length, y + line1Length + textHeight / 2, mTextPaint);
                } else if (x < w && y > h) { //第三象限
                    canvas.drawLine(x, y, x - line1Length, y + line1Length, mLinePaint);
                    canvas.drawLine(x - line1Length, y + line1Length, x - line2Length, y + line1Length, mLinePaint);
                    canvas.drawText(value, x - line2Length - textWidth, y + line1Length + textHeight / 2, mTextPaint);
                } else {                   //第四象限
                    canvas.drawLine(x, y, x - line1Length, y - line1Length, mLinePaint);
                    canvas.drawLine(x - line1Length, y - line1Length, x - line2Length, y - line1Length, mLinePaint);
                    canvas.drawText(value, x - line2Length - textWidth, y - line1Length + textHeight / 2, mTextPaint);
                }
            }
        }
        if (showCenterValue) {
            //画值和类型
            mCenterTextPaint.setColor(0xFF5B636B);
            mCenterTextPaint.setTextSize(spToPx(14));
            float valueW = mCenterTextPaint.measureText(String.valueOf(centerUpValue));
            canvas.drawText(String.valueOf(centerUpValue), getWidth() / 2 - valueW / 2, getHeight() / 2 - valueW / 4, mCenterTextPaint);

            float typeW = mCenterTextPaint.measureText(centerDownValue);
            mCenterTextPaint.setColor(0xFF878E96);
            mCenterTextPaint.setTextSize(spToPx(13));
            canvas.drawText(centerDownValue, getWidth() / 2 - typeW / 2, getHeight() / 2 + typeW / 4, mCenterTextPaint);
        }
        //画圆环的背景色
        if (showArcBg) {
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mCirclePaint);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //设置圆环的位置
        rectF = new RectF(getWidth() / 2 - radius, getHeight() / 2 - radius, getWidth() / 2 + radius, getHeight() / 2 + radius);
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


    /**
     * dp转化成为px
     *
     * @param dp
     * @return
     */
    private int dpToPx(int dp) {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f * (dp >= 0 ? 1 : -1));
    }

    /**
     * sp转化为px
     *
     * @param sp
     * @return
     */
    private int spToPx(int sp) {
        float scaledDensity = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f * (sp >= 0 ? 1 : -1));
    }
}
