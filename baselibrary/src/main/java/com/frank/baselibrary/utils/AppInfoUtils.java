package com.frank.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Frank on 2018/9/10.
 *
 * @fuction 获取应用程序的版本名和版本号
 */
public class AppInfoUtils {

    /*
     *     获取版本名
     */
    public static String getAppInfoName(Context context){

        try {
            //获取packageManager实例
            PackageManager pm=context.getPackageManager();
            //获取版本信息
            PackageInfo packageinfo= pm.getPackageInfo(context.getPackageName(), 0);
            String versionname=packageinfo.versionName;
            return versionname;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }


    /*
     * 获取版本号
     */
    public static int getAppInfoNumber(Context context){

        try {
            //获取packageManager实例
            PackageManager pm=context.getPackageManager();
            //获取版本信息
            PackageInfo packageinfo= pm.getPackageInfo(context.getPackageName(), 0);
            int versionnumber=packageinfo.versionCode;
            return versionnumber;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
}
