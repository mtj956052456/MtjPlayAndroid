package com.zhenqi.baselibrary.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author 孟腾蛟
 * @time 2018/11/19 2018 11
 * @des
 */

@SuppressLint("AppCompatCustomView")
public class TableTitleTextView extends TextView {

    public TableTitleTextView(Context context) {
        super(context);
    }

    public TableTitleTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TableTitleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private int mRightImgId ;

    public int getRightImgId() {
        return mRightImgId;
    }

    //设置排序表头图标
    public void setRatioRightImg(int img_Id) {
        try {
            this.mRightImgId = img_Id;

            String str = getText().toString();
            SpannableString span3 = new SpannableString(str);
            ImageSpan image = new ImageSpan(getContext(), mRightImgId, DynamicDrawableSpan.ALIGN_BOTTOM);
            span3.setSpan(image, str.length()-1, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            setText(span3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
