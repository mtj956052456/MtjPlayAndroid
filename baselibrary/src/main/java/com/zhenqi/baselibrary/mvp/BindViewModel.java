package com.zhenqi.baselibrary.mvp;

/**
 * @author mtj
 * @time 2019/6/8 2019 06
 * @des
 */
public interface BindViewModel<M extends BaseModel> {

    M createModel();

//    void onSuccess();
//
//    void onError(String errMsg);

}
