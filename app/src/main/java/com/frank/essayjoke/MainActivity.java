package com.frank.essayjoke;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.baselibrary.ExceptionCrashHandler;
import com.frank.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseSkinActivity {

    @BindView(R.id.tv_text)
    TextView mTvText;
    @BindView(R.id.iv_image)
    ImageView mIvImage;

    @Override
    protected void initData() {
        //每次运行程序都会获取上次的崩溃信息
        File crashFile = ExceptionCrashHandler.getInstance().getCrashFile();
        if (crashFile.exists()) {
            //将保存的崩溃日志读取出来并打印，就知道崩溃具体信息，然后处理
            try {
                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(crashFile));
                char[] buffer = new char[1024];
                int len = 0;
                while ((len = fileReader.read(buffer)) != -1) {
                    String str = new String(buffer, 0, len);
                    Log.d("=====崩溃信息打印====", str + "--------");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_text, R.id.iv_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_text:
                Toast.makeText(this,"12",Toast.LENGTH_LONG).show();
                break;
            case R.id.iv_image:
                Toast.makeText(this,"13",Toast.LENGTH_LONG).show();
                break;
        }
    }

//    @OnClick(R.id.tv_text)
//    public void onViewClicked() {
//        Toast.makeText(this,"12",Toast.LENGTH_LONG).show();
//    }
}
