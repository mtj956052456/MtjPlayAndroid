package com.zhenqi.pm2_5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumFile;
import com.zhenqi.baselibrary.base.BaseActivity;
import com.zhenqi.baselibrary.view.CustomToolbar;
import com.zhenqi.pm2_5.share.ShareLoginUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.customtoolbar)
    CustomToolbar mCustomToolbar;
    @BindView(R.id.iv_head)
    ImageView mIvHead;
    @BindView(R.id.share_img)
    ImageView mShareImg;
    @BindView(R.id.et_share_content)
    EditText mEtShareContent;

    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void afterBinder() {
        super.afterBinder();
        mCustomToolbar.setMainTitle("登录-分享-推送");
        mEtShareContent.setText("http://www.baidu.com");
    }

    @OnClick({R.id.share_img, R.id.btn_wx_login, R.id.btn_share})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_wx_login:
                ShareLoginUtil.WXLogin(this, mIvHead);
                break;
            case R.id.btn_share:
                shareImg();
                break;
            case R.id.share_img:
                chooseImg();
                break;
        }
    }

    private void shareImg() {
        if (mBitmap == null){
            ToastUtils.showShort("图片为空");
            return;
        }
        HomeSharePopWindow popWindow = new HomeSharePopWindow(this);
        popWindow.mUMWeb = getShareUMWeb();
        popWindow.showBottom();
    }

    /**
     * 获取分享的UMWeb对象
     *
     * @return
     */
    public UMWeb getShareUMWeb() {
        UMImage umImage = new UMImage(this, mBitmap);
        UMWeb web = new UMWeb(mEtShareContent.getText().toString()); //切记切记 这里分享的链接必须是https开头
        web.setTitle(getResources().getString(R.string.app_name));//标题
        web.setThumb(umImage);  //缩略图
        web.setDescription("web分享");//描述
        return web;
    }

    private Bitmap mBitmap;

    private void chooseImg() {
        if (checkRWPermission()) {
            Album.image(this)
                    .multipleChoice()
                    .camera(true)
                    .columnCount(3)
                    .selectCount(1)
                    .onResult(new Action<ArrayList<AlbumFile>>() {
                        @Override
                        public void onAction(int requestCode, @NonNull ArrayList<AlbumFile> result) {
                           String path = result.get(0).getPath();
                            mBitmap = BitmapFactory.decodeFile(path);
                            Glide.with(MainActivity.this).load(path).into(mShareImg);
                        }
                    }).start();

        }
    }
}
