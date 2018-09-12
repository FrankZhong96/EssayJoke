package com.frank.essayjoke;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.baselibrary.ExceptionCrashHandler;
import com.frank.baselibrary.fixBug.FixDexManager;
import com.frank.baselibrary.utils.PermisionUtils;
import com.frank.baselibrary.widgets.dialog.AlertDialog;
import com.frank.framelibrary.BaseSkinActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

        //权限申请
        PermisionUtils.verifyStoragePermissions(this);
//        initException();
//        AliFixBug();
        initFixBug();

    }

    /**
     * 修复bug
     */
    private void initFixBug() {
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.dex");
        if (fixFile.exists()) {
            FixDexManager fixDexManager = new FixDexManager(this);
            try {
                fixDexManager.fixDex(fixFile.getAbsolutePath()) ;
                Toast.makeText(MainActivity.this , "修复成功" , Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this , "修复失败" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void initException() {
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

    /**
     * 阿里andfix热修复
     */
    private void AliFixBug() {
        //测试，直接获取本地内存卡的fix.aptach
        File fixFile = new File(Environment.getExternalStorageDirectory(), "fix.apatch");
        if (fixFile.exists()) {
            try {
                //修复bug
                BaseApplication.sPatchManager.addPatch(fixFile.getAbsolutePath());
                Toast.makeText(this, "修复成功", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "修复失败", Toast.LENGTH_LONG).show();
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

                break;
            case R.id.iv_image:
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setContentView(R.layout.detail_comment_dialog)
                        .setOnClickListener(R.id.submit_btn, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this,"SUBMIT",Toast.LENGTH_LONG).show();
                            }
                        })
                        .formBottom(false).fullWidth().show();
                break;
        }
    }

//    @OnClick(R.id.tv_text)
//    public void onViewClicked() {
//        Toast.makeText(this,"12",Toast.LENGTH_LONG).show();
//    }
}
