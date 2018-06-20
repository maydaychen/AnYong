package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class MyRadiiActivity extends InitActivity {
    private SubscriberOnNextListener<JSONObject> listOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_radii);

    }

    @Override
    public void initData() {
        listOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
             //todo 半径列表
            } else {
                Toast.makeText(MyRadiiActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };

        HttpJsonMethod.getInstance().myRadiusList(
                new ProgressSubscriber(listOnNext, MyRadiiActivity.this), (String) SharedPreferencesUtils.getParam(this, "session", ""));
    }
}
