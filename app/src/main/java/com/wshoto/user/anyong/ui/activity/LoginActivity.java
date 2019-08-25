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
    private SubscriberOnNextListener<JSONObject> LoginOnNext;//登录接口回调
    private SubscriberOnNextListener<JSONObject> infoOnNext;//用户信息接口回调
    private UpdateBean mUpdateBean;
    private Gson mGson = new Gson();//Gson转换要用到

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        //黄油刀注解
        ButterKnife.bind(this);
        //自动登录
        if ((boolean) SharedPreferencesUtils.getParam(this, "autolog", false)) {
            Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        //授权同意框
        if ((boolean) SharedPreferencesUtils.getParam(this, "first", true)) {
            InstructionsDialog();
//            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        //设置沉浸式状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getSupportActionBar().hide();
        //用户名跟密码设置成保存的值，不过好像用不到了……都是自动登录
        mEtLoginMail.setText((String) SharedPreferencesUtils.getParam(this, "username", ""));
        mEtLoginPass.setText((String) SharedPreferencesUtils.getParam(this, "pass", ""));
        infoOnNext = jsonObject -> {
            mUpdateBean = mGson.fromJson(jsonObject.toString(), UpdateBean.class);
            checkUpdate(mUpdateBean.getData().getVandroid());
        };

    }

    @Override
    public void initData() {
        //注册友盟推送服务
        PushAgent pushAgent = PushAgent.getInstance(this);
        pushAgent.onAppStart();
        LoginOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                //登录成功以后，设置以后都是自动登录，并跳转到首页
                SharedPreferencesUtils.setParam(getApplicationContext(), "session", jsonObject.getJSONObject("data").getString("session"));
                SharedPreferencesUtils.setParam(getApplicationContext(), "username", mEtLoginMail.getText().toString());
                SharedPreferencesUtils.setParam(getApplicationContext(), "pass", mEtLoginPass.getText().toString());
                SharedPreferencesUtils.setParam(getApplicationContext(), "autolog", true);
                Intent intent = new Intent(LoginActivity.this, Main2Activity.class);
                //设置flage，将登录页移除栈
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                //登陆失败，弹出原因
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
                //认证页
                startActivity(new Intent(LoginActivity.this, ConfirmActivity.class));
                break;
            case R.id.tv_login_forget:
                //修改密码页面
                startActivity(new Intent(LoginActivity.this, ChangePassActivity.class));
                break;
            case R.id.tv_login:
                //点击登录
                String name = mEtLoginMail.getText().toString();
                String passs = mEtLoginPass.getText().toString();
                if (name.equals("") || passs.equals("")) {
                    Toast.makeText(this, getText(R.string.step1_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断邀请码是不是乱输的
                if (!etInvite.getText().toString().equals("") && etInvite.getText().toString().length() != 11) {
                    Toast.makeText(LoginActivity.this, getText(R.string.step1_gpn), Toast.LENGTH_SHORT).show();
                    return;
                }
                //登录接口
                HttpJsonMethod.getInstance().login(
                        new ProgressSubscriber(LoginOnNext, LoginActivity.this), name, passs,
                        (String) SharedPreferencesUtils.getParam(this, "language", "zh"),
                        (String) SharedPreferencesUtils.getParam(this, "device_token", ""), etInvite.getText().toString());

                break;
            default:
                break;
        }
    }

    //判断现在的版本号是否为最新的版本号
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

    /**
     * Instructions dialog.
     * 跳出用户协议授权框
     */
    public void InstructionsDialog() {
        //弹出用户授权协议，以alertDialog形式弹出
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle(getText(R.string.dialog_name));
        ad.setView(LayoutInflater.from(this).inflate(R.layout.instructions_dialog, null));
        ad.setCancelable(false);
        //点击我同意，跳转到引导页
        ad.setPositiveButton(getText(R.string.dialog_agree), (dialog, which) -> {
            SharedPreferencesUtils.setParam(getApplicationContext(), "first", false);
            Intent intent = new Intent(LoginActivity.this, GuideActivity.class);
            intent.putExtra("login", true);
            startActivity(intent);
        });
        ad.show();
    }
}
