package com.wshoto.user.anyong.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.message.PushAgent;
import com.wshoto.user.anyong.Bean.UpdateBean;
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

import static com.wshoto.user.anyong.Utils.compareVersion;

public class LoginActivity extends InitActivity {

    @BindView(R.id.et_login_mail)
    EditText mEtLoginMail;
    @BindView(R.id.et_login_pass)
    EditText mEtLoginPass;
    @BindView(R.id.et_invite)
    EditText etInvite;
    private SubscriberOnNextListener<JSONObject> LoginOnNext;
    private SubscriberOnNextListener<JSONObject> infoOnNext;
    private UpdateBean mUpdateBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if ((boolean) SharedPreferencesUtils.getParam(this, "autolog", false)) {
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        if ((boolean) SharedPreferencesUtils.getParam(this, "first", true)) {
            InstructionsDialog();
//            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getSupportActionBar().hide();
        mEtLoginMail.setText((String) SharedPreferencesUtils.getParam(this, "username", ""));
        mEtLoginPass.setText((String) SharedPreferencesUtils.getParam(this, "pass", ""));
        infoOnNext = jsonObject -> {
            mUpdateBean = mGson.fromJson(jsonObject.toString(), UpdateBean.class);
            checkUpdate(mUpdateBean.getData().getVandroid());
        };

    }

    @Override
    public void initData() {
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.onAppStart();
        LoginOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                SharedPreferencesUtils.setParam(getApplicationContext(), "session", jsonObject.getJSONObject("data").getString("session"));
                SharedPreferencesUtils.setParam(getApplicationContext(), "username", mEtLoginMail.getText().toString());
                SharedPreferencesUtils.setParam(getApplicationContext(), "pass", mEtLoginPass.getText().toString());
                SharedPreferencesUtils.setParam(getApplicationContext(), "autolog", true);
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
        HttpJsonMethod.getInstance().update(
                new ProgressSubscriber(infoOnNext, LoginActivity.this));
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
                if (name.equals("") || passs.equals("")) {
                    Toast.makeText(this, getText(R.string.step1_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!etInvite.getText().toString().equals("") && etInvite.getText().toString().length() != 11) {
                    Toast.makeText(LoginActivity.this, getText(R.string.step1_gpn), Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpJsonMethod.getInstance().login(
                        new ProgressSubscriber(LoginOnNext, LoginActivity.this), name, passs,
                        (String) SharedPreferencesUtils.getParam(this, "language", "zh"),
                        (String) SharedPreferencesUtils.getParam(this, "device_token", ""), etInvite.getText().toString());

                break;
            default:
                break;
        }
    }

    private void checkUpdate(String nowVersion) {
        String version = "";
        try {
            PackageManager manager = getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (compareVersion(version, nowVersion) < 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage(getText(R.string.update));
            builder.setTitle(R.string.app_name);
            builder.setCancelable(false);

            builder.setPositiveButton(getText(R.string.confirm), (dialog, which) -> {
                dialog.dismiss();
            });

            builder.setNegativeButton(getText(R.string.cancel), (dialog, which) -> dialog.dismiss());

            builder.create().show();
        }
    }

    public void InstructionsDialog() {

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(getText(R.string.dialog_name));
        ad.setView(LayoutInflater.from(this).inflate(R.layout.instructions_dialog, null));
        ad.setCancelable(false);

        ad.setPositiveButton(getText(R.string.dialog_agree), (dialog, which) -> {
            SharedPreferencesUtils.setParam(getApplicationContext(), "first", false);
            Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
            intent.putExtra("login", true);
            startActivity(intent);
        });


        ad.show();
    }
}
