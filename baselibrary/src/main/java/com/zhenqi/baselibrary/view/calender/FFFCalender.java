package com.zhenqi.baselibrary.view.calender;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.zhenqi.baselibrary.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by C4BOM on 2018/4/28.
 * GoodLuck No Bug
 */
public class FFFCalender extends View {
    private Paint backPaint;  //背景画笔
    private Paint linePaint;  //线条画笔

    private float widthScale; //屏幕七分宽度
    private float heigthScale;//日历每行的高度
    private Paint weekendPaint; //周几画笔 FFF6F6F6
    private Paint dayPaint;   //日子画笔
    private Paint memoPaint;  //备注画笔
    private int year;
    private int month;
    private int day;

    private float xLint; //绘制过程中的x标准线
    private float yLint;  //绘制过程中的y标准线
    private int firstDayWeek;

    private String[] weekends = {"日", "一", "二", "三", "四", "五", "六"};
    private List<CalenderBean> mList;

    public FFFCalender(Context context) {
        this(context, null);
    }

    public FFFCalender(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FFFCalender(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        backPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mList = new ArrayList<>();
        initLine();
        initDpSp(context);
        startTime();
        getValue(context, attrs);
    }

    private void initLine() {
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFFECECEC);
    }

    private int dp16;
    private int dp4;

    private void initDpSp(Context context) {
        heigthScale = 0;
        firstDayWeek = 0;
        dp16 = dp2px(context, 16);
        dp4 = dp2px(context, 14);
    }

    public void setData(int showYear, int showMonth, List<CalenderBean> list) {
        if (list!=null&&list.size()!=0){
            year = showYear;
            month = showMonth;
            mList.clear();
            mList.addAll(list);
            invalidate();
        }else {
            mList.clear();
            invalidate();
        }
    }

    /**
     * 初始化时间
     */
    private void startTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = 1;
    }

    /**
     * 获取资源文件
     */
    private void getValue(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FFFCalender);
        Weekend(typedArray, context);
        Day(typedArray, context);
        Memo(typedArray, context);
        typedArray.recycle();
        setBackgroundColor(0xFFFFFFFF);
    }

    /**
     * 初始化当日备注
     */
    private void Memo(TypedArray typedArray, Context context) {
        int memoColor = typedArray.getColor(R.styleable.FFFCalender_memoColor, 0xFFA4A4A4);
        float memoSize = typedArray.getDimension(R.styleable.FFFCalender_memoSize, sp2px(context, 12));
        memoPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        memoPaint.setColor(memoColor);
        memoPaint.setTextSize(memoSize);
    }

    /**
     * 初始化和天相关的数据
     */
    private void Day(TypedArray typedArray, Context context) {
        int dayColor = typedArray.getColor(R.styleable.FFFCalender_dayColor, 0xFF424242);
        float daySize = typedArray.getDimension(R.styleable.FFFCalender_daySize, sp2px(context, 14));
        dayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dayPaint.setColor(dayColor);
        dayPaint.setTextSize(daySize);
    }

    /**
     * 初始化星期文字画笔
     */
    private float weekBarHeight;
    private int weekBarColor;

    private void Weekend(TypedArray typedArray, Context context) {
        float weekSize = typedArray.getDimension(R.styleable.FFFCalender_weekSize, sp2px(context, 16));
        int weekColor = typedArray.getColor(R.styleable.FFFCalender_weekColor, 0xFF414141);
        weekBarHeight = typedArray.getDimension(R.styleable.FFFCalender_weekBarHeight, dp2pxF(context, 48));
        weekBarColor = typedArray.getColor(R.styleable.FFFCalender_weekBarColor, 0xFFF6F6F6);
        weekendPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        weekendPaint.setTextSize(weekSize);
        weekendPaint.setColor(weekColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        widthScale = ((float) getWidth()) / 7;

        backPaint.setColor(weekBarColor);
        canvas.drawRect(0, 0, getRight(), weekBarHeight, backPaint);
        Paint.FontMetrics fontMetrics = weekendPaint.getFontMetrics();
        for (int i = 0; i < 7; i++) {
            float weekLength = weekendPaint.measureText(weekends[i]);
            canvas.drawText(weekends[i], widthScale / 2 - weekLength / 2 + i * widthScale, weekBarHeight / 2 + (fontMetrics.descent - fontMetrics.ascent) / 2, weekendPaint);
        }
        xLint = weekBarHeight;
        /**
         * 获取星期几
         */
        int dateToWeek = dateToWeek(year, month, day);
        if (dateToWeek == 7) {
            dateToWeek = 0;
        }
        firstDayWeek = dateToWeek;
        /**
         * 获取一个月有几天
         */
        int dayNum = getDay(year, month);
        Paint.FontMetrics dayMetrics1 = dayPaint.getFontMetrics();
        Paint.FontMetrics memoMetrics1 = memoPaint.getFontMetrics();
        heigthScale = (dayMetrics1.descent - dayMetrics1.ascent) / 2 + dp16 + dp4 + (memoMetrics1.descent - memoMetrics1.ascent) / 2 + dp16;
        for (int i = 1; i <= dayNum; i++) {
            String day = String.valueOf(i);
            String memo = "";
            if (mList.size() != 0) {
                memo = mList.get(i).getMemo();
            }
            float dayLength = dayPaint.measureText(day);
            float memoLength = memoPaint.measureText(memo);

            if (dateToWeek < 7) {
                yLint = widthScale / 2 + dateToWeek * widthScale;
                dateToWeek++;
            } else {
                yLint = widthScale / 2 - dayLength / 2;
                xLint = xLint + heigthScale;
                canvas.drawLine(0, xLint, getRight(), xLint, linePaint);
                dateToWeek = 1;
            }
            canvas.drawText(day, yLint - dayLength / 2, xLint + dp16 + (dayMetrics1.descent - dayMetrics1.ascent) / 2, dayPaint);
            canvas.drawText(memo, yLint - memoLength / 2, xLint + dp16 + (dayMetrics1.descent - dayMetrics1.ascent) / 2 + dp4 + (memoMetrics1.descent - memoMetrics1.ascent) / 2, memoPaint);
        }
    }

    /**
     * 当点击的时候可以触发onClickListener()
     */
    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                performClick();
                chooseItem(event);
                return true;
            default:
                return super.onTouchEvent(event);
        }
    }

    private void chooseItem(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int xRadio = (int) (x / widthScale) + 1;
        int yRadio = (int) (y / heigthScale);
        int goal = (yRadio - 1) * 7 + xRadio - firstDayWeek;
        if (null != mItemClickListener) {
            if (goal > 0 && goal < mList.size()) {
                mItemClickListener.click(goal, mList.get(goal));
            } else {
                mItemClickListener.onValue("不存在该位置的数据");
            }
        }
    }

    private ItemClickListener mItemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void click(int day, CalenderBean calenderBean);

        void onValue(String errMsg);
    }

    //工具类
    public static int dp2px(Context c, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context c, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    public static float dp2pxF(Context c, float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
    }

    public static float sp2pxF(Context c, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, c.getResources().getDisplayMetrics());
    }

    /**
     * 获取当月的天数
     */
    private int getDay(int year, int month) {
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
            return 31;
        } else if (month == 2) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return 28;
            }
        } else {
            return 30;
        }
    }

    /**
     * 判断是否是闰年
     */
    private boolean isLeapYear(int year) {
        return year % 400 == 0 || year % 100 != 0 && year % 4 == 0;
    }

    /**
     * 日期转星期
     */
    private int dateToWeek(int year, int month, int day) {
        String monthString = "";
        if (month < 10) {
            monthString = "0" + month;
        } else {
            monthString = String.valueOf(month);
        }
        String dayString = "";
        if (day < 10) {
            dayString = "0" + day;
        } else {
            dayString = String.valueOf(day);
        }
        String time = year + "-" + monthString + "-" + dayString;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance(); // 获得一个日历
        Date date = null;
        try {
            date = f.parse(time);
            cal.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cal.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else {
            week = week - 1;
        }
        return week;
    }
}
