package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmSuccessActivity extends InitActivity {

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm_success);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            startActivity(new Intent(ConfirmSuccessActivity.this, LoginActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.iv_comfirm_back, R.id.iv_confirm_success})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                startActivity(new Intent(ConfirmSuccessActivity.this, LoginActivity.class));
                break;
            case R.id.iv_confirm_success:
                startActivity(new Intent(ConfirmSuccessActivity.this, LoginActivity.class));
                break;
            default:
                break;
        }
    }
}
