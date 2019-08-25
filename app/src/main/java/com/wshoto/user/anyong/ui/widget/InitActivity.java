package com.wshoto.user.anyong.ui.widget;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.umeng.message.PushAgent;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;

import java.util.Locale;

/**
 * Created by user on 2018/4/26.
 * 2091320109@qq.com
 * 封装的模板activity
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
        if ((boolean) SharedPreferencesUtils.getParam(this, "language_auto", true)) {
            String country = Locale.getDefault().getCountry();
            if ("CN".equals(country)) {
                selectLanguage("zh");
            } else {
                selectLanguage("en");
            }
        } else {
            selectLanguage((String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        }
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.onAppStart();
        initView(savedInstanceState);
        initData();
    }

    /**
     * 设置要显示的语言
     *
     * @param language zh或者en，中文或者英文
     */
    protected void selectLanguage(String language) {
        //设置语言类型
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        switch (language) {
            case "en":
                configuration.locale = Locale.ENGLISH;
                break;
            case "zh":
                configuration.locale = Locale.SIMPLIFIED_CHINESE;
                break;
            default:
                configuration.locale = Locale.getDefault();
                break;
        }
        resources.updateConfiguration(configuration, displayMetrics);
//
//        //保存设置语言的类型
        SharedPreferencesUtils.setParam(getApplicationContext(), "language", language);
    }

    public void goActivity(Class c) {
        startActivity(new Intent(this, c));
    }
}
