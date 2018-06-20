package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.UserInfoBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonActivity extends InitActivity {
    @BindView(R.id.iv_person_logo)
    SmartImageView ivPersonLogo;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.tv_person_number)
    TextView tvPersonNumber;
    @BindView(R.id.tv_person_bumen)
    TextView tvPersonBumen;
    @BindView(R.id.tv_person_position)
    TextView tvPersonPosition;
    @BindView(R.id.tv_person_tele)
    TextView tvPersonTele;
    @BindView(R.id.tv_person_email)
    TextView tvPersonEmail;
    @BindView(R.id.tv_person_recommend)
    TextView tvPersonRecommend;
    private UserInfoBean.DataBean mDataBean;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        mDataBean = (UserInfoBean.DataBean) getIntent().getSerializableExtra("person");
        ivPersonLogo.setImageUrl(mDataBean.getAvatar());
        tvPersonName.setText(mDataBean.getUsername());
        tvPersonNumber.setText(mDataBean.getJob_no());
        tvPersonBumen.setText(mDataBean.getDepartment_id());
        tvPersonPosition.setText(mDataBean.getPosition());
        tvPersonTele.setText(mDataBean.getMobile());
        tvPersonEmail.setText(mDataBean.getEmail());
        tvPersonRecommend.setText(mDataBean.getInvitecode());
    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_person_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_person_setting:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
        }
    }
}
