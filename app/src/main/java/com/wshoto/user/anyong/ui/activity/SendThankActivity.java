package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SendThankActivity extends InitActivity {

    @BindView(R.id.tv_thank_select)
    TextView mTvThankSelect;
    @BindView(R.id.tv_thank_theme)
    TextView mTvThankTheme;
    @BindView(R.id.iv_thank_upload)
    ImageView mIvThankUpload;
    @BindView(R.id.et_thank_content)
    EditText mEtThankContent;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_send_thank);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_thanku_send, R.id.iv_thank_upload,R.id.tv_thank_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_thanku_send:
                break;
            case R.id.iv_thank_upload:
                break;
            case R.id.tv_thank_select:
                startActivity(new Intent(SendThankActivity.this,ThankSelectActivity.class));
                break;
            default:
                break;
        }
    }

}
