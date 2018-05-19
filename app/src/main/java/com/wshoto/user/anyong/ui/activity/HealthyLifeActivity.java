package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

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

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_healthy_life);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        switch (hour) {
            case 10:
                mTvOptionYanbao.setClickable(true);
                mTvOptionYanbao.setOnClickListener(v -> {
                    Toast.makeText(HealthyLifeActivity.this, "积分取得成功！", Toast.LENGTH_SHORT).show();
                    mTvOptionYanbao.setBackground(getResources().getDrawable(R.drawable.boder_healthy_grey));
                    mTvOptionYanbao.setText("已领取积分！");
                });
                mTvOptionYanbao.setBackground(getResources().getDrawable(R.drawable.boder_healthy_yellow));
                mTvOptionYanbao.setText("完成任务点我");
                break;
            case 16:
                mTvOptionJingzhui.setClickable(true);
                mTvOptionJingzhui.setOnClickListener(v -> Toast.makeText(HealthyLifeActivity.this, "积分取得成功！", Toast.LENGTH_SHORT).show());
                mTvOptionJingzhui.setBackground(getResources().getDrawable(R.drawable.boder_healthy_yellow));
                mTvOptionJingzhui.setText("完成任务点我");
                break;
        }
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }
}
