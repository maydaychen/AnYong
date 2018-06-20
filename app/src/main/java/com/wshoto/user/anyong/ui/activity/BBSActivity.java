package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.webkit.WebView;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BBSActivity extends InitActivity {

    @BindView(R.id.wv_bbs)
    WebView mWvBbs;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_bbs);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        mWvBbs.loadUrl("https://anyong.wshoto.com/html/community.html?uid="+
                getIntent().getStringExtra("id")+"&session="+
                SharedPreferencesUtils.getParam(this, "session", ""));
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }

}
