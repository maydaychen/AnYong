package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class AnniversaryActivity extends InitActivity {

    @BindView(R.id.wv_anni)
    BridgeWebView mWvAnni;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_anniversary);
        ButterKnife.bind(this);
//        init();
        initWebview();
    }

    @Override
    public void initData() {
        mWvAnni.loadUrl("https://anyong.wshoto.com/html/annual.html?uid=" +
                getIntent().getStringExtra("id") + "&session=" +
                SharedPreferencesUtils.getParam(this, "session", "") +
                "&lang=" + SharedPreferencesUtils.getParam(this, "language", "zh"));


    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }

    private void showShare(String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getResources().getString(R.string.app_name));
        // text是分享文本，所有平台都需要这个字段
//        oks.setText(getResources().getString(R.string.app_name));
        oks.setText("");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath(fileName);//确保SDcard下面存在此张图片
        oks.setImagePath("file:///android_asset/logo.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(url);//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://www.pgyer.com/RYqN");
        oks.setTitleUrl("https://www.pgyer.com/RYqN");
        oks.setSiteUrl("https://www.pgyer.com/RYqN");
//        oks.setUrl("http://www.baidu.com");
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {

            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(Platform platform, int i) {

            }
        });
        // 启动分享GUI

        oks.show(this);

    }

    private void initWebview() {
        WebSettings webSettings = mWvAnni.getSettings();
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
        mWvAnni.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //open dom storage
        webSettings.setDomStorageEnabled(true);
        //priority high
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        //add by chenyi end
        String ua = webSettings.getUserAgentString();
        webSettings.setUserAgentString(ua + ";wshoto");
        mWvAnni.setDefaultHandler(new DefaultHandler());
        mWvAnni.setWebChromeClient(new WebChromeClient());
        mWvAnni.setWebViewClient(new BridgeWebViewClient(mWvAnni) {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("chenyi", "shouldOverrideUrlLoading: " + url);
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            }
        });
        mWvAnni.registerHandler("share", (responseData, function) -> {
            Log.i("chenyi", "getLocation: start");
            try {
                JSONObject jsonObject = new JSONObject(responseData);
                showShare(jsonObject.getString("url"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
      
    }
}
