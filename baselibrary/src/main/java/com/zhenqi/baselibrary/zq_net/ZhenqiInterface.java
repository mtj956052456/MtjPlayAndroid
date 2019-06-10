package com.zhenqi.baselibrary.zq_net;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class ZhenqiInterface {


    public interface NetWorkResponse{
        void success(JSONObject result);
        void failed(Exception e, String excpetion);
    }

    public interface NetWorkResponseArray{
        void success(JSONArray result);
        void failed(Exception e, String excpetion);
    }

    public interface NetWorkResponseString{
        void success(boolean result);
        void failed(Exception e, String excpetion);
    }

    public interface NetWorkFileDown{

        /**
         * 下载完成
         */
        void downLoadFinish();

        /**
         * 下载进度
         * @param progress
         */
        void progress(int progress);

        /**
         * 下载异常
         * @param e
         * @param excpetion
         */
        void failed(Exception e, String excpetion);
    }
}
