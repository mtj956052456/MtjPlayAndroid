package com.zhenqi.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.zhenqi.baselibrary.R;

/**
 * @author 孟腾蛟
 * @time 2018/10/12 2018 10
 * @des
 */

public class TipsDialog extends Dialog {

    private TextView mTvTipsTitle, mTvTipsContent, mTvTipsCancle, mTvTipsSure, mTvTipsSkip;
    private CallBack mCallBack;
    private String title, content, yesStr, noStr;
    private String downLoadUrl;

    public TipsDialog(@NonNull Context context, @NonNull String title, @NonNull String content, @NonNull String yesStr, @NonNull String noStr, CallBack callBack) {
        super(context);
        this.mCallBack = callBack;
        this.title = title;
        this.content = content;
        this.yesStr = yesStr;
        this.noStr = noStr;
        setCancelable(false);
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public TipsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected TipsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tips);
        initView();
        mTvTipsTitle.setText(title);

        mTvTipsContent.setTextIsSelectable(true);//可选择复制
        mTvTipsCancle.setText(noStr);
        mTvTipsSure.setText(yesStr);
        if (content.contains("跳转下载")) {
            mTvTipsContent.setText(content.replace("跳转下载", ""));
            mTvTipsSkip.setVisibility(View.VISIBLE);
        } else {
            mTvTipsContent.setText(content);
            mTvTipsSkip.setVisibility(View.INVISIBLE);
        }
    }

    private void initView() {
        mTvTipsTitle = findViewById(R.id.tv_tips_title);
        mTvTipsContent = findViewById(R.id.tv_tips_content);
        mTvTipsCancle = findViewById(R.id.tv_tips_cancle);
        mTvTipsSure = findViewById(R.id.tv_tips_sure);
        mTvTipsSkip = findViewById(R.id.tv_tips_skip);
        TextView[] tvs = {mTvTipsTitle, mTvTipsContent, mTvTipsCancle, mTvTipsSure, mTvTipsSkip};
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.tv_tips_skip) {
                skipDownLoad();
                dismiss();
                mCallBack.callNO();
            } else if (i == R.id.tv_tips_cancle) {
                mCallBack.callNO();
                dismiss();
            } else if (i == R.id.tv_tips_sure) {
                mCallBack.callYES();
                dismiss();
            }
        }
    };


    /*
    跳转下载
     */
    private void skipDownLoad() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse(downLoadUrl);//此处填正式版链接
        intent.setData(content_url);
        getContext().startActivity(intent);
    }


    public interface CallBack {

        void callYES();

        void callNO();
    }

}
