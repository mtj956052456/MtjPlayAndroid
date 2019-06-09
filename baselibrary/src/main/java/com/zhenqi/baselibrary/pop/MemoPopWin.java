package com.zhenqi.baselibrary.pop;

import android.app.Activity;
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
 * 创建人:孟腾蛟
 * 时间: 2019-05-15
 * 描述:
 */

public class MemoPopWin extends BasePopWindow implements View.OnClickListener {

    private View view;
    private LoopView loopView_year1, loopView_month1, loopView_day1, loopView_year2, loopView_month2, loopView_day2;
    private TextView tv_close, tv_sure;
    private OnMemoListener listener;
    private String year1, month1, day1, year2, month2, day2;


    public MemoPopWin(Activity activity) {
        super(activity);
        initCanTouch();
        addBackView();
    }

    @Override
    protected View SetView() {
        view = LayoutInflater.from(mActivity).inflate(R.layout.memo_oneloopview_pop_layout, null);

        initView();
        initClick();

        /**初始化时间 start */
        String[] front7Date = DateUtil.getBeforeDate(7).split("-");
        year1 = front7Date[0];
        month1 = front7Date[1];
        day1 = front7Date[2];
        String[] NowDate = DateUtil.getBeforeDate(1).split("-");
        year2 = NowDate[0];
        month2 = NowDate[1];
        day2 = NowDate[2];

        initData();

        /**初始化时间 end */
        return view;
    }


    String[] years = new String[]{"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};
    String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] days = null;

    public void initData() {
        //DateUtil.getFront7Date();
        //year1 = strs[0];
        //month1 = strs[1];
        //day1 = strs[2];
        //year2 = strs[3];
        //month2 = strs[4];
        //day2= strs[5];
        //Log.e(TAG, "initData: year1 : " + year1 + " month1 :" + month1 + " day1 :" + day1);
        //Log.e(TAG, "initData: year2 : " + year2 + " month2 :" + month2 + " day2 :" + day2);
        initYearData1();
        initMonthData1();
        initDayData1();
        initYearData2();
        initMonthData2();
        initDayData2();
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

    /**
     * 年份2
     */
    private void initYearData2() {
        List<String> list = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < years.length; i++) {
            list.add(years[i]);
            if (years[i].equals(year2)) {
                position = i;
            }
        }
        loopView_year2.setItems(list); //设置数据
        loopView_year2.setTextSize(16);
        loopView_year2.setNotLoop();
        loopView_year2.setCurrentPosition(position);
    }

    /**
     * 月份2
     */
    private void initMonthData2() {
        List<String> list = new ArrayList<>();
        int position = 0;

        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
            if (months[i].equals(month2)) {
                position = i;
            }
        }
        loopView_month2.setItems(list); //设置数据
        loopView_month2.setTextSize(16);
        loopView_month2.setCurrentPosition(position);
        loopView_month2.setNotLoop();
    }

    /**
     * 天2
     */
    private void initDayData2() {
        List<String> list = new ArrayList<>();
        int position = 0;
        int Alldays = DateUtil.getDays(Integer.parseInt(year2), Integer.parseInt(month2));//获取当月多少天
        for (int i = 1; i <= Alldays; i++) {
            String d = String.valueOf(i);
            if (d.length() < 2) {
                d = String.valueOf("0" + d);
            }
            if (d.equals(day2)) {
                position = i - 1;
            }
            list.add(d);
        }
        days = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            days[i] = list.get(i);
        }
        loopView_day2.setItems(list); //设置数据
        loopView_day2.setTextSize(16);
        loopView_day2.setCurrentPosition(position);
        day2 = list.get(position);
        loopView_day2.setNotLoop();
    }


    private void initClick() {
        tv_close.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        //年 月 日 时 分  的时间监听
        loopView_year1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                year1 = years[index];
                initDayData1();            //每次选中年份后 要重新判断当月的天数  重新初始化
            }
        });
        loopView_month1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                month1 = months[index];
                initDayData1();            //每次选中年份后 要重新判断当月的天数  重新初始化
            }
        });
        loopView_day1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                day1 = days[index];
            }
        });

        loopView_year2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                year2 = years[index];
                initDayData2();            //每次选中年份后 要重新判断当月的天数  重新初始化
            }
        });
        loopView_month2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                month2 = months[index];
                initDayData2();            //每次选中年份后 要重新判断当月的天数  重新初始化
            }
        });
        loopView_day2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                day2 = days[index];
            }
        });
    }


    private void initView() {
        tv_close = (TextView) view.findViewById(R.id.memo_close);
        tv_sure = (TextView) view.findViewById(R.id.memo_sure);
        loopView_year1 = (LoopView) view.findViewById(R.id.memo_year_one_loopView);
        loopView_month1 = (LoopView) view.findViewById(R.id.memo_month_one_loopView);
        loopView_day1 = (LoopView) view.findViewById(R.id.memo_day_one_loopView);
        loopView_year2 = (LoopView) view.findViewById(R.id.memo_year_two_loopView);
        loopView_month2 = (LoopView) view.findViewById(R.id.memo_month_two_loopView);
        loopView_day2 = (LoopView) view.findViewById(R.id.memo_day_two_loopView);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.memo_sure) {
            dismiss();
            listener.time(year1, month1, day1, year2, month2, day2);
        } else if (i == R.id.memo_close) {
            dismiss();
        }
    }


    public interface OnMemoListener {
        void time(String year1, String month1, String day1, String year2, String month2, String day2);
    }

    public void setOnMemoListener(OnMemoListener listener) {
        this.listener = listener;
    }
}
