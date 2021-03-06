package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.RadioGroup;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageActivity extends InitActivity {


    @BindView(R.id.rg_language)
    RadioGroup rgLanguage;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_language);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

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
//        //保存设置语言的类型
        SharedPreferencesUtils.setParam(getApplicationContext(), "language", language);
    }

    @OnClick({R.id.iv_comfirm_back, R.id.button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                //返回
                finish();
                break;
            case R.id.button:
                switch (rgLanguage.getCheckedRadioButtonId()) {
                    case R.id.rb_chinese:
                        //设置中文
                        selectLanguage("zh");
                        SharedPreferencesUtils.setParam(getApplicationContext(), "language_auto", false);
                        break;
                    case R.id.rb_english:
                        //设置英文
                        selectLanguage("en");
                        SharedPreferencesUtils.setParam(getApplicationContext(), "language_auto", false);
                        break;
                    case R.id.rb_auto:
                        //设置根据系统语言自动设置
                        SharedPreferencesUtils.setParam(getApplicationContext(), "language_auto", true);
                        String country = Locale.getDefault().getCountry();
                        if ("CN".equals(country)) {
                            selectLanguage("zh");
                        } else {
                            selectLanguage("en");
                        }
                        break;
                }
                //设置完成后回到Main2Activity，重构页面
                Intent intent = new Intent(LanguageActivity.this, Main2Activity.class);
                startActivity(intent);
                break;
        }

    }
}
