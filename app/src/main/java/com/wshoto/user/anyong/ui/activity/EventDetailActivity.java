package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.CalendarDetailBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventDetailActivity extends InitActivity {
    private SubscriberOnNextListener<JSONObject> detailOnNext;
    private Gson mGson = new Gson();
    private SubscriberOnNextListener<JSONObject> joinOnNext;

    @BindView(R.id.tv_time_start)
    TextView tvTimeStart;
    @BindView(R.id.tv_time_end)
    TextView tvTimeEnd;
    @BindView(R.id.tv_time_content)
    TextView tvTimeContent;
    @BindView(R.id.tv_join)
    TextView tvJoin;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        detailOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                CalendarDetailBean detailBean = mGson.fromJson(jsonObject.toString(), CalendarDetailBean.class);
                CalendarDetailBean.DataBean dataBean = detailBean.getData();
                tvTimeStart.setText(String.format((String) getResources().getText(R.string.start_time), dataBean.getStart_time()));
                tvTimeEnd.setText(String.format((String) getResources().getText(R.string.end_time), dataBean.getEnd_time()));
                tvTitle.setText(dataBean.getTitle());
                tvTimeContent.setText(Html.fromHtml(dataBean.getContent()));
            }
        };
        joinOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(EventDetailActivity.this, getText(R.string.join_success), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(EventDetailActivity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
        HttpJsonMethod.getInstance().activityInfo(
                new ProgressSubscriber(detailOnNext, EventDetailActivity.this),
                (String) SharedPreferencesUtils.getParam(EventDetailActivity.this, "session", ""),
                getIntent().getStringExtra("id"));

    }

    @Override
    public void initData() {
        if (!getIntent().getBooleanExtra("mine", false)) {
            tvJoin.setVisibility(View.VISIBLE);
        }

    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_join})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_join:
                HttpJsonMethod.getInstance().joinActivity(
                        new ProgressSubscriber(joinOnNext, EventDetailActivity.this),
                        (String) SharedPreferencesUtils.getParam(EventDetailActivity.this, "session", ""),
                        getIntent().getStringExtra("id"));
                break;
        }
    }

}
