package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

public class NewFriendActivity extends InitActivity {
    private SubscriberOnNextListener<JSONObject> listOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_friend);

    }

    @Override
    public void initData() {
        listOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                //todo 好友申请列表
            } else {
                Toast.makeText(NewFriendActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };

        HttpJsonMethod.getInstance().myRadiusList(
                new ProgressSubscriber(listOnNext, NewFriendActivity.this), (String) SharedPreferencesUtils.getParam(this, "session", ""));
    }


}
