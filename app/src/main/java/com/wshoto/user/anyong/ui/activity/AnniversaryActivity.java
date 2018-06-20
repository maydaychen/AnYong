package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnniversaryActivity extends InitActivity {

    @BindView(R.id.wv_anni)
    WebView mWvAnni;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_anniversary);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        mWvAnni.loadUrl("https://anyong.wshoto.com/html/annual.html?uid="+
                getIntent().getStringExtra("id")+"&session="+
                SharedPreferencesUtils.getParam(this, "session", ""));
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }

}
