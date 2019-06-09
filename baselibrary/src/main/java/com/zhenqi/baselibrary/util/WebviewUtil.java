package com.zhenqi.baselibrary.util;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


/**
 * 创建者: 孟腾蛟
 * 时间: 2019/4/29
 * 描述:
 */
public class WebviewUtil {

    private WebviewUtil() {
    }

    private static class Builder {
        static WebviewUtil webviewUtil = new WebviewUtil();
    }

    public static WebviewUtil instance() {
        return Builder.webviewUtil;
    }

    private boolean flushFlag = true;

    /**
     * 下来刷新webview
     *
     * @param web
     * @param swipe
     */
    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface", "SetJavaScriptEnabled"})
    public void initWeb(final WebView web, final SwipeRefreshLayout swipe) {
        flushFlag = true;
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient() {
            // 配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setGeolocationEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        web.setHorizontalScrollBarEnabled(false);// 水平不显示
        web.setVerticalScrollBarEnabled(false); // 垂直不显示
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        web.getSettings().setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        web.getSettings().setSupportZoom(false); // 设置支持缩放
        web.getSettings().setBuiltInZoomControls(false);// 显示缩放控件
        web.getSettings().setLoadWithOverviewMode(true);
        web.addJavascriptInterface(this, "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//图片显示不正常
        }

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                web.reload();
            }
        });
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (flushFlag) { //刷新标识
                    swipe.setRefreshing(true);
                    flushFlag = false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                flushFlag = false;
                swipe.setRefreshing(false);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                flushFlag = false;
            }
        });
    }

    /**
     * @param web
     */
    @SuppressLint({"JavascriptInterface", "AddJavascriptInterface", "SetJavaScriptEnabled"})
    public void initWeb(final Dialog mDialog, WebView web) {
        flushFlag = true;
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient() {
            // 配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin,
                                                           GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setGeolocationEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        web.setHorizontalScrollBarEnabled(false);// 水平不显示
        web.setVerticalScrollBarEnabled(false); // 垂直不显示
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        web.getSettings().setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        web.getSettings().setSupportZoom(false); // 设置支持缩放
        web.getSettings().setBuiltInZoomControls(false);// 显示缩放控件
        web.getSettings().setLoadWithOverviewMode(true);
        web.addJavascriptInterface(this, "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//图片显示不正常
        }
        web.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (flushFlag) { //刷新标识
                    mDialog.show();
                    flushFlag = false;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                flushFlag = false;
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                flushFlag = false;
            }
        });
    }

    /**
     * @param web
     */
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    public void initWeb(Context context ,WebView web) {
        web.setWebViewClient(new WebViewClient());
        web.setWebChromeClient(new WebChromeClient() {
            // 配置权限（同样在WebChromeClient中实现）
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }
        });
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setGeolocationEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        web.getSettings().setTextSize(WebSettings.TextSize.NORMAL);
        web.setHorizontalScrollBarEnabled(false);// 水平不显示
        web.setVerticalScrollBarEnabled(false); // 垂直不显示
        web.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        web.getSettings().setUseWideViewPort(true);// 设置是当前html界面自适应屏幕
        web.getSettings().setSupportZoom(false); // 设置支持缩放
        web.getSettings().setBuiltInZoomControls(false);// 显示缩放控件
        web.getSettings().setLoadWithOverviewMode(true);
        web.addJavascriptInterface(context, "android");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            web.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);//图片显示不正常
        }
    }
}
