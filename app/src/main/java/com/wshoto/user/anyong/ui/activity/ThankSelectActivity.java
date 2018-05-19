package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankSelectActivity extends InitActivity {

    @BindView(R.id.rv_thank_select)
    RecyclerView mRvThankSelect;
    @BindView(R.id.et_thank_select)
    EditText mEtThankSelect;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thank_select);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_select_back, R.id.iv_select_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_back:
                finish();
                break;
            case R.id.iv_select_clear:
                mEtThankSelect.setText("");
                break;
            default:
                break;
        }
    }

}
