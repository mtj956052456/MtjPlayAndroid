package com.zhenqi.baselibrary.view.myprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建者: lenovo
 * 时间: 2019/5/10
 * 描述:
 */
public class MyProgressBarView extends View {

    public MyProgressBarView(Context context) {
        this(context, null);
    }

    public MyProgressBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Paint mCirclePaint;
    private Paint mCenterTextPaint;
    private Paint mArcPaint;
    private RectF rectF;

    private void init() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(0xFFF7F7F9);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(arcRadius); // 线条宽度为 20 像素

        mCenterTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCenterTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));

        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setShader(new LinearGradient(100, 100, 500, 500, colors, null, Shader.TileMode.CLAMP));
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(arcRadius); // 线条宽度为 20 像素
    }


    public static int[] YELLOW = new int[]{Color.parseColor("#FDD175"), Color.parseColor("#FF9415"), Color.parseColor("#FDD175")};
    public static int[] GREEN = new int[]{Color.parseColor("#73E8C6"), Color.parseColor("#46D78D"), Color.parseColor("#73E8C6")};
    public static int[] BLUE = new int[]{Color.parseColor("#66D6F1"), Color.parseColor("#55A8F2"), Color.parseColor("#66D6F1")};

    private int colors[] = YELLOW;  //颜色
    private int radius = 150;       //半径
    int maxValue = 100;             //总值
    int curValue = 0;               //当前值
    int arcRadius = 30;             //圆环宽度
    String type = "";               //类型

    public void setData(MyProgressBarBean bean) {
        this.colors = bean.getColors();
        mArcPaint.setShader(new LinearGradient(100, 100, 500, 500, colors, null, Shader.TileMode.CLAMP));
        this.curValue = bean.getValue();
        this.maxValue = bean.getMaxValue();
        this.type = bean.getType();
        this.radius = bean.getRadius();
        this.arcRadius = bean.getArcRadius();
        mCirclePaint.setStrokeWidth(arcRadius);
        mArcPaint.setStrokeWidth(arcRadius);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画圆环
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mCirclePaint);
        //画值和类型
        mCenterTextPaint.setColor(0xFF5B636B);
        mCenterTextPaint.setTextSize(spToPx(14));
        String value = String.valueOf(curValue);
        if (curValue < 0)
            value = "-";
        float valueW = mCenterTextPaint.measureText(value);
        canvas.drawText(value, getWidth() / 2 - valueW / 2, getHeight() / 2 - valueW / 4, mCenterTextPaint);

        float typeW = mCenterTextPaint.measureText(type);
        mCenterTextPaint.setColor(0xFF878E96);
        mCenterTextPaint.setTextSize(spToPx(13));
        canvas.drawText(type, getWidth() / 2 - typeW / 2, getHeight() / 2 + typeW / 4, mCenterTextPaint);
        if (curValue >= 0) {
            canvas.drawArc(rectF, -90, -360 * curValue / maxValue, false, mArcPaint); //sweepAngle 负号为逆时针画
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(w / 2 - radius, h / 2 - radius, w / 2 + radius, h / 2 + radius);
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
