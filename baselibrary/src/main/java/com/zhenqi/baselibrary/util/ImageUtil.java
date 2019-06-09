package com.zhenqi.baselibrary.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.yanzhenjie.album.AlbumFile;
import com.zhenqi.baselibrary.R;
import com.zhenqi.baselibrary.util.album.ImageDialogUtil;
import com.zhenqi.baselibrary.util.album.ImgAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/9/13 0013.
 */

public class ImageUtil {


    /**
     * 设置图片集的高度
     *
     * @param mAlbumFiles
     * @param gridView
     */
    public static void setGridViewHeightBasedOnChildren(ArrayList<AlbumFile> mAlbumFiles, GridView gridView) {
        int heigh = gridView.getColumnWidth();
        // 固定列宽，有多少列
        int col = gridView.getNumColumns();
        int num = mAlbumFiles.size();
        int trueline = num == 9 ? 3 : (num / col + 1);
        int totalHeight = heigh * trueline;

        // 获取listview的布局参数
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        // 设置高度
        params.height = totalHeight;
        // 设置margin
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        // 设置参数
        gridView.setLayoutParams(params);
    }

    /**
     * 设置RecyclerView  水平展示
     *
     * @param context
     * @param recycler
     * @param imgList
     */
    public static void setImageListRecyclerView(final Context context, RecyclerView recycler, final String imgList, final String thumbnailList) {
        String[] imgStrs = imgList.split("\\|");
        String[] thumbnailStrs = thumbnailList.split("\\|");
        final List<String> imgLists = Arrays.asList(imgStrs);
        final List<String> thumbList = Arrays.asList(thumbnailStrs);//网络url集合
        ImgAdapter backAdapter = new ImgAdapter(R.layout.item_img, thumbList);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        recycler.setLayoutManager(mLayoutManager);
        recycler.setAdapter(backAdapter);
        backAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ImageDialogUtil.showImageListDialog(context, position, imgLists).show();  //Dialog图片放大
            }
        });
    }
    /**
     * 设置ImageView的 宽高
     *
     * @param context
     * @param imageView
     * @param multiple  几分
     */
    public static void setImageViewWH(Context context, ImageView imageView, int multiple) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;         // 屏幕宽度（像素）
        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
        lp.width = screenWidth / multiple - dp2px(context, 2); //减去
        lp.height = screenWidth / multiple - dp2px(context, 2);
        //imageView.setPadding(5,5,5,5);
        Log.e("mtj", "width: " + lp.width);
        Log.e("mtj", "height: " + lp.height);
        imageView.setLayoutParams(lp);
    }


    /**
     * 绘制文字到右下角
     *
     * @param context
     * @param bitmap
     * @param text
     * @param size
     * @param color
     * @return
     */
    public static Bitmap drawTextToRightBottom(Context context, Bitmap bitmap, String text,
                                               int size, int color, int paddingRight, int paddingBottom) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setTextSize(dp2px(context, size));
        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);
        return drawTextToBitmap(context, bitmap, text, paint, bounds,
                bitmap.getWidth() - bounds.width() - dp2px(context, paddingRight),
                bitmap.getHeight() - dp2px(context, paddingBottom));
    }

    //图片上绘制文字
    private static Bitmap drawTextToBitmap(Context context, Bitmap bitmap, String text,
                                           Paint paint, Rect bounds, int paddingLeft, int paddingTop) {
        Bitmap.Config bitmapConfig = bitmap.getConfig();

        paint.setDither(true); // 获取跟清晰的图像采样
        paint.setFilterBitmap(true);// 过滤一些
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);

        canvas.drawText(text, paddingLeft, paddingTop, paint);
        return bitmap;
    }


    /**
     * 获取网络图片  下载并获取文件集合
     * @param filepaths
     * @param context
     * @return
     */
    public static ArrayList<File> getImageFileList(List<String> filepaths, Context context) {
        ArrayList<File> lists = new ArrayList<>();
        for (int i = 0; i < filepaths.size(); i++) {
            File uploadBmpFile = getBitmapFile(filepaths.get(i), context);
            lists.add(uploadBmpFile);
        }
        return lists;
    }
    /**
     * 获取本地图片  压缩并获取文件集合
     * @param filepaths
     * @param context
     * @return
     */
    public static ArrayList<File> getBitmappaths(List<String> filepaths, Context context) {
        ArrayList<File> lists = new ArrayList<>();
        for (int i = 0; i < filepaths.size(); i++) {
            File uploadBmpFile = getBitmap(filepaths.get(i), context);
            //long fileSize = getFileSize(uploadBmpFile);
            //Log.e("fileSize", "fileSize: " + fileSize / 1024 + " KB");
            lists.add(uploadBmpFile);
        }
        return lists;
    }

    /**
     * 获取url图片文件 不压缩
     *
     * @param path
     * @param context
     * @return
     */
    public static File getBitmapFile(String path, Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int i = 1;
        while (true) {
            if (options.outWidth / i <= 1000) {
                options.inSampleSize = i;
                options.inJustDecodeBounds = false;
                break;
            }
            i *= 2;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        String s = context.getFilesDir() + "zhenqicamera";
        File file = new File(s);
        if (!file.exists()) {
            file.mkdir();
        }
        String filepath = s + "/" + System.currentTimeMillis() + ".jpg";
        File filestore = new File(filepath);
        try {
            FileOutputStream outputStream = new FileOutputStream(filestore);
            outputStream.flush();
            outputStream.close();
            return filestore;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(path);

    }


    /**
     * 获取图片文件 并压缩
     *
     * @param path
     * @param context
     * @return
     */
    public static File getBitmap(String path, Context context) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        int i = 1;
        while (true) {
            if (options.outWidth / i <= 1000) {
                options.inSampleSize = i;
                options.inJustDecodeBounds = false;
                break;
            }
            i *= 2;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        String s = context.getFilesDir() + "zhenqicamera";
        File file = new File(s);
        if (!file.exists()) {
            file.mkdir();
        }
        String filepath = s + "/" + System.currentTimeMillis() + ".jpg";
        File filestore = new File(filepath);
        try {
            FileOutputStream outputStream = new FileOutputStream(filestore);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream);

            int i1 = bitmap.getByteCount() / 1024;
            Log.e("mtj", "Bitmap之后大小: " + i1 + "KB");
            outputStream.flush();
            outputStream.close();
            return filestore;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(path);

    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    //存背景图片
    public static void saveImgFile(Context context, String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int i = 1;
        while (true) {
            if (options.outWidth / i <= 1000) {
                options.inSampleSize = i;
                options.inJustDecodeBounds = false;
                break;
            }
            i *= 2;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        String s = context.getFilesDir() + "zhenqibg";
        File file = new File(s);
        if (!file.exists()) {
            file.mkdir();
        }
        String filepath = s + "/" + System.currentTimeMillis() + ".jpg";
        SPUtil.save("imagebg", filepath);
        File filestore = new File(filepath);
        try {
            FileOutputStream outputStream = new FileOutputStream(filestore);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //删除背景图片
    public static void deleteImageSrc(Context context) {
        String s = context.getFilesDir() + "zhenqibg";
        File file = new File(s);
        if (file.exists() && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                f.delete();
            }
        }
    }

    //取背景图片
    public static String getImageSrc() {
        return SPUtil.get("imagebg");
    }


    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
