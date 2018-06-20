package com.wshoto.user.anyong.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.Bean.UserInfoBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends InitActivity {
    private SubscriberOnNextListener<JSONObject> logoutOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        logoutOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                SharedPreferencesUtils.clear(getApplicationContext());
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                Toast.makeText(SettingActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_logout, R.id.tv_language_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_logout:
                show();
                break;
            case R.id.tv_language_setting:
                startActivity(new Intent(SettingActivity.this, LanguageActivity.class));

        }
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setMessage("确定要退出么？");
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton("确认", (dialog, which) -> {
            dialog.dismiss();
            HttpJsonMethod.getInstance().logout(
                    new ProgressSubscriber(logoutOnNext, SettingActivity.this), (String) SharedPreferencesUtils.getParam(this, "session", ""));
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
