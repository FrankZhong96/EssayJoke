package com.frank.baselibrary.widgets.navigationbar;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Frank on 2018/9/14.
 *
 * @fuction NavigationBar的基类
 */
public abstract class AbsNavigationBar<P extends AbsNavigationBar.Builder.AbsNavigationBarParams> implements INavigationBar {

    private P mParams;
    private View mNavigationView;

    public AbsNavigationBar(P params) {
        this.mParams = params;
        creatAndBindView();
    }

    public P getParams() {
        return mParams;
    }

    /**
     * 设置文本
     * @param viewId
     * @param text
     */
    protected void setText(int viewId, String text) {
        TextView tv = findViewById(viewId);
        if(!TextUtils.isEmpty(text)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }
    }

    /**
     * 设置点击
     * @param viewId
     * @param listener
     */
    protected void setOnClickListener(int viewId,View.OnClickListener listener){
        findViewById(viewId).setOnClickListener(listener);
    }

    public <T extends View> T findViewById(int viewId){
        return (T)mNavigationView.findViewById(viewId);
    }

    /**
     * 绑定创建View
     */
    private void creatAndBindView() {
        // 1. 创建View

        if(mParams.mParent == null){
            // 获取activity的根布局，View源码
            // 方法1：android.R.id.content就是 android.R.layout.screen_simple中的一个 FrameLayout的一个id
//            ViewGroup activityRoot = (ViewGroup) ((Activity)(mParams.mContext))
//                    .findViewById(android.R.id.content);

            // 方法2：.getWindow().getDecorView() 表示直接加载的就是 android.R.layout.screen_simple的根布局，而根布局就是一个 LinearLayout
            // 所以不管 activity_main中的根布局是RelativeLayout、LinearLayout都可以
            ViewGroup activityRoot = (ViewGroup) ((Activity)(mParams.mContext))
                    .getWindow().getDecorView() ;

            mParams.mParent = (ViewGroup) activityRoot.getChildAt(0);

        }

        // 处理Activity的源码，后面再去看
        if(mParams.mParent == null){
            return;
        }

        mNavigationView = LayoutInflater.from(mParams.mContext).
                inflate(bindLayoutId(), mParams.mParent, false);

        // 2.添加
        mParams.mParent.addView(mNavigationView, 0);

        applyView();
    }

    //Builder
    public abstract static class Builder {

        AbsNavigationBarParams P;

        public Builder(Context context, ViewGroup parent) {
            P = new AbsNavigationBarParams(context, parent);
        }

        public abstract AbsNavigationBar builder();

        public static class AbsNavigationBarParams {

            public Context mContext;
            public ViewGroup mParent;

            public AbsNavigationBarParams(Context context, ViewGroup parent) {
                this.mContext = context;
                this.mParent = parent;
            }

        }
    }

}
