package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.NewFriendListBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.NewFriendApplyAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewFriendActivity extends InitActivity {
    @BindView(R.id.rv_new_friends)
    RecyclerView rvNewFriends;
    private SubscriberOnNextListener<JSONObject> listOnNext;
    private NewFriendListBean newFriendListBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_friend);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        listOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                newFriendListBean = mGson.fromJson(jsonObject.toString(), NewFriendListBean.class);
                rvNewFriends.setLayoutManager(new LinearLayoutManager(this));
                NewFriendApplyAdapter messageCenterAdapter = new NewFriendApplyAdapter(NewFriendActivity.this, newFriendListBean.getData());
                rvNewFriends.setAdapter(messageCenterAdapter);
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().newfriendList(
                new ProgressSubscriber(listOnNext, NewFriendActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));

    }

    @OnClick({R.id.iv_comfirm_back, R.id.bt_goods_detail_enter_supplier})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.bt_goods_detail_enter_supplier:
                startActivity(new Intent(NewFriendActivity.this, SearchNewFriendActivity.class));
                break;
        }
    }
}
