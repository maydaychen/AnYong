package com.wshoto.user.anyong.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointActivity extends InitActivity {

    @BindView(R.id.rv_points)
    RecyclerView mRvPoints;
    private SubscriberOnNextListener<JSONObject> pointOnNext;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        pointOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                //todo 积分明细
            } else {
                Toast.makeText(PointActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        HttpJsonMethod.getInstance().creditDetail(
                new ProgressSubscriber(pointOnNext, PointActivity.this),(String) SharedPreferencesUtils.getParam(this, "session", ""));
    }

    @OnClick({R.id.iv_comfirm_back, R.id.iv_point_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.iv_point_help:
                break;
            default:
                break;
        }
    }
}
