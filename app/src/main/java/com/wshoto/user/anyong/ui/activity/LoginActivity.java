package com.wshoto.user.anyong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends InitActivity {

    @BindView(R.id.et_login_mail)
    EditText mEtLoginMail;
    @BindView(R.id.et_login_pass)
    EditText mEtLoginPass;
    private SubscriberOnNextListener<JSONObject> LoginOnNext;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if ((boolean) SharedPreferencesUtils.getParam(this, "autolog", false)) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getSupportActionBar().hide();
        mEtLoginMail.setText( (String) SharedPreferencesUtils.getParam(this, "username", ""));
        mEtLoginPass.setText( (String) SharedPreferencesUtils.getParam(this, "pass", ""));
    }

    @Override
    public void initData() {
        mPreferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();

        LoginOnNext = jsonObject -> {
            if (jsonObject.getInt("code")==1) {
                if ((boolean) SharedPreferencesUtils.getParam(this, "first", true)) {
                    Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }else {
                    SharedPreferencesUtils.setParam(getApplicationContext(), "session", jsonObject.getJSONObject("data").getString("session"));
                    SharedPreferencesUtils.setParam(getApplicationContext(), "username",  mEtLoginMail.getText().toString());
                    SharedPreferencesUtils.setParam(getApplicationContext(), "pass", mEtLoginPass.getText().toString());
                    SharedPreferencesUtils.setParam(getApplicationContext(), "autolog",true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }else {
                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };

    }

    @OnClick({R.id.tv_login_first, R.id.tv_login_forget, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_first:
                startActivity(new Intent(LoginActivity.this, ConfirmActivity.class));
                break;
            case R.id.tv_login_forget:
                startActivity(new Intent(LoginActivity.this, ChangePassActivity.class));
                break;
            case R.id.tv_login:
                String name = mEtLoginMail.getText().toString();
                String passs = mEtLoginPass.getText().toString();
                if (name.equals("")||passs.equals("")) {
                    Toast.makeText(this, "请将信息填写完整！", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpJsonMethod.getInstance().login(
                        new ProgressSubscriber(LoginOnNext, LoginActivity.this), name, passs);

                break;
            default:
                break;
        }
    }
}
