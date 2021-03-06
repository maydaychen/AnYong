package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
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

public class AddFriendActivity extends InitActivity {

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
    @BindView(R.id.tv_person_anyong_rank)
    TextView tvPersonAnyongRank;
    private SubscriberOnNextListener<JSONObject> friendInfoOnNext;
    private SubscriberOnNextListener<JSONObject> addFriendOnNext;
    private FriendInfoBean friendInfoBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        friendInfoOnNext = jsonObject -> {
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
                tvPersonAnyongRank.setText(dataBean.getLevel() + "");
            }
        };
        addFriendOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(AddFriendActivity.this, getText(R.string.operate_successfully), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddFriendActivity.this, MyRadiiActivity.class));
            } else {
                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
        HttpJsonMethod.getInstance().friendInfo(
                new ProgressSubscriber(friendInfoOnNext, AddFriendActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                getIntent().getStringExtra("friend_id"),(String) SharedPreferencesUtils.getParam(this, "language", "zh"));

    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_give_credit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_give_credit:
                HttpJsonMethod.getInstance().addFriend(
                        new ProgressSubscriber(addFriendOnNext, AddFriendActivity.this),
                        (String) SharedPreferencesUtils.getParam(this, "session", ""),
                        getIntent().getStringExtra("friend_id"),(String) SharedPreferencesUtils.getParam(this, "language", "zh"));
                break;
        }
    }
}
