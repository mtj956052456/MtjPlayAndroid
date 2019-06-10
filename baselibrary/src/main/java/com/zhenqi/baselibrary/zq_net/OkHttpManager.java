package com.zhenqi.baselibrary.zq_net;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.zhenqi.baselibrary.zq_net.encrypt.EncryptionUtil;
import com.zhenqi.baselibrary.zq_net.encrypt.Md5Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class OkHttpManager implements Serializable {
    /**
     * 单例,反序列化,反射
     */
    private static OkHttpClient mOkHttpClients;

    private OkHttpManager() {
    }

    private static class OkHttpViewHolder {
        private static final OkHttpManager OK_HTTP_MANAGER = new OkHttpManager();
    }

    public static OkHttpManager getInstance() {
        mOkHttpClients = new OkHttpClient();
        return OkHttpViewHolder.OK_HTTP_MANAGER;
    }

    private Object readResolve() throws ObjectStreamException {
        return OkHttpViewHolder.OK_HTTP_MANAGER;
    }

    /**
     * post  Json 格式请求
     *
     * @param url
     * @param okCallBack
     */
    public void postJsonResult(final String url, final String param, final OkHttpCallBack.okCallBack okCallBack) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), param);

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String dataS = response.body().string();
                            JSONObject jsonObject = new JSONObject(dataS);
                            JSONArray jsonArray = jsonObject.getJSONArray("results");
                            JSONObject resultObj = (JSONObject) jsonArray.get(0);
                            JSONObject valuesObj = resultObj.getJSONObject("values");
                            e.onNext(valuesObj.getString("text"));
                        } catch (JSONException e1) {
                            e.onError(new Throwable("解析错误"));
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * 不加密 普通get请求
     *
     * @param url
     * @param okCallBack
     */
    public void getResult(final String url, final OkHttpCallBack.okCallBack okCallBack) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String dataS = response.body().string();
                            JSONObject jsonObject = new JSONObject(dataS);
                            String result = jsonObject.getString("result");
                            boolean succeed = jsonObject.getBoolean("succeed");
                            if (succeed) {
                                e.onNext(result);
                            } else {
                                e.onError(new Throwable(result));
                            }
                        } catch (JSONException e1) {
                            e.onError(new Throwable("解析错误"));
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * palm3 加解密请求
     *
     * @param method
     * @param map
     * @param url
     * @param okCallBack
     */
    public void Palm3Post(String method, Map map, final String url, final OkHttpCallBack.okCallBack okCallBack) {
        final String secret = EncryptionUtil.getRequestMsg(method, map);//加密
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                FormBody formBody = new FormBody.Builder()
                        .add("param", secret)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String dataS = response.body().string();
                            String resultMsg = EncryptionUtil.getResult(dataS);//解密
                            JSONObject jsonObject = new JSONObject(resultMsg);
                            boolean success = jsonObject.getBoolean("success");
                            String errcode = jsonObject.getString("errcode");
                            String errmsg = jsonObject.getString("errmsg");
                            if (success) {
                                JSONObject object = jsonObject.getJSONObject("result");
                                boolean result = object.getBoolean("success");
                                String data = object.getString("data");
                                if (result) {
                                    e.onNext(data);
                                } else {
                                    e.onError(new Throwable(data));
                                }
                            } else {
                                e.onError(new Throwable("错误码:" + errcode + "  错误内容:" + errmsg));
                            }
                        } catch (JSONException e1) {
                            e.onError(new Throwable("解析错误"));
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * palm2 加解密请求
     *
     * @param method
     * @param map
     * @param url
     * @param okCallBack
     */
    public void Palm2Post(String method, Map map, final String url, final OkHttpCallBack.okCallBack okCallBack) {
        final String secret = EncryptionUtil.getRequestMsgOld(method, map);//加密
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                FormBody formBody = new FormBody.Builder()
                        .add("param", secret)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String dataS = response.body().string();
                            String resultMsg = EncryptionUtil.getResultOld(dataS);//解密
                            JSONObject jsonObject = new JSONObject(resultMsg);
                            boolean success = jsonObject.getBoolean("success");
                            String errcode = jsonObject.getString("errcode");
                            String errmsg = jsonObject.getString("errmsg");
                            if (success) {
                                JSONObject object = jsonObject.getJSONObject("result");
                                boolean result = object.getBoolean("success");
                                String data = object.getString("data");
                                if (result) {
                                    e.onNext(data);
                                } else {
                                    e.onError(new Throwable(data));
                                }
                            } else {
                                e.onError(new Throwable("错误码:" + errcode + "  错误内容:" + errmsg));
                            }
                        } catch (JSONException e1) {
                            e.onError(new Throwable("解析错误"));
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * app j
     * 上传错误信息
     *
     * @param method
     * @param map
     * @param url
     * @param okCallBack
     */
    public void PushErrorInfo(String method, Map map, final String url, final OkHttpCallBack.okCallBack okCallBack) {
        final String secret = EncryptionUtil.getRequestMsgOld(method, map);//加密
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) {
                FormBody formBody = new FormBody.Builder()
                        .add("param", secret)
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        try {
                            String dataS = response.body().string();
                            String resultMsg = EncryptionUtil.getResultOld(dataS);//解密
                            JSONObject jsonObject = new JSONObject(resultMsg);
                            boolean success = jsonObject.getBoolean("success");
                            String errmsg = jsonObject.getString("errmsg");
                            if (success) {
                                e.onNext(success);
                            } else {
                                e.onError(new Throwable(errmsg));
                            }
                        } catch (JSONException e1) {
                            e.onError(new Throwable("解析错误"));
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    /**
     * Palm2_palmapi_文件上传
     */
    public void postPalmFileApi(final String method, final Map<String, String> map, final String url, final ArrayList<File> filePaths, final OkHttpCallBack.okCallBack okCallBack) {
        final String secret = EncryptionUtil.getRequestMsgPalmapi(method, map);//加密
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                //图片
                MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
                if (filePaths != null) {
                    for (int i = 0; i < filePaths.size(); i++) {
                        RequestBody body = RequestBody.create(MediaType.parse("image/jpg"), filePaths.get(i));
                        requestBody.addPart(Headers.of("Content-Disposition", "form-data; name=\"file_" + (i + 1) + "\";filename=\"file.jpg\""), body);
                    }
                }
                requestBody.addFormDataPart("appId", "4ab4aca1c61c78b89338c3e3804e0e9d")
                        .addFormDataPart("method", method)
                        .addFormDataPart("secret", secret);
                for (String key : map.keySet()) {
                    requestBody.addFormDataPart(key, map.get(key));
                }

                //发送请求
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody.build())
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String dataS = response.body().string();
                            String resultPalmapi = EncryptionUtil.getResultPalmapi(dataS);
                            e.onNext(resultPalmapi);
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    /**
     * Palm2_palmapi   接口请求
     */
    public void postPalmapi(final String method, final Map<String, String> map, final String url, final OkHttpCallBack.okCallBack okCallBack) {
        final String secret = EncryptionUtil.getRequestMsgPalmapi(method, map);//加密
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                FormBody.Builder formBody = new FormBody.Builder()
                        .add("appId", "4ab4aca1c61c78b89338c3e3804e0e9d")
                        .add("method", method)
                        .add("secret", secret);
                for (String key : map.keySet()) {
                    formBody.add(key, map.get(key));
                }
                FormBody build = formBody.build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(build)
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e1) {
                        e.onError(e1);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        try {
                            String dataS = response.body().string();
                            String resultPalmapi = EncryptionUtil.getResultPalmapi(dataS);
                            e.onNext(resultPalmapi);
                        } catch (Exception e1) {
                            e.onError(new Throwable(e1.getMessage()));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        okCallBack.onSuccess(object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        okCallBack.onError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    /**
     * 获取最新版本
     */
    public void getVersion(final Context context, final ZhenqiInterface.NetWorkResponse callBack) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("appId", "4ab4aca1c61c78b89338c3e3804e0e9d");
        builder.add("method", "VERSIONAPI_GETNEWESTVERSION");
        builder.add("appname", "SJZ");
        builder.add("clienttype", "Android");
        String secret = "4ab4aca1c61c78b89338c3e3804e0e9d" + "VERSIONAPI_GETNEWESTVERSION" + "Android";
        builder.add("secret", Md5Util.getMd5(secret));
        Request request = new Request.Builder().url(API.Palm2_palmapi).post(builder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.failed(e, "IOException");
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String result = response.body().string();
                            byte[] bb = Base64.decode(result, Base64.DEFAULT);
                            final String aa = new String(bb, "utf-8");
                            callBack.success(new JSONObject(aa));
                        } catch (Exception e) {
                            e.printStackTrace();
                            callBack.failed(e, e.getMessage());
                        }
                    }
                });
            }
        });
    }

    public void downLoadApk(final String appName, final String url, final ZhenqiInterface.NetWorkFileDown listener) {
        Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                mOkHttpClients.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException eio) {
                        e.onError(new Throwable("网络请求失败,请检查网络"));
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        InputStream is = null;
                        int len = 0;
                        FileOutputStream fos = null;
                        try {
                            is = response.body().byteStream();
                            File file = new File(Environment.getExternalStorageDirectory(), appName + ".apk");
                            fos = new FileOutputStream(file);
                            byte[] buf = new byte[1024]; // 这个是缓冲区，即一次读取10个比特，我弄的小了点，因为在本地，所以数值太大一
                            // 下就下载完了，看不出progressbar的效果。
                            int ch = -1;
                            int process = 0;
                            while ((ch = is.read(buf)) != -1) {
                                fos.write(buf, 0, ch);
                                process += ch;
                                e.onNext(process / 1024 / 1024); // 这里就是关键的实时更新进度了！
                            }
                            fos.flush();
                            // 下载完成
                            e.onComplete();
                        } catch (Exception e2) {
                            e.onError(new Throwable("下载异常"));
                        } finally {
                            try {
                                if (is != null)
                                    is.close();
                                if (fos != null)
                                    fos.close();
                            } catch (IOException e) {
                            }
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull Object object) {
                        listener.progress((Integer) object);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        listener.failed(new Exception(e.getMessage()), e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        listener.downLoadFinish();
                    }
                });

    }


}
