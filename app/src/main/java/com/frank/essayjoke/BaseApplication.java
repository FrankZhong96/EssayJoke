package com.frank.essayjoke;

import android.app.Application;

import com.alipay.euler.andfix.patch.PatchManager;
import com.frank.baselibrary.ExceptionCrashHandler;
import com.frank.baselibrary.fixBug.FixDexManager;
import com.frank.baselibrary.utils.AppInfoUtils;

/**
 * Created by Frank on 2018/9/8.
 *
 * @fuction 基类Application
 */
public class BaseApplication extends Application {

    public static PatchManager sPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        //设置全局异常捕获
        ExceptionCrashHandler.getInstance().init(this);
        //初始化热修复 阿里AndFxi
        sPatchManager = new PatchManager(getApplicationContext());
        sPatchManager.init(AppInfoUtils.getAppInfoName(this));//当前版本
        sPatchManager.loadPatch();//加载之前的path包
        // 加载所有修复的 dex包
        try {
            FixDexManager fixDexManager = new FixDexManager(this);
            fixDexManager.loadFixDex();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
