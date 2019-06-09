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
public class TimeMdPopWin extends BasePopWindow implements View.OnClickListener {

    private View view;
    private LoopView loopView_chooseMonth, loopView_chooseDay;
    private TextView tv_close, tv_sure;
    private OnBackListener listener;
    private String chooseMonth, chooseDay;
    private String nowYear, nowMonth, nowDay;


    public TimeMdPopWin(Activity activity, String reqTimeDay) {
        super(activity);
        chooseDay = reqTimeDay;
        initCanTouch();
        addBackView();
    }

    @Override
    protected View SetView() {
        view = LayoutInflater.from(mActivity).inflate(R.layout.time_dh_pop_layout, null);

        /**初始化时间 start */
        String[] nowDate = DateUtil.getNowDate().split("-");
        chooseMonth = nowDate[1];
        //        chooseDay = nowDate[2];

        nowYear = nowDate[0];
        nowMonth = nowDate[1];
        nowDay = nowDate[2];

        //        for (int i = 0; i < months.length; i++) {
        //            if(nowYear.equals(months [i])){
        //                months = new String[]{nowYear};
        //                break;
        //            }
        //        }


        initView();
        initClick();
        initData();
        /**初始化时间 end */
        return view;
    }


    //    String[] months = new String[]{"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    String[] days = null;


    public void initData() {
        initMonthData1();
        initDayData1();
    }


    /**
     * 月份1
     */
    private void initMonthData1() {
        List<String> list = new ArrayList<>();
        list.add(nowMonth);
        //        int position = 0;
        //
        //        for (int i = 0; i < months.length; i++) {
        //            list.add(months[i]);
        //            if (months[i].equals(chooseMonth)) {
        //                position = i;
        //            }
        //        }
        loopView_chooseMonth.setItems(list); //设置数据
        loopView_chooseMonth.setTextSize(16);
        //        loopView_chooseMonth.setCurrentPosition(position);
        loopView_chooseMonth.setNotLoop();
    }

    /**
     * 天1
     */
    private void initDayData1() {
        List<String> list = new ArrayList<>();
        int position = 0;
        //        int Alldays = DateUtil.getDays(Integer.parseInt(DateUtil.getNowYearDate()), Integer.parseInt(chooseMonth));//获取当月多少天
        int Alldays = Integer.parseInt(chooseDay);
        for (int i = 1; i <= Alldays; i++) { //8号之后的
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
        loopView_chooseDay.setItems(list); //设置数据
        loopView_chooseDay.setTextSize(16);
        loopView_chooseDay.setCurrentPosition(position);

        chooseDay = list.get(position);
        loopView_chooseDay.setNotLoop();
    }

    private void initClick() {
        tv_close.setOnClickListener(this);
        tv_sure.setOnClickListener(this);
        //年 月 日 时 分  的时间监听
        //        loopView_chooseMonth.setListener(new OnItemSelectedListener() {
        //            @Override
        //            public void onItemSelected(int index) {
        //                chooseMonth = months[index];
        //                initDayData1();            //每次选中年份后 要从新判断当月的天数  从新初始化
        //            }
        //        });
        loopView_chooseDay.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                chooseDay = days[index];
            }
        });
    }


    private void initView() {
        tv_close = (TextView) view.findViewById(R.id.memo_close);
        tv_sure = (TextView) view.findViewById(R.id.memo_sure);
        loopView_chooseMonth = (LoopView) view.findViewById(R.id.memo_month_one_loopView);
        loopView_chooseDay = (LoopView) view.findViewById(R.id.memo_day_one_loopView);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.memo_sure) {
            dismiss();
            listener.time(chooseMonth, chooseDay);
        } else if (id == R.id.memo_close) {
            dismiss();
        }
    }

    public interface OnBackListener {
        void time(String month, String day);
    }

    public void setOnBackListener(OnBackListener listener) {
        this.listener = listener;
    }
}
