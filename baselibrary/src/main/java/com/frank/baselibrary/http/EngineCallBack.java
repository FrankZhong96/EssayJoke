package com.frank.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by Frank on 2018/9/15.
 *
 * @fuction
 */
public interface EngineCallBack {

    /**
     * 开始执行，在执行之前会回调的方法
     * @param context
     * @param params
     */
    void onPreExecute(Context context, Map<String,Object> params);

    /**
     * 网络请求成功
     * @param result
     */
    void onSuccess(String result);

    /**
     * 网络请求失败
     * @param e
     */
    void onError(Exception e);

    /**
     * 默认，什么都不干
     */
    EngineCallBack DEFAULT_CALL_BACK = new EngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onSuccess(String result) {

        }

        @Override
        public void onError(Exception e) {

        }
    };
}
