package com.wshoto.user.anyong;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        SDKInitializer.initialize(getApplicationContext());
        super.onCreate();
    }
}
