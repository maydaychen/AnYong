package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.wshoto.user.anyong.Bean.MessageCenterBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetailActivity extends InitActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_detail);
        ButterKnife.bind(this);
        MessageCenterBean.DataBean dataBean = (MessageCenterBean.DataBean) getIntent().getSerializableExtra("info");
        tvTitle.setText(dataBean.getTitle());
        tvContent.setText(dataBean.getContent());
    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }
}
