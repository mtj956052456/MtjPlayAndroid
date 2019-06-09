package com.zhenqi.baselibrary.util.album;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.zhenqi.baselibrary.R;
import com.zhenqi.baselibrary.util.ViewpagerFixed;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/17
 * 描述:  图片工具类
 */
public class ImageDialogUtil {

    /**
     * 显示单个图片
     *
     * @param context
     * @param imgUrl
     * @return
     */
    public static Dialog showImageDialog(final Context context, String imgUrl) {
        final Dialog dialog = new Dialog(context, R.style.loadingDialog);
        PhotoView image = new PhotoView(context);
        Glide.with(context).load(imgUrl).into(image);
        image.setBackgroundDrawable(new ColorDrawable(0x000000));
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(image);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.black);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); //获取对话框当前的参数值
        p.width = (int) (displayWidth * 1);
        p.height = (int) (displayHeight * 1);
        dialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog消失
        dialog.getWindow().setAttributes(p);  //设置生效
        return dialog;
    }


    /**
     * 图片显示ImagView
     *
     * @param context
     * @param position 点击条目的
     * @param imgUrl   集合
     * @return
     */
    public static Dialog showImageListDialog(final Context context, int position, List<String> imgUrl) {
        final Dialog dialog = new Dialog(context, R.style.loadingDialog);
        View view = LayoutInflater.from(context).inflate(R.layout.imageview_dialog, null);
        ViewpagerFixed vp = (ViewpagerFixed) view.findViewById(R.id.viewpager);
        List<PhotoView> listImage = new ArrayList<>();
        for (int i = 0; i < imgUrl.size(); i++) {
            PhotoView image = new PhotoView(context);
            Glide.with(context).load(imgUrl.get(i)).into(image);
            listImage.add(image);
            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
        viewpageAdapter adapter = new viewpageAdapter(listImage);
        vp.setAdapter(adapter);
        vp.setCurrentItem(position);
        vp.setOffscreenPageLimit(4);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        window.setBackgroundDrawableResource(R.color.black);
        WindowManager.LayoutParams p = window.getAttributes(); //获取对话框当前的参数值
        p.width = WindowManager.LayoutParams.MATCH_PARENT;
        p.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(p);  //设置生效

        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(true);// 设置点击屏幕Dialog消失
        return dialog;
    }


    public static class viewpageAdapter extends PagerAdapter {
        List<PhotoView> listView;

        public viewpageAdapter(List<PhotoView> listView) {
            this.listView = listView;
        }

        @Override
        public int getCount() {
            return listView.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(listView.get(position));
            return listView.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(listView.get(position));
        }

    }

}
