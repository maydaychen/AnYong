package com.wshoto.user.anyong.ui.widget;

import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.wshoto.user.anyong.R;

/**
 * Created by user on 2018/4/26.
 * 2091320109@qq.com
 */

public abstract class InitActivity extends AppCompatActivity {
    public abstract void initView(Bundle savedInstanceState);

    public abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            StatusBarCompat.compat(this, ContextCompat.getColor(this, R.color.title));
        }
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initView(savedInstanceState);
        initData();
    }
}
