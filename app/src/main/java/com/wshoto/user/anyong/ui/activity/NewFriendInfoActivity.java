package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.FriendInfoBean;
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
    SmartImageView ivNewFriendLogo;
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
    private SubscriberOnNextListener<JSONObject> operateOnNext;
    private FriendInfoBean friendInfoBean;
    private Gson mGson = new Gson();


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_new_friend_info);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        infoOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                friendInfoBean = mGson.fromJson(jsonObject.toString(), FriendInfoBean.class);
                FriendInfoBean.DataBean dataBean = friendInfoBean.getData();
                ivNewFriendLogo.setImageUrl(dataBean.getAvatar());
                tvNewFriendName.setText(dataBean.getEnglish_name());
                tvNewFriendNum.setText(String.format(getResources().getString(R.string.user_number1), dataBean.getJob_no()));
                tvNewFriendTele.setText(dataBean.getMobile());
                tvPersonBumen.setText(dataBean.getDepartment_id());
                tvPersonZhiwei.setText(dataBean.getPosition());
                tvPersonTele.setText(dataBean.getIntegral() + "");
                tvPersonFriendRank.setText(dataBean.getFriendlevel() + "");
            }
        };
        operateOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(NewFriendInfoActivity.this, "操作成功！", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };

        HttpJsonMethod.getInstance().friendInfo(
                new ProgressSubscriber(infoOnNext, NewFriendInfoActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""), getIntent().getStringExtra("friend_id"));
    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_new_friend_accept, R.id.tv_new_friend_refuse})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_new_friend_accept:
                HttpJsonMethod.getInstance().newFriendOperate(
                        new ProgressSubscriber(operateOnNext, NewFriendInfoActivity.this),
                        (String) SharedPreferencesUtils.getParam(this, "session", ""), getIntent().getStringExtra("apply_id"), "1");
                break;
            case R.id.tv_new_friend_refuse:
                HttpJsonMethod.getInstance().newFriendOperate(
                        new ProgressSubscriber(operateOnNext, NewFriendInfoActivity.this),
                        (String) SharedPreferencesUtils.getParam(this, "session", ""), getIntent().getStringExtra("apply_id"), "2");
                break;
        }
    }
}
