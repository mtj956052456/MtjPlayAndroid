package com.zhenqi.baselibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 创建者: lenovo
 * 时间: 2019/5/16
 * 描述:
 */
public class TextViewUnderline extends TextView {

    public TextViewUnderline(Context context) {
        this(context, null);
    }

    public TextViewUnderline(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewUnderline(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int underlineColor = 0x00000000;
    private boolean isChecked = false;
    private Paint mPaint = new Paint();

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public int getUnderlineColor() {
        return underlineColor;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //getPaint()
        if (isChecked) {
            mPaint.setColor(underlineColor);
        } else {
            mPaint.setColor(0x00000000);
        }
        String string = getText().toString();
        float width = getPaint().measureText(string);
        canvas.drawRect((getWidth() - width) / 2, getHeight() - 5, width + (getWidth() - width) / 2, getHeight(), mPaint);
    }
}
