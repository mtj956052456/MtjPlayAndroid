package com.zhenqi.baselibrary.util.album;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhenqi.baselibrary.R;

import java.util.List;

/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/17
 * 描述:  相册选择 适配器
 */
public class AlbumImgAdapter extends RecyclerView.Adapter<AlbumImgAdapter.ViewHolder> {

    public LayoutInflater mInflater;
    public List<String> imgpathList;
    public Context mContext;
    public addListener listener;
    public int count = 0;
    public int maxImageSize = 9;//默认最大是9张
    public int multiple = 3;//屏幕的几分之多少

    public AlbumImgAdapter(Context context, List<String> imgpathList, int maxImageSize, int multiple) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.imgpathList = imgpathList;
        this.maxImageSize = maxImageSize;
        this.multiple = multiple;
    }

    @Override
    public AlbumImgAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_img_delete, parent, false);
        return new AlbumImgAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        setImageViewWH(mContext, holder.mImgItem, multiple);
        if (position == imgpathList.size()) {
            holder.mImgItem.setImageResource(R.mipmap.add);
            holder.mImgItem.setOnClickListener(new AlbumImgAdapter.addClick());
            holder.mImagDelete.setVisibility(View.GONE);
        } else {
            holder.mImagDelete.setVisibility(View.VISIBLE);
            holder.mImgItem.setOnClickListener(new AlbumImgAdapter.mClick(position));
            holder.mImagDelete.setOnClickListener(new AlbumImgAdapter.mClick(position));
            Glide.with(mContext).load(imgpathList.get(position)).into(holder.mImgItem);
        }
    }

    /**
     * 设置宽高
     * @param context
     * @param imageView
     * @param multiple
     */
    public static void setImageViewWH(Context context, ImageView imageView, int multiple) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;         // 屏幕宽度（像素）
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.width = screenWidth / multiple - dp2px(context, 2); //减去
        lp.height = screenWidth / multiple - dp2px(context, 2);
        imageView.setLayoutParams(lp);
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        if (imgpathList.size() == maxImageSize) {
            return imgpathList.size();
        } else {
            return imgpathList.size() + 1;
        }
    }

    public void refreshData(List<String> list) {
        imgpathList = list;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImgItem;
        private ImageView mImagDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgItem = (ImageView) itemView.findViewById(R.id.imgItem);
            mImagDelete = (ImageView) itemView.findViewById(R.id.img_delete);
        }
    }


    private class mClick implements View.OnClickListener {
        private int post;

        public mClick(int post) {
            this.post = post;
        }

        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.imgItem) {
                listener.enlargePhoto(post);
                notifyDataSetChanged();
            } else if (i == R.id.img_delete) {
                imgpathList.remove(post);
                listener.removePhoto(post);
                count--;
                notifyDataSetChanged();
            }
        }
    }

    private class addClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            listener.addListen();
        }
    }

    public void setAddListener(addListener listener) {
        this.listener = listener;
    }


    public interface addListener {
        void addListen();

        void removePhoto(int position);

        void enlargePhoto(int position);
    }
}
