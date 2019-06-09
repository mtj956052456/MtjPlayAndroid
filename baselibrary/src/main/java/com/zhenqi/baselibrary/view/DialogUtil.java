package com.zhenqi.baselibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhenqi.baselibrary.R;


/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/29
 * 描述:
 */
public class DialogUtil {


    /**
     * @param context 上下文
     * @param info    图标下文字
     * @return
     */
    public static Dialog CreateLoadingDialog(Context context, String info) {
        final View view = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
        ImageView img = (ImageView) view.findViewById(R.id.iv_loading);
        TextView tv = (TextView) view.findViewById(R.id.tv_content);
        tv.setText(info);
        //img.setImageResource(R.drawable.dialog_animation);
        Glide.with(context).load(R.drawable.dialog_loading).into(img);
        final Dialog dialog = new Dialog(context, R.style.loadingDialog);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (displayWidth * 0.5); //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * 0.25); //高度设置为屏幕的0.25
        dialog.getWindow().setAttributes(p);  //设置生效
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        return dialog;
    }

    /**
     * @param context 上下文
     * @param info    图标下文字
     * @return
     */
    public static Dialog CreateZhenQiDialog(Context context, String info) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_zhenqi, null);
        ImageView img = (ImageView) view.findViewById(R.id.iv_loading);
        TextView tv = (TextView) view.findViewById(R.id.tv_content);
        tv.setText(info);
        AnimationDrawable animationDrawable = (AnimationDrawable) img.getDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.start();

        Dialog dialog = new Dialog(context, R.style.loadingDialog);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        android.view.WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (displayWidth * 0.5); //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * 0.25); //高度设置为屏幕的0.25
        dialog.getWindow().setAttributes(p);  //设置生效
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        return dialog;
    }


}
