package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.fragment.ConfirmStep1Fragment;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConfirmActivity extends InitActivity {

    @BindView(R.id.id_content)
    FrameLayout mIdContent;
    private FragmentManager mFragmentManager;
    private Fragment mFragmentOne;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_confirm);
        ButterKnife.bind(this);
        mFragmentOne = ConfirmStep1Fragment.newInstance();
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initData() {
        initDefaultFragment();
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }

    private void initDefaultFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_content, mFragmentOne);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}
