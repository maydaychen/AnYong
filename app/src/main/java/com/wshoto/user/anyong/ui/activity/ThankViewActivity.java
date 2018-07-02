package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
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
        init();
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

    private void init() {
        WebSettings webSettings = wvPreview.getSettings();
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webSettings.setAppCachePath(appCachePath);
        webSettings.setAllowFileAccess(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setTextSize(WebSettings.TextSize.NORMAL);

        wvPreview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //open dom storage
        webSettings.setDomStorageEnabled(true);
        //priority high
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDatabasePath(ThankViewActivity.this.getApplicationContext().getCacheDir().getAbsolutePath());
        //add by wjj end
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + ";wshoto");
        wvPreview.setWebChromeClient(new WebChromeClient());
    }
}
