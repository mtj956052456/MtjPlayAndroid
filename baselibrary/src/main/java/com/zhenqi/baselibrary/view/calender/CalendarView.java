package com.zhenqi.baselibrary.view.calender;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

/**
 * 这个是新首页的日历
 * Created by C4BOM on 2018/7/11.
 * GoodLuck No Bug
 */
public class CalendarView extends View {
    private HashMap<Integer, CalendarModel> mHashMap;
    private float screenWidth; //屏幕宽度
    private float screenHeight;//屏幕高度
    private float unitWidth;  //最大单元格的宽度
    private float unitHeigth; //最大单元格的高度

    private Paint linePaint;  //分割线画笔,之后会去掉

    private Paint weekPaint;//日历上方星期标题画笔
    private String weekTitle[] = {"日", "一", "二", "三", "四", "五", "六",};

    private Paint dayPaint;//日子画笔
    private Paint recfWhitePaint;//日子白色画笔
    private Paint dayWhitePaint;//日子白色画笔

    private int year;//当前的年
    private int month;//当前月
    private int day;//当前日

    private RectF mRectF;
    private Paint recfPaint;//绘制圆角矩形画笔
    private float dp8;
    private float dp4;
    private float dp1;
    private float dp2;
    private float sp1;
    private float sp14;
    private float sp18;

    private int startWeek;//这个月第一天是星期几,用来计算点击位置
    private int dayNum;

    private int chooseDay;//设置选中日期
    private Paint choosePaint;//选中日期专用的画笔
    private boolean initValue;//让需要初始化一些因为界面发生变化而要重新计算的数据

    private List<Integer> mColorStartList;
    private List<Integer> mColorEndList;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dp8 = dp2pxF(context, 8);
        dp4 = dp2pxF(context, 4);
        dp1 = dp2pxF(context, 1);
        dp2 = dp2pxF(context, 2);
        sp1 = sp2pxF(context, 1);
        sp14 = sp2pxF(context, 14);
        sp18 = sp2pxF(context, 18);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setColor(0xFF414141);
        weekPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        weekPaint.setTextSize(sp14);
        weekPaint.setColor(0xFF5B636B);

        dayPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dayPaint.setTextSize(sp14);
        dayPaint.setColor(0xFF93979C); //默认日子画笔为这个颜色有需要再去更改

        dayWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dayWhitePaint.setTextSize(sp14);
        dayWhitePaint.setColor(0xFFFFFFFF);

        recfWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        recfWhitePaint.setColor(0xFFF4F6F8);

        startTime();

        mRectF = new RectF();
        recfPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        recfPaint.setColor(0xFFF4F6F8);

        choosePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        choosePaint.setColor(0xFF93979C);//暂时用默认颜色
        initValue = true;//第一次需要初始化数据,后面看情况
        dayChangeFlag = true;
        rectChange = dp8;
        cornerChange = dp8;
        txChange = sp14;
        mHashMap = new LinkedHashMap<>();
        mColorStartList = new ArrayList<>();
        mColorEndList = new ArrayList<>();
        mColorEndList.add(0xFFF4F6F8);
        mColorEndList.add(0xFF43CE17);
        mColorEndList.add(0xFFF9CE02);
        mColorEndList.add(0xFFF1A001);
        mColorEndList.add(0xFFFF401B);
        mColorEndList.add(0xFFC81414);
        mColorEndList.add(0xFFA6134C);

        //mColorStartList.add(0xFFF4F6F8);
        //mColorStartList.add(0xFF78E933);
        //mColorStartList.add(0xFFFFEE48);
        //mColorStartList.add(0xFFF9CE02);
        //mColorStartList.add(0xFFFF743A);
        //mColorStartList.add(0xFFFA3815);
        //mColorStartList.add(0xFFEE184A);

        mColorStartList.add(getResources().getColor(R.color.valueIllegal));
        mColorStartList.add(getResources().getColor(R.color.excellent));
        mColorStartList.add(getResources().getColor(R.color.good));
        mColorStartList.add(getResources().getColor(R.color.mild));
        mColorStartList.add(getResources().getColor(R.color.moderate));
        mColorStartList.add(getResources().getColor(R.color.heavy));
        mColorStartList.add(getResources().getColor(R.color.severe));

    }

    public void setData(HashMap<Integer, CalendarModel> map, int year, int month,int day) {
        if (map != null) {
            mHashMap.clear();
            mHashMap.putAll(map);
            this.year = year;
            this.month = month;
            startWeek = dateToWeek(year, month, day);
            dayNum = getDay(year, month);//获取当期月份有几天
            chooseDay = day;
            dayChangeFlag = true;
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dayChangeFlag = true;//切换viewpager后会从新画
        if (initValue) {
            screenWidth = getWidth();    //获取宽度
            screenHeight = getHeight(); //获取高度
            unitWidth = screenWidth / 7;
            unitHeigth = screenHeight / 7;
        }
        drawTitle(canvas);//绘制标题
        drawDay(canvas);//绘制日期
    }

    private boolean dayChangeFlag;
    private float rectChange;
    private float txChange;
    private float cornerChange;

    private void drawDay(Canvas canvas) {
        int xPosition = startWeek = dateToWeek(year, month, day);
        if (xPosition == 7) {
            xPosition = 0;//周日的时候在初始位置绘制
        }
        int yPosition = 1;
        dayNum = getDay(year, month);//获取当期月份有几天
        for (int i = 0; i < dayNum; i++) {
            if ((i + 1) == chooseDay) {
                txChange += 1;
                rectChange -= 1;
                cornerChange += 0.2f;
                if (txChange > sp18) {
                    txChange = sp18;
                    dayChangeFlag = false;
                }
                mRectF.left = unitWidth * xPosition + rectChange;//绘制矩形
                mRectF.right = unitWidth * (xPosition + 1) - rectChange;
                mRectF.top = unitHeigth * yPosition + rectChange;
                mRectF.bottom = unitHeigth * (yPosition + 1) - rectChange;
                if (mHashMap.get(i) != null) {
                    int lev = mHashMap.get(i).getLev();
                    if (lev > 6) {
                        lev = 6;
                    }
                    int startColor = mColorStartList.get(lev);
                    int endColor = mColorEndList.get(lev);
                    //LinearGradient gradient = new LinearGradient(mRectF.right, mRectF.top, mRectF.left, mRectF.bottom, startColor, endColor, Shader.TileMode.CLAMP);
                    //recfPaint.setShader(gradient); //渐变色
                    recfPaint.setColor(startColor); //原色
                    canvas.drawRoundRect(mRectF, cornerChange, cornerChange, recfPaint);
                    choosePaint.setColor(0xFFFFFFFF);
                } else {
                    canvas.drawRoundRect(mRectF, cornerChange, cornerChange, recfWhitePaint);
                    choosePaint.setColor(0xFF93979C);
                }
                choosePaint.setTextSize(txChange);
                float yOffset = getOffset(choosePaint);
                String value = String.valueOf(i + 1);
                float xOffset = choosePaint.measureText(value);
                canvas.drawText(value, unitWidth / 2 + unitWidth * xPosition - xOffset / 2, unitHeigth / 2 + unitHeigth * yPosition + yOffset, choosePaint);
                canvas.drawCircle(unitWidth / 2 + unitWidth * xPosition, unitHeigth / 2 + unitHeigth * yPosition + yOffset + dp8, dp2, recfWhitePaint);
                xPosition++;
                if (xPosition == 7) {
                    xPosition = 0;
                    yPosition++;
                }
            } else {
                float yOffset = getOffset(dayPaint);
                mRectF.left = unitWidth * xPosition + dp8;//绘制矩形
                mRectF.right = unitWidth * (xPosition + 1) - dp8;
                mRectF.top = unitHeigth * yPosition + dp8;
                mRectF.bottom = unitHeigth * (yPosition + 1) - dp8;
                if (mHashMap.get(i) != null) {
                    int lev = mHashMap.get(i).getLev();
                    if (lev > 6) {
                        lev = 6;
                    }
                    int startColor = mColorStartList.get(lev);
                    int endColor = mColorEndList.get(lev);
                    //LinearGradient gradient = new LinearGradient(mRectF.right, mRectF.top, mRectF.left, mRectF.bottom, startColor, endColor, Shader.TileMode.CLAMP);
                    //recfPaint.setShader(gradient);//渐变色
                    recfPaint.setColor(startColor); //原色
                    canvas.drawRoundRect(mRectF, dp8, dp8, recfPaint);
                    dayPaint.setColor(0xFFFFFFFF);
                } else {
                    canvas.drawRoundRect(mRectF, dp8, dp8, recfWhitePaint);
                    dayPaint.setColor(0xFF93979C);
                }
                String value = String.valueOf(i + 1);
                float xOffset = dayPaint.measureText(value);
                canvas.drawText(value, unitWidth / 2 + unitWidth * xPosition - xOffset / 2, unitHeigth / 2 + unitHeigth * yPosition + yOffset, dayPaint);

                xPosition++;
                if (xPosition == 7) {
                    xPosition = 0;
                    yPosition++;
                }
            }
        }
        if (dayChangeFlag) {
            invalidate();
        } else {
            rectChange = dp8;
            txChange = sp14;
            cornerChange = dp8;
        }
    }

    private void drawTitle(Canvas canvas) {
        float weekOffset = getOffset(weekPaint);

        for (int i = 0; i < 7; i++) {
            String value = weekTitle[i];
            float weekLength = weekPaint.measureText(value);
            canvas.drawText(weekTitle[i], unitWidth / 2 + i * unitWidth - weekLength / 2, unitHeigth / 2 + weekOffset, weekPaint);
        }
    }

    /**
     * 计算把文字放在正中间的上下偏移量
     *
     * @param paint 文字画笔
     * @return 需要的偏移量
     */
    private float getOffset(Paint paint) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (-1) * (fontMetrics.ascent + fontMetrics.descent) / 2;
    }

    /**
     * 初始化时间,以当月为准
     */
    private void startTime() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        chooseDay = 1;
        day = 1;

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


    /**
     * 判断出哪个数需要变化增加,然后打开变化开关
     *
     * @param event
     */
    private void chooseItem(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int column = (int) (x / unitWidth) + 1;//点击在的行
        int row = (int) (y / unitHeigth) + 1;//点击在的列
        int goal = 0;
        if (row == 2 && column > startWeek) {
            goal = column - startWeek;
        } else if (row > 2) {
            goal = (row - 3) * 7 + (7 - startWeek) + column;
        }
        if (goal > 0 && goal <= dayNum) {
            chooseDay = goal;
            dayChangeFlag = true;
            if (mCalendarClickListener != null) {
                mCalendarClickListener.click(goal);
            }
            if (mHashMap != null) {
                if (mHashMap.get(goal - 1) != null) {
                    invalidate();
                }
            }
        }
    }

    private CalendarClickListener mCalendarClickListener;

    public void setCalendarClickListener(CalendarClickListener calendarClickListener) {
        this.mCalendarClickListener = calendarClickListener;
    }

    public interface CalendarClickListener {
        void click(int day);
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < 9; i++) {
            canvas.drawLine(i * unitWidth, 0, i * unitWidth, screenHeight, linePaint);
        }
        for (int i = 0; i < 7; i++) {
            canvas.drawLine(0, i * unitHeigth, screenWidth, i * unitHeigth, linePaint);
        }
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
     * 获取是星期几
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
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
        if (week == 7) {
            week = 0;
        }
        return week;
    }
}
