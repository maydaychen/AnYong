package com.wshoto.user.anyong.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

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
                boolean auto = (boolean) SharedPreferencesUtils.getParam(this, "language_auto", true);
                String lang = (String) SharedPreferencesUtils.getParam(this, "language", "zh");
                String device_token = (String) SharedPreferencesUtils.getParam(this, "device_token", "");
                SharedPreferencesUtils.clear(getApplicationContext());
                SharedPreferencesUtils.setParam(getApplicationContext(), "first", false);
                SharedPreferencesUtils.setParam(getApplicationContext(), "language_auto", auto);
                SharedPreferencesUtils.setParam(getApplicationContext(), "language", lang);
                SharedPreferencesUtils.setParam(getApplicationContext(), "device_token", device_token);
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
        builder.setMessage(getText(R.string.exit_hint));
        builder.setTitle(R.string.app_name);

        builder.setPositiveButton(getText(R.string.confirm), (dialog, which) -> {
            dialog.dismiss();
            HttpJsonMethod.getInstance().logout(
                    new ProgressSubscriber(logoutOnNext, SettingActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        });

        builder.setNegativeButton(getText(R.string.cancel), (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
