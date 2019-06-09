package com.zhenqi.pm2_5;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMWeb;
import com.zhenqi.baselibrary.base.BasePopWindow;
import com.zhenqi.pm2_5.share.ShareLoginUtil;


public class HomeSharePopWindow extends BasePopWindow {
    public UMWeb mUMWeb;
    public View wechat;
    public View friend;
    public View qq;
    public View weibo;

    public HomeSharePopWindow(Activity activity) {
        super(activity);
        initCanTouch();
        addBackView();
    }

    @Override
    protected View SetView() {
        View view = View.inflate(mActivity, R.layout.pop_share_home, null);
        wechat = view.findViewById(R.id.wechat_share);
        friend = view.findViewById(R.id.friend_share);
        qq = view.findViewById(R.id.qq_share);
        weibo = view.findViewById(R.id.weibo_share);
        initView(wechat, R.mipmap.wechat, "微信");
        initView(friend, R.mipmap.friend, "朋友圈");
        initView(qq, R.mipmap.qq, "QQ");
        initView(weibo, R.mipmap.sign, "微博");
        wechat.setOnClickListener(mOnclick);
        friend.setOnClickListener(mOnclick);
        qq.setOnClickListener(mOnclick);
        weibo.setOnClickListener(mOnclick);
        return view;
    }

    private void initView(View view, int imgRes, String title) {
        if (view != null) {
            ImageView imageView = view.findViewById(R.id.share_img);
            imageView.setImageResource(imgRes);
            TextView textView = view.findViewById(R.id.share_title);
            textView.setText(title);
        }
    }


    private View.OnClickListener mOnclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mUMWeb == null)
                return;
            int id = v.getId();
            if (id == R.id.wechat_share) {
                ShareLoginUtil.ShareWeb(mActivity, SHARE_MEDIA.WEIXIN, mUMWeb);
            } else if (id == R.id.friend_share) {
                ShareLoginUtil.ShareWeb(mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, mUMWeb);
            } else if (id == R.id.qq_share) {
                ShareLoginUtil.ShareWeb(mActivity, SHARE_MEDIA.QQ, mUMWeb);
            } else if (id == R.id.weibo_share) {
                ShareLoginUtil.ShareWeb(mActivity, SHARE_MEDIA.SINA, mUMWeb);
            }
        }
    };
}
