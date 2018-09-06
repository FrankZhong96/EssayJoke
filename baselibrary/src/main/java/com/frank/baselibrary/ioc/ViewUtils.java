package com.frank.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Frank on 2018/9/6.
 *
 * @fuction IOC 注入 ViewUtils
 */
public class ViewUtils {

    //Activity
    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    // 兼容View
    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    // 兼容Fragment
    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    //兼容上面三个方法 object-->反射需要执行的类
    public static void inject(ViewFinder viewFinder, Object object) {
        injectField(viewFinder, object);
        injectEvent(viewFinder, object);
    }

    /**
     * 注入属性
     *
     * @param viewFinder
     * @param object
     */
    private static void injectField(ViewFinder viewFinder, Object object) {
        //1.获取类里面所有的属性
        Class<?> aClass = object.getClass();
        //获取所有属性,包括私有和公有
        Field[] fields = aClass.getDeclaredFields();
        //2.获取ViewById里面的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里的id值
                int viewId = viewById.value();
                //3.findViewById 找到view
                View view = viewFinder.findViewById(viewId);
                if (view != null) {
                    field.setAccessible(true);//能够注入所有修饰符 private public ...
                    //4.动态的注入找到view
                    try {
                        field.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 注入事件
     *
     * @param viewFinder
     * @param object
     */
    private static void injectEvent(ViewFinder viewFinder, Object object) {
        //1.获取类里所有的方法
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        // 2.获取方法上面的所有id
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                // 3.遍历所有的id 先findViewById然后 setOnClickListener
                for (int viewId : viewIds) {
                    View view = viewFinder.findViewById(viewId);
                    //扩展功能 检测网络
                    Boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    if (view != null) {
                        view.setOnClickListener(new DeclaredOnClickListener(method, object,isCheckNet));
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Method mMethod;
        private Object mObject;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object,boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            //是否需要检测网络
            if(mIsCheckNet){//需要
                if (!networkAvailable(v.getContext())){
                    //提示不应该写死 后续优化
                    Toast.makeText(v.getContext(),"亲，您的网络不太给力",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //点击会调用该方法
            try {
                mMethod.setAccessible(true);//能够注入所有方法 private public ...
                //4.反射执行方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    //4.反射执行方法
                    mMethod.invoke(mObject, null);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static boolean networkAvailable(Context context){
        //得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()){
            return true;
        }else {
            return false;
        }
    }

}
