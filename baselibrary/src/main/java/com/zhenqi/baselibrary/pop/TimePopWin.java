package com.zhenqi.baselibrary.pop;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;
import com.zhenqi.baselibrary.R;
import com.zhenqi.baselibrary.base.BasePopWindow;
import com.zhenqi.baselibrary.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/29
 * 描述:
 */
public class TimePopWin extends BasePopWindow implements View.OnClickListener {

    private View view;
    private LoopView loopView_year1, loopView_month1, loopView_day1;
    private TextView tv_close, tv_sure;
    private OnMemoListener listenerThree;
    private OnMemoTwoListener listenerTwo;
    private OnMemoOneListener listenerOne;
    private String year1, month1, day1;

    public static final int yyyyMMdd = 0; //年月日
    public static final int yyyyMM = 1;   //年月
    public static final int MMdd = 2;     //月日
    public static final int yyyy = 3;     //年
    public static final int MM = 4;       //月
    public static final int dd = 5;       //日

    private int type = 0; //默认是年月日
    private String initTime;

    /**
     *
     * @param activity
     * @param type
     * @param initTime  初始化时间  格式为 yyyy-MM-dd
     */
    public TimePopWin(Activity activity, int type, String initTime) {
        super(activity);
        this.type = type;
        this.initTime = initTime;
        initCanTouch();
        addBackView();
    }

    @Override
    protected View SetView() {
        view = LayoutInflater.from(mActivity).inflate(R.layout.time_one_pop_layout, null);

        initView();
        initClick();

        /**初始化时间 start */
        if (TextUtils.isEmpty(initTime))
            initTime = DateUtil.getBeforeDate(1);
        String[] NowDate = initTime.split("-");
        year1 = NowDate[0];
        month1 = NowDate[1];
        day1 = NowDate[2];

        initData();

        /**初始化时间 end */
        return view;
    }


    String[] years = new String[]{"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};
    String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] days = null;

    public void initData() {
        initYearData1();
        initMonthData1();
        initDayData1();
    }


    /**
     * 年份1
     */
    private void initYearData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < years.length; i++) {
            list.add(years[i]);
            if (years[i].equals(year1)) {
                position = i;
            }
        }
        loopView_year1.setItems(list); //设置数据
        loopView_year1.setTextSize(16);
        loopView_year1.setNotLoop();
        loopView_year1.setCurrentPosition(position);
    }

    /**
     * 月份1
     */
    private void initMonthData1() {
        List<String> list = new ArrayList<>();
        int position = 0;

        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
            if (months[i].equals(month1)) {
                position = i;
            }
        }
        loopView_month1.setItems(list); //设置数据
        loopView_month1.setTextSize(16);
        loopView_month1.setCurrentPosition(position);
        loopView_month1.setNotLoop();
    }

    /**
     * 天1
     */
    private void initDayData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        int Alldays = DateUtil.getDays(Integer.parseInt(year1), Integer.parseInt(month1));//获取当月多少天
        for (int i = 1; i <= Alldays; i++) {
            String d = String.valueOf(i);
            if (d.length() < 2) {
                d = String.valueOf("0" + d);
            }
            if (d.equals(day1)) {
                position = i - 1;
            }
            list.add(d);
        }
        days = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            days[i] = list.get(i);
        }
        loopView_day1.setItems(list); //设置数据
        loopView_day1.setTextSize(16);
        loopView_day1.setCurrentPosition(position);
        day1 = list.get(position);
        loopView_day1.setNotLoop();
    }


    private void initClick() {
        tv_close.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        //年 月 日 时 分  的时间监听
        loopView_year1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                year1 = years[index];
                initDayData1();            //每次选中年份后 要从新判断当月的天数  从新初始化
            }
        });
        loopView_month1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                month1 = months[index];
                initDayData1();            //每次选中年份后 要从新判断当月的天数  从新初始化
            }
        });
        loopView_day1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                day1 = days[index];
            }
        });
    }


    private void initView() {
        tv_close = (TextView) view.findViewById(R.id.memo_close);
        tv_sure = (TextView) view.findViewById(R.id.memo_sure);
        loopView_year1 = (LoopView) view.findViewById(R.id.memo_year_one_loopView);
        loopView_month1 = (LoopView) view.findViewById(R.id.memo_month_one_loopView);
        loopView_day1 = (LoopView) view.findViewById(R.id.memo_day_one_loopView);
        if (type == TimePopWin.yyyyMM) {
            loopView_day1.setVisibility(View.GONE);
        } else if (type == TimePopWin.MMdd) {
            loopView_year1.setVisibility(View.GONE);
        } else if (type == TimePopWin.yyyy) {
            loopView_month1.setVisibility(View.GONE);
            loopView_day1.setVisibility(View.GONE);
        } else if (type == TimePopWin.MM) {
            loopView_year1.setVisibility(View.GONE);
            loopView_day1.setVisibility(View.GONE);
        } else if (type == TimePopWin.dd) {
            loopView_year1.setVisibility(View.GONE);
            loopView_month1.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.memo_sure) {
            if (type == TimePopWin.yyyyMMdd) {
                listenerThree.time(year1, month1, day1);
            }
            if (type == TimePopWin.yyyyMM) {
                listenerTwo.time(year1, month1);
            } else if (type == TimePopWin.MMdd) {
                listenerTwo.time(month1, day1);
            }
            if (type == TimePopWin.yyyy) {
                listenerOne.time(year1);
            } else if (type == TimePopWin.MM) {
                listenerOne.time(month1);
            } else if (type == TimePopWin.dd) {
                listenerOne.time(day1);
            }
            dismiss();
        } else if (id == R.id.memo_close) {
            dismiss();
        }
    }


    public interface OnMemoListener {
        void time(String year1, String month1, String day1);//年月日
    }

    public void setOnMemoListener(OnMemoListener listener) {
        this.listenerThree = listener;
    }

    public interface OnMemoTwoListener {
        void time(String year1, String month1);             //年月  月日
    }

    public void setOnMemoTwoListener(OnMemoTwoListener listener) {
        this.listenerTwo = listener;
    }

    public interface OnMemoOneListener {
        void time(String year1);                            //年 月 日
    }

    public void setOnMemoOneListener(OnMemoOneListener listener) {
        this.listenerOne = listener;
    }

}
