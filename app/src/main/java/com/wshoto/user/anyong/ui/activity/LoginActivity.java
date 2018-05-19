package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.wshoto.user.anyong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_login_mail)
    EditText mEtLoginMail;
    @BindView(R.id.et_login_pass)
    EditText mEtLoginPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        getSupportActionBar().hide();
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
                finish();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                break;
            default:
                break;
        }
    }
}
