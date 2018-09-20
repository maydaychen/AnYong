package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.Utils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

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
    private SubscriberOnNextListener<JSONObject> sendOnNext;
    private SubscriberOnNextListener<JSONObject> changeOnNext;
    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_pass);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        sendOnNext = resultBean -> {
            if (resultBean.getInt("code") == 1) {
                Toast.makeText(ChangePassActivity.this, getText(R.string.send_sussess), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ChangePassActivity.this, resultBean.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
        changeOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(ChangePassActivity.this, getText(R.string.change_success), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePassActivity.this,LoginActivity.class));
            } else {
                Toast.makeText(ChangePassActivity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
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
                    HttpJsonMethod.getInstance().sendCode(
                            new ProgressSubscriber(sendOnNext, ChangePassActivity.this), tele ,
                            "fix",(String) SharedPreferencesUtils.getParam(this, "language", "zh"));
                } else {
                    Toast.makeText(ChangePassActivity.this, getText(R.string.mobile_hint), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_login:
                HttpJsonMethod.getInstance().forget(
                        new ProgressSubscriber(changeOnNext, ChangePassActivity.this),
                        mEtLoginMail.getText().toString(), mEtLoginYanzheng.getText().toString(),
                        mEtLoginPass.getText().toString(), mEtLoginPass2.getText().toString(),
                        (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
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
                mTvGetEms.setText(getText(R.string.tv_change_pass_sms_send));
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
