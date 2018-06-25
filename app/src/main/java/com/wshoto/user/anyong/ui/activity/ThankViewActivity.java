package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankViewActivity extends InitActivity {

    @BindView(R.id.wv_preview)
    WebView wvPreview;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thank_view);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        Log.i("chenyi", "initData: " + "https://anyong.wshoto.com/html/thankU.html?id=" + getIntent().getStringExtra("id"));
        wvPreview.loadUrl("https://anyong.wshoto.com/html/thankU.html?id=" + getIntent().getStringExtra("id"));
//        wvPreview.loadUrl("https://anyong.wshoto.com/html/thankU.html?id=4");
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }
}
