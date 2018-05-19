package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.Utils;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassActivity extends InitActivity {

    @BindView(R.id.et_login_mail)
    EditText mEtLoginMail;
    @BindView(R.id.tv_get_ems)
    TextView mTvGetEms;
    @BindView(R.id.et_login_yanzheng)
    EditText mEtLoginYanzheng;
    @BindView(R.id.et_login_pass)
    EditText mEtLoginPass;
    @BindView(R.id.et_login_pass2)
    EditText mEtLoginPass2;
    private int recLen = 60;
    private boolean flag = true;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_get_ems, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_get_ems:
                String tele = mEtLoginMail.getText().toString();
                if (flag && Utils.isChinaPhoneLegal(tele)) {
                    flag = false;
                    mTvGetEms.setClickable(false);
                    handler.post(runnable);
                } else {
                    Toast.makeText(ChangePassActivity.this, "请填写手机号！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_login:
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen >= 1) {
                recLen--;
                mTvGetEms.setText(recLen + "");
                handler.postDelayed(this, 1000);
            } else {
                flag = true;
                recLen = 60;
                mTvGetEms.setClickable(true);
                mTvGetEms.setText("获取验证码");
            }
        }
    };
}
