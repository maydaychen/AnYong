package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankPreviewActivity extends InitActivity {

    @BindView(R.id.wv_preview)
    WebView wvPreview;
    private SubscriberOnNextListener<JSONObject> sendOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thank_preview);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void initData() {
        Log.i("chenyi", "initData: " + "https://anyong.wshoto.com/html/thankU.html?id=" + getIntent().getIntExtra("id", 0));
        wvPreview.loadUrl("https://anyong.wshoto.com/html/thankU.html?id=" + getIntent().getIntExtra("id", 0));
        sendOnNext = jsonObject -> {
//            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(getApplicationContext(), getText(R.string.send_sussess), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ThankPreviewActivity.this, ThankYouActivity.class));
//            } else {
//                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
//            }
        };
    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_thanku_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_thanku_send:
                HttpJsonMethod.getInstance().sendThank(
                        new ProgressSubscriber(sendOnNext, ThankPreviewActivity.this),
                        getIntent().getIntExtra("id", 0) + "",
                        (String) SharedPreferencesUtils.getParam(this, "session", ""),
                        (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
                break;
        }
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
        webSettings.setDatabasePath(ThankPreviewActivity.this.getApplicationContext().getCacheDir().getAbsolutePath());
        //add by wjj end
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + ";wshoto");
        wvPreview.setWebChromeClient(new WebChromeClient());
    }
}
