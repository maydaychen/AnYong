package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendInfoActivity extends InitActivity {

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
    @BindView(R.id.tv_person_anyong_rank)
    TextView tvPersonAnyongRank;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_give_credit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_give_credit:
                break;
        }
    }
}
