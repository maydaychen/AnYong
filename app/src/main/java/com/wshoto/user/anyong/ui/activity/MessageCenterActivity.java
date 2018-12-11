package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
    @BindView(R.id.tv_hint)
    TextView hint;

    private SubscriberOnNextListener<JSONObject> messageOnNext;
    private SubscriberOnNextListener<JSONObject> readOnNext;
    private MessageCenterBean mMessageBean;
    private Gson mGson = new Gson();
    private String ID = "";

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        messageOnNext = jsonObject -> {
            Log.i("chenyi", "message: " + jsonObject.toString());
            if (jsonObject.getInt("code") == 1) {
                mMessageBean = mGson.fromJson(jsonObject.toString(), MessageCenterBean.class);
                rvPoints.setLayoutManager(new LinearLayoutManager(this));
                MessageCenterAdapter messageCenterAdapter = new MessageCenterAdapter(getApplicationContext(), mMessageBean.getData());
                rvPoints.setAdapter(messageCenterAdapter);
                messageCenterAdapter.setOnItemClickListener((view, data) -> {
                    ID = mMessageBean.getData().get(data).getPush_type();
                    HttpJsonMethod.getInstance().mesageRead(
                            new ProgressSubscriber(readOnNext, MessageCenterActivity.this),
                            (String) SharedPreferencesUtils.getParam(MessageCenterActivity.this, "session", ""),
                            mMessageBean.getData().get(data).getId());
                });
                rvPoints.notifyAll();
            } else {
                rvPoints.setVisibility(View.GONE);
                hint.setVisibility(View.VISIBLE);
            }
        };
        readOnNext = jsonObject -> {
            switch (ID) {
//                case "1":
//                    startActivity(new Intent(MessageCenterActivity.this, MessageDetailActivity.class));
//                    break;
                case "2":
                    startActivity(new Intent(MessageCenterActivity.this, MyRadiiActivity.class));
                    break;
                case "3":
                    startActivity(new Intent(MessageCenterActivity.this, ThankYouActivity.class));
                    break;
                case "4":
                    startActivity(new Intent(MessageCenterActivity.this, HealthyLifeActivity.class));
                    break;
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
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
