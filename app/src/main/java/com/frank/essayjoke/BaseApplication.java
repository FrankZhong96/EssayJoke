package com.frank.essayjoke;

import android.app.Application;

import com.frank.baselibrary.ExceptionCrashHandler;

/**
 * Created by Frank on 2018/9/8.
 *
 * @fuction 基类Application
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局异常捕获
        ExceptionCrashHandler.getInstance().init(this);
    }
}
