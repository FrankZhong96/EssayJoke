package com.frank.baselibrary.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by Frank on 2018/9/7.
 *
 * @fuction Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据
        initData();
    }

    //初始化数据
    protected abstract void initData();

    /**
     * 启动activity
     *
     * @param c
     */
    protected void startActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    /**
     * findViewById
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T viewById(int viewId) {
        return (T) findViewById(viewId);
    }

}
