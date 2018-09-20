package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.HealthyTaskBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealthyLifeActivity extends InitActivity {

    @BindView(R.id.tv_healthy_number)
    TextView mTvHealthyNumber;
    @BindView(R.id.tv_healthy_gather)
    TextView mTvHealthyGather;
    @BindView(R.id.tv_option_yanbao)
    TextView mTvOptionYanbao;
    @BindView(R.id.tv_option_jingzhui)
    TextView mTvOptionJingzhui;
    private SubscriberOnNextListener<JSONObject> healthTaskOnNext;
    private SubscriberOnNextListener<JSONObject> healthCommitOnNext;
    private SubscriberOnNextListener<JSONObject> footsetpOnNext;
    private HealthyTaskBean mHealthyTaskBean;
    private Gson mGson = new Gson();

    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().healthTask(
                new ProgressSubscriber(healthTaskOnNext, HealthyLifeActivity.this),
                (String) com.wshoto.user.anyong.SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
    }


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_healthy_life);
        ButterKnife.bind(this);
        mTvHealthyNumber.setText((String) SharedPreferencesUtils.getParam(this, "step", "0"));

    }

    @Override
    public void initData() {
        footsetpOnNext = jsonObject -> {
        };
        healthTaskOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mHealthyTaskBean = mGson.fromJson(jsonObject.toString(), HealthyTaskBean.class);
                if (mHealthyTaskBean.getData().get(0).getIs_done() == 1) {
                    mTvOptionYanbao.setBackground(getResources().getDrawable(R.drawable.boder_healthy_grey));
                    mTvOptionYanbao.setText(getText(R.string.point_success));
                    mTvOptionYanbao.setClickable(false);
                }
                if (mHealthyTaskBean.getData().get(1).getIs_done() == 1) {
                    mTvOptionJingzhui.setBackground(getResources().getDrawable(R.drawable.boder_healthy_grey));
                    mTvOptionJingzhui.setText(getText(R.string.point_success));
                    mTvOptionJingzhui.setClickable(false);
                }
            } else {
                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
        healthCommitOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(HealthyLifeActivity.this, getText(R.string.point_successfully), Toast.LENGTH_SHORT).show();
                HttpJsonMethod.getInstance().healthTask(
                        new ProgressSubscriber(healthTaskOnNext, HealthyLifeActivity.this),
                        (String) com.wshoto.user.anyong.SharedPreferencesUtils.getParam(this, "session", ""), (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
            } else {
                Toast.makeText(HealthyLifeActivity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        switch (hour) {
            case 10:
                mTvOptionYanbao.setClickable(true);
                mTvOptionYanbao.setOnClickListener(v -> {
                    HttpJsonMethod.getInstance().healthCommit(
                            new ProgressSubscriber(healthCommitOnNext, HealthyLifeActivity.this),
                            (String) com.wshoto.user.anyong.SharedPreferencesUtils.getParam(this, "session", ""), "1", (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
                });
                mTvOptionYanbao.setBackground(getResources().getDrawable(R.drawable.boder_healthy_yellow));
                mTvOptionYanbao.setText(getText(R.string.complete));
                break;
            case 16:
                mTvOptionJingzhui.setClickable(true);
                mTvOptionJingzhui.setOnClickListener(v -> HttpJsonMethod.getInstance().healthCommit(
                        new ProgressSubscriber(healthCommitOnNext, HealthyLifeActivity.this),
                        (String) SharedPreferencesUtils.getParam(this, "session", ""), "2", (String) SharedPreferencesUtils.getParam(this, "language", "zh")));
                mTvOptionJingzhui.setBackground(getResources().getDrawable(R.drawable.boder_healthy_yellow));
                mTvOptionJingzhui.setText(getText(R.string.complete));
                break;
        }
        if (Integer.valueOf((String) SharedPreferencesUtils.getParam(this, "step", "0")) >= 500) {
            if ((Boolean) SharedPreferencesUtils.getParam(this, "fresh", false)) {
                HttpJsonMethod.getInstance().footstep(
                        new ProgressSubscriber(footsetpOnNext, HealthyLifeActivity.this),
                        (String) com.wshoto.user.anyong.SharedPreferencesUtils.getParam(this, "session", ""), (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
            } else {
                mTvHealthyGather.setText(getText(R.string.point_success));
            }
        }
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }


}
