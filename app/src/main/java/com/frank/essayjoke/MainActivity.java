package com.frank.essayjoke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.frank.baselibrary.ioc.CheckNet;
import com.frank.baselibrary.ioc.OnClick;
import com.frank.baselibrary.ioc.ViewById;
import com.frank.baselibrary.ioc.ViewUtils;

public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.tv_text)
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewUtils.inject(this);
        mTextView.setText("IOC");
    }
    @OnClick({R.id.tv_text,R.id.iv_image})
    @CheckNet
    private void OnClick(View v){
        Toast.makeText(this,"test",Toast.LENGTH_LONG).show();
    }
}
