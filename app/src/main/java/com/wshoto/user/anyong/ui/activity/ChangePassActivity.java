package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePassActivity extends InitActivity {

    @BindView(R.id.et_login_mobile)
    EditText mTvStep2Mobile;
    @BindView(R.id.tv_get_sms)
    TextView mTvGetEms;
    @BindView(R.id.et_login_code)
    EditText mTvGetCode;
    @BindView(R.id.sp_location)
    Spinner mSpLocation;
    @BindView(R.id.et_login_pass)
    EditText etLoginPass;
    @BindView(R.id.et_login_pass_again)
    EditText etLoginPassAgain;

    private int recLen = 60;
    private boolean flag = true;
    private String code = "";
    private SubscriberOnNextListener<JSONObject> sendOnNext;
    private SubscriberOnNextListener<JSONObject> changeOnNext;
    List<String> list = new ArrayList();

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
                startActivity(new Intent(ChangePassActivity.this, LoginActivity.class));
            } else {
                Toast.makeText(ChangePassActivity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };

        list.add("+86");
        list.add("+886");
        list.add("+852");
        list.add("+853");
        code = list.get(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ChangePassActivity.this, android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        mSpLocation.setAdapter(adapter);
        mSpLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //showPrice(position);
                code = list.get(position);
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.yellow));    //设置颜色
                tv.setTextSize(14.0f);    //设置大小
                tv.setGravity(Gravity.CENTER_HORIZONTAL);   //设置居中
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_get_sms, R.id.tv_step1_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_get_sms:
                String tele = mTvStep2Mobile.getText().toString();
                if (flag) {
                    flag = false;
                    mTvGetEms.setClickable(false);
                    handler.post(runnable);
                    HttpJsonMethod.getInstance().sendCode(
                            new ProgressSubscriber(sendOnNext, ChangePassActivity.this), tele, "bind",
                            (String) SharedPreferencesUtils.getParam(ChangePassActivity.this, "language", "zh"),
                            code.substring(1));
                } else {
                    Toast.makeText(ChangePassActivity.this, getText(R.string.mobile_hint), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_step1_next:
                if (mTvStep2Mobile.getText().toString().equals("") || mTvGetCode.getText().toString().equals("") ||
                        etLoginPass.getText().toString().equals("") || etLoginPassAgain.getText().toString().equals("")) {
                    Toast.makeText(ChangePassActivity.this, getText(R.string.step1_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!etLoginPassAgain.getText().toString().equals(etLoginPass.getText().toString())) {
                    Toast.makeText(ChangePassActivity.this, getText(R.string.step1_pass), Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpJsonMethod.getInstance().forget(
                        new ProgressSubscriber(changeOnNext, ChangePassActivity.this),
                        mTvStep2Mobile.getText().toString(), mTvGetCode.getText().toString(),
                        etLoginPassAgain.getText().toString(), etLoginPassAgain.getText().toString(),
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
