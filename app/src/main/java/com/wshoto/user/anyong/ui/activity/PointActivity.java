package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.PointBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.PointAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointActivity extends InitActivity {

    @BindView(R.id.rv_points)
    RecyclerView mRvPoints;
    private SubscriberOnNextListener<JSONObject> pointOnNext;
    private PointBean pointBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        pointOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                pointBean = mGson.fromJson(jsonObject.toString(), PointBean.class);
                mRvPoints.setLayoutManager(new LinearLayoutManager(this));
                PointAdapter messageCenterAdapter = new PointAdapter(getApplicationContext(), pointBean.getData());
                mRvPoints.setAdapter(messageCenterAdapter);
            }
        };
        HttpJsonMethod.getInstance().creditDetail(
                new ProgressSubscriber(pointOnNext, PointActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
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
