package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.ThankBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.ThankUListAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;
import com.wshoto.user.anyong.ui.widget.RecycleViewGridDivider;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankSentBoxActivity extends InitActivity {
    @BindView(R.id.rv_mail_box)
    RecyclerView rvMailBox;
    @BindView(R.id.tv_mail_num)
    TextView tvMailNum;
    private SubscriberOnNextListener<JSONObject> searchOnNext;
    private ThankBean mThankBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thank_sent_box);
        ButterKnife.bind(this);
        searchOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mThankBean = mGson.fromJson(jsonObject.toString(), ThankBean.class);
                tvMailNum.setText(mThankBean.getData().size()+"");

                rvMailBox.setLayoutManager(new LinearLayoutManager(ThankSentBoxActivity.this));
                ThankUListAdapter messageCenterAdapter = new ThankUListAdapter(ThankSentBoxActivity.this, mThankBean.getData());
                rvMailBox.addItemDecoration(new RecycleViewGridDivider(ThankSentBoxActivity.this));
                rvMailBox.setAdapter(messageCenterAdapter);
                messageCenterAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(ThankSentBoxActivity.this, ThankViewActivity.class);
                    intent.putExtra("id", mThankBean.getData().get(data).getId());
                    startActivity(intent);
                });
            } else {
                Toast.makeText(ThankSentBoxActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public void initData() {
        HttpJsonMethod.getInstance().thankSentList(new ProgressSubscriber(searchOnNext, ThankSentBoxActivity.this),
                (String) SharedPreferencesUtils.getParam(ThankSentBoxActivity.this, "session", ""));
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }
}
