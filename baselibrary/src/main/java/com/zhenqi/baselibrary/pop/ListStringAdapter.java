package com.zhenqi.baselibrary.pop;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhenqi.baselibrary.R;
import com.zhenqi.baselibrary.util.TextUtil;

import java.util.List;

public class ListStringAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ListStringAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.text1, TextUtil.nullToStr(item));
        if (mBgColor != 0) {
            helper.setBackgroundColor(R.id.text1, mBgColor);
        }
        if (textColor != 0) {
            helper.setTextColor(R.id.text1, textColor);
        }
        helper.addOnClickListener(R.id.text1);
    }

    private int mBgColor;
    private int textColor;

    public void setBackgroundColor(int color) {
        this.mBgColor = color;
    }

    public void setTextColor(int color) {
        this.textColor = color;
    }
}