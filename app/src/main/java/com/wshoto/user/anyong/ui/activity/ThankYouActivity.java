package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.fragment.ThankListFragment;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankYouActivity extends InitActivity {
    private FragmentManager mFragmentManager;
    private Fragment mFragmentOne;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thank_you);
        ButterKnife.bind(this);
        mFragmentOne = ThankListFragment.newInstance();
        mFragmentManager = getSupportFragmentManager();
    }

    @Override
    public void initData() {
        initDefaultFragment();
    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_thanku_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_thanku_send:
                startActivity(new Intent(ThankYouActivity.this, SendThankActivity.class));
                break;
            default:
                break;
        }
    }

    private void initDefaultFragment() {
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.id_thank_content, mFragmentOne);
        fragmentTransaction.addToBackStack(null);

        fragmentTransaction.commit();
    }
}
