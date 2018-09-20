package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.MessageCenterBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.MessageCenterAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageCenterActivity extends InitActivity {

    @BindView(R.id.rv_points)
    RecyclerView rvPoints;

    private SubscriberOnNextListener<JSONObject> messageOnNext;
    private MessageCenterBean mMessageBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        messageOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mMessageBean = mGson.fromJson(jsonObject.toString(), MessageCenterBean.class);
                rvPoints.setLayoutManager(new LinearLayoutManager(this));
                MessageCenterAdapter messageCenterAdapter = new MessageCenterAdapter(getApplicationContext(), mMessageBean.getMessage().getData());
                rvPoints.setAdapter(messageCenterAdapter);
            }
        };

        HttpJsonMethod.getInstance().mesageList(
                new ProgressSubscriber(messageOnNext, MessageCenterActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }
}
