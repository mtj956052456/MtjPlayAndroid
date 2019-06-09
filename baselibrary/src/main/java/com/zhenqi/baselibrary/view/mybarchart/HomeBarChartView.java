package com.zhenqi.baselibrary.view.mybarchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 创建人:孟腾蛟
 * 时间: 2019/5/9
 * 描述: 柱状图
 */
public class HomeBarChartView extends View {

    private Scroller mScroller;

    private Paint mTextPaint;   //文字画笔

    private Paint mTextPopPaint; //弹窗文字画笔

    private Paint mLinePaint; //底线画笔

    private Paint mRectPaint;   //文字边框画笔


    private VelocityTracker velocityTracker; //速度跟踪器

    private int mMaxX;  //画板的总宽

    private int offsetX = dpToPx(40); //偏移量


    public HomeBarChartView(Context context) {
        super(context);
        init(context);
    }

    public HomeBarChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeBarChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mScroller = new Scroller(context);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(spToPx(11));
        mTextPaint.setColor(0xFF858E96);

        mTextPopPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPopPaint.setTextSize(spToPx(12));
        mTextPopPaint.setColor(0xFFFFFFFF);

        mRectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mRectPaint.setColor(0xFF4E93FF);

        mLinePaint = new Paint();
        mLinePaint.setColor(0xFFF4F6F8);
        mLinePaint.setTextSize(spToPx(11));
        mLinePaint.setStrokeWidth(2);

        textRect = new Rect();
    }

    private List<HomeBarChartBean> mList = new ArrayList<>(); //数据源
    private String curCity;
    private String type;

    /**
     * 设置类型
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    //设置数据源
    public void setData(List<HomeBarChartBean> mList, String curCity) {
        this.mList = mList;
        this.curCity = curCity;
        initMaxValue(new ArrayList<>(mList));
        invalidate();
    }

    private float mMaxValue = 10; //最大问题数

    //获取Y最大范围
    private void initMaxValue(List<HomeBarChartBean> list) {
        if (list.isEmpty())
            return;
        Collections.sort(list, new Comparator<HomeBarChartBean>() {
            @Override
            public int compare(HomeBarChartBean o1, HomeBarChartBean o2) {
                if (o1.getNum() > o2.getNum())
                    return 1;
                else if (o1.getNum() < o2.getNum())
                    return -1;
                else
                    return 0;
            }
        });
        mMaxValue = (list.get(list.size() - 1).getNum() / 5 + 1) * 5; //临近最大整数
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mList == null)
            return;
        if (mList.size() == 0) {
            drawNoData(canvas);
            return;
        } else {
            mTextPaint.setTextSize(spToPx(11));
        }
        mMaxX = offsetX * mList.size() - getWidth();
        YHeight = getHeight() - YBottomDistance;
        //mRectW = getWidth()/5;//按需求
        offsetX = getWidth() / 5;//按需求
        drawXLine(canvas);
    }

    /**
     * 暂无数据
     *
     * @param canvas
     */
    private void drawNoData(Canvas canvas) {
        mTextPaint.setColor(0xFFFF401A);
        mTextPaint.setTextSize(spToPx(16));
        canvas.drawText("暂无数据", getWidth() / 2 - 80, getHeight() / 2 + 40, mTextPaint);
    }


    private int YHeight;
    private int Rectradius = 60;
    private int YBottomDistance = dpToPx(12); //距离X轴以下的距离
    private int pTop = dpToPx(60);            //显示框预留的高度

    private int mRectW = 60;//柱子宽度
    private int dp20 = dpToPx(20);
    private int textPadding = 20;   //文字的内左右内边距
    private int tipsHeightOffset = dpToPx(5);   //提示框距离柱子的高度
    private RectF rectF = new RectF();
    private Rect textRect;

    private void drawXLine(Canvas canvas) {
        int lineLength = offsetX * mList.size() + offsetX / 2;
        if (lineLength < getWidth())
            lineLength = getWidth();
        canvas.drawLine(0, YHeight, lineLength, YHeight, mLinePaint); //画X线
        int left, top, right, bottom;
        for (int i = 0; i < mList.size(); i++) {
            //画文字的框
            left = offsetX * i + dp20 / 2 + offsetX / 4;
            right = offsetX * i + dp20 / 2 + mRectW + offsetX / 4;
            bottom = YHeight;
            //高度 - 每个值的像素比*问题个数 - 距离底部的距离
            float num = mList.get(i).getNum();
            String sNum;
            if ("CO".equals(type)||"综合指数".equals(type)) {
                sNum = String.valueOf(num);
            } else {
                sNum = String.valueOf((int) num);
            }
            int YDistance;
            if (num != 0) {
                YDistance = (int) (YHeight - (YHeight - pTop) * num / mMaxValue);
            } else {
                YDistance = YHeight;
            }

            //测量文字的宽高
            mTextPaint.getTextBounds(sNum, 0, sNum.length(), textRect);
            int width = textRect.width();
            int height = textRect.height();
            //提示框的范围
            rectF.left = left;
            rectF.top = YDistance - height * 2 - tipsHeightOffset;
            rectF.right = left + width + textPadding * 2;
            rectF.bottom = YDistance - tipsHeightOffset;

            if (curCity.equals(mList.get(i).getCity())) {//当前城市
                mRectPaint.setColor(0xFF5BB8FF);
                mTextPaint.setColor(0xFF5BB8FF);
                //画提示框
                canvas.drawRoundRect(rectF, 30, 30, mTextPaint); //画圆角矩形
                canvas.drawRect(left, YDistance - height - tipsHeightOffset, left + width / 2 + textPadding, YDistance - tipsHeightOffset, mTextPaint);//画左下角的小正方形
                canvas.drawText(sNum, left + textPadding * 2 / 3, YDistance - height / 2 - tipsHeightOffset, mTextPopPaint); //画值
            } else if (mScroller.getFinalX() + lastX > left - offsetX / 4 && mScroller.getFinalX() + lastX < right + offsetX / 4 && clickFlag) { //点击的地方
                mTextPaint.setColor(0xFF71DE95);
                mRectPaint.setColor(0xFF71DE95);
                //画提示框
                canvas.drawRoundRect(rectF, 30, 30, mTextPaint); //画圆角矩形
                canvas.drawRect(left, YDistance - height - tipsHeightOffset, left + width / 2 + textPadding, YDistance - tipsHeightOffset, mTextPaint);//画左下角的小正方形
                canvas.drawText(sNum, left + textPadding * 2 / 3, YDistance - height / 2 - tipsHeightOffset, mTextPopPaint); //画值
            } else {
                mRectPaint.setColor(0xFF71DE95);
                mTextPaint.setColor(0xFF858E96);
            }

            //画圆柱
            if (bottom - YDistance > 0)
                canvas.drawRoundRect(new RectF(left, YDistance, right, bottom), Rectradius / 2, Rectradius / 2, mRectPaint);//画圆角矩形
            if (bottom - YDistance <= Rectradius)
                canvas.drawRect(left, YDistance + (bottom - YDistance) / 2, right, bottom, mRectPaint); //画柱子 往下画20像素 圆角的距离
            else {
                canvas.drawRect(left, YDistance + Rectradius / 2, right, bottom, mRectPaint); //画柱子 往下画20像素 圆角的距离
            }
            float cityWidth = mTextPaint.measureText(mList.get(i).getCity());
            canvas.drawText(mList.get(i).getCity(), mRectW / 2 - cityWidth / 2 + left, bottom + YBottomDistance - 5, mTextPaint);
        }
    }

    int currentX;
    int distanceX;
    private int lastX, startX;
    private int lastY, startY;

    private boolean clickFlag;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        obtainVelocityTracker(event);
        getParent().requestDisallowInterceptTouchEvent(true);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                startX = (int) event.getX();//记录点击的坐标 用于判断点击事件的范围
                startY = (int) event.getY(); //用于判断上下滑动处理
                clickFlag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                //if (mMaxX < getWidth()) { //不满屏幕 不可滑动
                if (offsetX * mList.size() < getWidth()) { //不满屏幕 不可滑动
                    break;
                }
                //计算出两次动作间的滑动距离
                currentX = (int) event.getX();
                lastY = (int) event.getY();

                distanceX = currentX - lastX;       //手指移动的距离 = 移动时的X坐标 - 第一次按下去的坐标
                distanceX = distanceX * -1;         //手指移动的距离值左右相反
                lastX = currentX;                   //记录最后滑动时的X坐标(否则长按轻轻滑动 会飞的)
                smoothScrollBy(distanceX, 0);   //移动到多少距离
                clickFlag = false;
                //                if (mScroller.getFinalX() == mMaxX) { //滑到最右边失去焦点
                //                    getParent().requestDisallowInterceptTouchEvent(false);
                //                }
                //Lg.e("X : " + (Math.abs(lastY) - Math.abs(startY)) + "  Y :" + (Math.abs(lastX) - Math.abs(startX)));
                //Lg.e("Math.abs(lastY) - Math.abs(startY)");

//                if (Math.abs(Math.abs(lastY) - Math.abs(startY)) > 50 && Math.abs(lastX) - Math.abs(startX) < 50) { //上下滑动的距离超过20像素 视为上下滑动 不处理左右滑动事件
//                    getParent().requestDisallowInterceptTouchEvent(false);
//                }

                //                if (Math.abs(lastX) - Math.abs(startX) > 50) {
                //                    getParent().requestDisallowInterceptTouchEvent(true);
                //                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (lastX == startX) { //从手指按下到起来  还是一个点的距离视为点击事件
                    clickFlag = true;
                    invalidate();
                    break;
                }
                velocityTracker.computeCurrentVelocity(1000); //获取速度前要执行此方法 一般值为1000
                //计算速率
                int velocityX = (int) velocityTracker.getXVelocity() * -1;  //横向滑动的速度
                //如果速率大于最小速率要求，执行滑行，否则拖动到位置
                if (Math.abs(velocityX) > 500) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                    fling(velocityX, 0);
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    /**
     * 根据瞬时速度，让画布滑行
     *
     * @param velocityX X轴速度，有正负方向，正数画布左移
     * @param velocityY
     */
    protected void fling(int velocityX, int velocityY) {
        //最后两个是参数是允许的超过边界值的距离
        mScroller.fling(mScroller.getFinalX(), 0, velocityX, 0, 0, mMaxX, 0, 0);
        //invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        //Log.e("MTJ", "changed: " + changed + "  left: " + left + "  top: " + top + "  right: " + right + "   bottom: " + bottom);
        super.onLayout(changed, left, top, right, bottom);
    }

    //调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        //设置mScroller的滚动偏移量
        if (mScroller.getFinalX() + dx > mMaxX) {  //越界恢复,如果超出界面的时候,则回弹
            dx = mMaxX - mScroller.getFinalX();
        } else if (mScroller.getFinalX() + dx < 0) {
            dx = -1 * mScroller.getFinalX();       //如果会拉越界的时候,回弹
        }
        mScroller.startScroll(mScroller.getFinalX(), 0, dx, 0, 500);
        invalidate();//这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {
        //先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        super.computeScroll();
    }


    /**
     * 获取速度跟踪器
     *
     * @param event
     */
    private void obtainVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    /**
     * 回收速度跟踪器
     */
    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
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