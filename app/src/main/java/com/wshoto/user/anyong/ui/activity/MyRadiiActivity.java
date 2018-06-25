package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.MyRadiuBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.MyRadiiAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyRadiiActivity extends InitActivity {
    @BindView(R.id.rv_my_radii)
    RecyclerView rvMyRadii;
    @BindView(R.id.ll_new_friend_apply)
    LinearLayout llNewFriendApply;
    private SubscriberOnNextListener<JSONObject> listOnNext;
    private SubscriberOnNextListener<JSONObject> messageOnNext;
    private MyRadiuBean myRadiuBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_radii);
        ButterKnife.bind(this);
        llNewFriendApply.setOnClickListener(view -> startActivity(new Intent(MyRadiiActivity.this, NewFriendActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().myRadiusList(
                new ProgressSubscriber(listOnNext, MyRadiiActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""));

    }

    @Override
    public void initData() {
        listOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                myRadiuBean = mGson.fromJson(jsonObject.toString(), MyRadiuBean.class);
                rvMyRadii.setLayoutManager(new LinearLayoutManager(this));
                MyRadiiAdapter messageCenterAdapter = new MyRadiiAdapter(getApplicationContext(), myRadiuBean.getData());
                rvMyRadii.setAdapter(messageCenterAdapter);
            } else {
                Toast.makeText(MyRadiiActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
    finish();}
}
