package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

public class NewFriendInfoActivity extends InitActivity {

    @BindView(R.id.iv_new_friend_logo)
    ImageView ivNewFriendLogo;
    @BindView(R.id.tv_new_friend_name)
    TextView tvNewFriendName;
    @BindView(R.id.tv_new_friend_num)
    TextView tvNewFriendNum;
    @BindView(R.id.tv_new_friend_tele)
    TextView tvNewFriendTele;
    @BindView(R.id.tv_person_bumen)
    TextView tvPersonBumen;
    @BindView(R.id.tv_person_zhiwei)
    TextView tvPersonZhiwei;
    @BindView(R.id.tv_person_tele)
    TextView tvPersonTele;
    @BindView(R.id.tv_person_friend_rank)
    TextView tvPersonFriendRank;

    private SubscriberOnNextListener<JSONObject> infoOnNext;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_friend_info);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        infoOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                //todo 新好友信息
            } else {
                Toast.makeText(NewFriendInfoActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };

        HttpJsonMethod.getInstance().friendInfo(
                new ProgressSubscriber(infoOnNext, NewFriendInfoActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),getIntent().getStringExtra("friend"));
    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_new_friend_accept, R.id.tv_new_friend_refuse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_new_friend_accept:
                break;
            case R.id.tv_new_friend_refuse:
                break;
        }
    }
}
