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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/29
 * 描述:
 */
public class TimeMdHPopWin extends BasePopWindow implements View.OnClickListener {

    private View view;
    private LoopView loopView_chooseMonth1, loopView_chooseDay1, loopView_chooseHour1, loopView_chooseMonth2, loopView_chooseDay2, loopView_chooseHour2;
    private TextView tv_close, tv_sure;
    private OnBackListener listener;
    private String chooseMonth1, chooseDay1, chooseHour1, chooseMonth2, chooseDay2, chooseHour2;


    public TimeMdHPopWin(Activity activity) {
        super(activity);
        initCanTouch();
        addBackView();
    }

    @Override
    protected View SetView() {
        view = LayoutInflater.from(mActivity).inflate(R.layout.time_mdh_pop_layout, null);

        initView();
        initClick();

        /**初始化时间 start */
        long st = System.currentTimeMillis() - 24 * (60 * 60 * 1000);  //开始时间为昨天上一个小时的时间
        long et = System.currentTimeMillis() - 60 * 60 * 1000;         //结束时间为上一个小时的时间
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-HH");
        String startTime = sdf.format(new Date(st));
        String endTime = sdf.format(new Date(et));
        String[] nowDate1 = startTime.split("-");
        String[] nowDate2 = endTime.split("-");
        chooseMonth1 = nowDate1[0];
        chooseDay1 = nowDate1[1];
        chooseHour1 = nowDate1[2];
        chooseMonth2 = nowDate2[0];
        chooseDay2 = nowDate2[1];
        chooseHour2 = nowDate2[2];

        initData();

        /**初始化时间 end */
        return view;
    }

    String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] days = null;
    String[] hours = new String[]{"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10"
            , "11", "12", "13", "14", "15", "16", "17", "18", "19", "10"
            , "21", "22", "23"};

    public void initData() {
        initMonthData1();
        initDayData1();
        initHourData1();
        initMonthData2();
        initDayData2();
        initHourData2();
    }

    /**
     * 月份1
     */
    private void initMonthData1() {
        List<String> list = new ArrayList<>();
        int position = 0;

        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
            if (months[i].equals(chooseMonth1)) {
                position = i;
            }
        }
        loopView_chooseMonth1.setItems(list); //设置数据
        loopView_chooseMonth1.setTextSize(16);
        loopView_chooseMonth1.setCurrentPosition(position);
        loopView_chooseMonth1.setNotLoop();
    }

    /**
     * 天1
     */
    private void initDayData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        int Alldays = DateUtil.getDays(DateUtil.getThisYear(), Integer.parseInt(chooseMonth1));//获取当月多少天
        for (int i = 1; i <= Alldays; i++) {
            String d = String.valueOf(i);
            if (d.length() < 2) {
                d = String.valueOf("0" + d);
            }
            if (d.equals(chooseDay1)) {
                position = i - 1;
            }
            list.add(d);
        }
        days = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            days[i] = list.get(i);
        }
        loopView_chooseDay1.setItems(list); //设置数据
        loopView_chooseDay1.setTextSize(16);
        loopView_chooseDay1.setCurrentPosition(position);
        chooseDay1 = list.get(position);
        loopView_chooseDay1.setNotLoop();
    }

    /**
     * 小时1
     */
    private void initHourData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < hours.length; i++) {
            list.add(hours[i]);
            if (hours[i].equals(chooseHour1)) {
                position = i;
            }
        }
        loopView_chooseHour1.setItems(list); //设置数据
        loopView_chooseHour1.setTextSize(16);
        loopView_chooseHour1.setNotLoop();
        loopView_chooseHour1.setCurrentPosition(position);

    }

    /**
     * 月份1
     */
    private void initMonthData2() {
        List<String> list = new ArrayList<>();
        int position = 0;

        for (int i = 0; i < months.length; i++) {
            list.add(months[i]);
            if (months[i].equals(chooseMonth2)) {
                position = i;
            }
        }
        loopView_chooseMonth2.setItems(list); //设置数据
        loopView_chooseMonth2.setTextSize(16);
        loopView_chooseMonth2.setCurrentPosition(position);
        loopView_chooseMonth2.setNotLoop();
    }

    /**
     * 天1
     */
    private void initDayData2() {
        List<String> list = new ArrayList<>();
        int position = 0;
        int Alldays = DateUtil.getDays(DateUtil.getThisYear(), Integer.parseInt(chooseMonth2));//获取当月多少天
        for (int i = 1; i <= Alldays; i++) {
            String d = String.valueOf(i);
            if (d.length() < 2) {
                d = String.valueOf("0" + d);
            }
            if (d.equals(chooseDay2)) {
                position = i - 1;
            }
            list.add(d);
        }
        days = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            days[i] = list.get(i);
        }
        loopView_chooseDay2.setItems(list); //设置数据
        loopView_chooseDay2.setTextSize(16);
        loopView_chooseDay2.setCurrentPosition(position);
        chooseDay2 = list.get(position);
        loopView_chooseDay2.setNotLoop();
    }

    /**
     * 小时1
     */
    private void initHourData2() {
        List<String> list = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < hours.length; i++) {
            list.add(hours[i]);
            if (hours[i].equals(chooseHour2)) {
                position = i;
            }
        }
        loopView_chooseHour2.setItems(list); //设置数据
        loopView_chooseHour2.setTextSize(16);
        loopView_chooseHour2.setNotLoop();
        loopView_chooseHour2.setCurrentPosition(position);

    }


    private void initClick() {
        tv_close.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        //月 日 时 分  的时间监听
        loopView_chooseMonth1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                chooseMonth1 = months[index];
                initDayData1();            //每次选中年份后 要从新判断当月的天数  从新初始化
            }
        });
        loopView_chooseDay1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                chooseDay1 = days[index];
            }
        });
        loopView_chooseHour1.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                chooseHour1 = hours[index];
            }
        });

        loopView_chooseMonth2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                chooseMonth2 = months[index];
                initDayData2();            //每次选中年份后 要从新判断当月的天数  从新初始化
            }
        });
        loopView_chooseDay2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                chooseDay2 = days[index];
            }
        });
        loopView_chooseHour2.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                chooseHour2 = hours[index];
            }
        });
    }


    private void initView() {
        tv_close = (TextView) view.findViewById(R.id.memo_close);
        tv_sure = (TextView) view.findViewById(R.id.memo_sure);
        loopView_chooseMonth1 = (LoopView) view.findViewById(R.id.memo_month_one_loopView);
        loopView_chooseDay1 = (LoopView) view.findViewById(R.id.memo_day_one_loopView);
        loopView_chooseHour1 = (LoopView) view.findViewById(R.id.memo_hour_one_loopView);
        loopView_chooseMonth2 = (LoopView) view.findViewById(R.id.memo_month_two_loopView);
        loopView_chooseDay2 = (LoopView) view.findViewById(R.id.memo_day_two_loopView);
        loopView_chooseHour2 = (LoopView) view.findViewById(R.id.memo_hour_two_loopView);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.memo_sure) {
            dismiss();
            listener.time(chooseMonth1, chooseDay1, chooseHour1, chooseMonth2, chooseDay2, chooseHour2);
        } else {
            dismiss();
        }
    }

    public interface OnBackListener {
        void time(String month1, String day1, String hour1, String month2, String day2, String hour2);
    }

    public void setOnBackListener(OnBackListener listener) {
        this.listener = listener;
    }
}
