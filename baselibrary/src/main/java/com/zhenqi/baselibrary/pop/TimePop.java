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
 * 创建者: 孟腾蛟
 * 时间: 2019/4/29
 * 描述:
 */
public class TimePop extends BasePopWindow implements View.OnClickListener {

    private View view;
    private LoopView memo_loopView;
    private TextView tv_close, tv_sure, tv_type;
    private OnBackListener listener;
    private String chooseYear, chooseMonth, chooseDay, chooseHour;
    private String nowYear, nowMonth, nowDay, nowHour;

    public enum TimeType {
        HOUR, DAY, MONTH, YEAR
    }

    private TimeType mTimeType;

    public TimePop(Activity activity, TimeType mTimeType) {
        super(activity);
        this.mTimeType = mTimeType;
        initCanTouch();
        addBackView();
    }

    @Override
    protected View SetView() {
        view = LayoutInflater.from(mActivity).inflate(R.layout.time_pop_layout, null);

        initView();
        initClick();

        /**初始化时间 start */
        String[] nowDate = DateUtil.getNowyyyyMMddHHTime().split("-");
        chooseYear = nowDate[0];
        chooseMonth = nowDate[1];
        chooseDay = nowDate[2];
        chooseHour = nowDate[3];

        nowYear = nowDate[0];
        nowMonth = nowDate[1];
        nowDay = nowDate[2];
        nowHour = nowDate[3];

        initData();

        /**初始化时间 end */
        return view;
    }


    private String[] years = new String[]{"2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023", "2024"};
    private String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] days = null;
    private String[] hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
            , "11", "12", "13", "14", "15", "16", "17", "18", "19", "10"
            , "21", "22", "23"};


    public void initData() {
        if (mTimeType == TimeType.HOUR) {
            tv_type.setText("时");
            initHourData1();
        } else if (mTimeType == TimeType.DAY) {
            tv_type.setText("日");
            initDayData1();
        } else if (mTimeType == TimeType.MONTH) {
            tv_type.setText("月");
            initMonthData1();
        } else if (mTimeType == TimeType.YEAR) {
            tv_type.setText("年");
            initYearData1();
        }
    }


    /**
     * 年份1
     */
    private void initYearData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < years.length; i++) {
            list.add(years[i]);
            if (years[i].equals(chooseYear)) {
                position = i;
            }
        }
        memo_loopView.setItems(list); //设置数据
        memo_loopView.setTextSize(16);
        memo_loopView.setNotLoop();
        memo_loopView.setCurrentPosition(position);
    }

    /**
     * 月份1
     */
    private void initMonthData1() {
        List<String> list = new ArrayList<>();
        int position = 0;

        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
            if (months[i].equals(chooseMonth)) {
                position = i;
            }
        }
        memo_loopView.setItems(list); //设置数据
        memo_loopView.setTextSize(16);
        memo_loopView.setCurrentPosition(position);
        memo_loopView.setNotLoop();
    }

    /**
     * 天1
     */
    private void initDayData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        int Alldays = DateUtil.getDays(Integer.parseInt(chooseYear), Integer.parseInt(chooseMonth));//获取当月多少天
        for (int i = 1; i <= Alldays; i++) {
            String d = String.valueOf(i);
            if (d.length() < 2) {
                d = String.valueOf("0" + d);
            }
            if (d.equals(chooseDay)) {
                position = i - 1;
            }
            list.add(d);
        }
        days = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            days[i] = list.get(i);
        }
        memo_loopView.setItems(list); //设置数据
        memo_loopView.setTextSize(16);
        memo_loopView.setCurrentPosition(position);
        chooseDay = list.get(position);
        memo_loopView.setNotLoop();
    }

    /**
     * 小时1
     */
    private void initHourData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < hours.length; i++) {
            list.add(hours[i]);
            if (hours[i].equals(chooseHour)) {
                position = i;
            }
        }
        memo_loopView.setItems(list); //设置数据
        memo_loopView.setTextSize(16);
        memo_loopView.setNotLoop();
        memo_loopView.setCurrentPosition(position);

    }

    private void initClick() {
        tv_close.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        //年 月 日 时 分  的时间监听
        if (mTimeType == TimeType.HOUR) {
            memo_loopView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    chooseHour = hours[index];
                }
            });
        } else if (mTimeType == TimeType.DAY) {
            memo_loopView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    chooseDay = days[index];
                }
            });
        } else if (mTimeType == TimeType.MONTH) {
            memo_loopView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    chooseMonth = months[index];
                }
            });
        } else if (mTimeType == TimeType.YEAR) {
            memo_loopView.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    chooseYear = years[index];
                }
            });
        }
    }


    private void initView() {
        tv_close = (TextView) view.findViewById(R.id.memo_close);
        tv_sure = (TextView) view.findViewById(R.id.memo_sure);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        memo_loopView = (LoopView) view.findViewById(R.id.memo_loopView);
    }


    @Override
    public void onClick(View v) {
        dismiss();
        int id = v.getId();
        if (id == R.id.memo_sure) {
            if (mTimeType == TimeType.HOUR) {
                listener.time(chooseHour);
            } else if (mTimeType == TimeType.DAY) {
                listener.time(chooseDay);
            } else if (mTimeType == TimeType.MONTH) {
                listener.time(chooseMonth);
            } else if (mTimeType == TimeType.YEAR) {
                listener.time(chooseYear);
            }
        } else {
            dismiss();
        }
    }

    public interface OnBackListener {
        void time(String tiem);
    }

    public void setOnBackListener(OnBackListener listener) {
        this.listener = listener;
    }
}
