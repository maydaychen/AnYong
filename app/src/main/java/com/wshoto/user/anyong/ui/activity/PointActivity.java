package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PointActivity extends InitActivity {

    @BindView(R.id.rv_points)
    RecyclerView mRvPoints;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_point);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_comfirm_back, R.id.iv_point_help})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.iv_point_help:
                break;
            default:
                break;
        }
    }
}
