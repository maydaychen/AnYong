package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.adapter.SlidePagerAdapter;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends InitActivity {
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.btn_im_exp)
    TextView btnImExp;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    private ArrayList<View> mList;
//    private ImageView[] mImageViews;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initViewPager();
    }

    @Override
    public void initData() {
        tvLogin.setOnClickListener(view -> finish());
    }

    private void initViewPager() {
        LayoutInflater inflater = getLayoutInflater();
        mList = new ArrayList<>();
        mList.add(inflater.inflate(R.layout.view_pager_first, null));
        mList.add(inflater.inflate(R.layout.view_pager_second, null));
        mList.add(inflater.inflate(R.layout.view_pager_third, null));
        mList.add(inflater.inflate(R.layout.view_pager_forth, null));
        mList.add(inflater.inflate(R.layout.view_pager_fifth, null));

//        // 底部点点实现
//        mImageViews = new ImageView[mList.size()];
//        for (int i = 0; i < mList.size(); i++) {
//            mImageViews[i] = new ImageView(GuideActivity.this);
//            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
//            // 设置边界
//            params.setMargins(7, 10, 7, 10);
//            mImageViews[i].setLayoutParams(params);
//            if (0 == i) {
//                mImageViews[i].setBackgroundResource(R.drawable.dotcopy4);
//            } else {
//                mImageViews[i].setBackgroundResource(R.drawable.dotcopy42);
//            }
//            layoutDots.addView(mImageViews[i]);
//        }

        viewPager.setAdapter(new SlidePagerAdapter(mList, GuideActivity.this));
        // 绑定回调
        viewPager.addOnPageChangeListener(new onPageChangeListener());
//        viewPager.setCurrentItem(0);
    }

    /**
     * 立刻体验按钮监听
     */
    @OnClick(R.id.btn_im_exp)
    public void onButtonClick() {
//        // 写入是否引导记录
//        SharedUtil.setIsFirst(GuideActivity.this);
//        // 跳转到LoginActivity
//        openActivityFn(LoginActivity.class);
//        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//        startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        finish();
    }

    /**
     * 监听ViewPager滑动效果
     */
    private class onPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            // 更新小圆点图标
//            for (int i = 0; i < mList.size(); i++) {
//                if (position == i) {
//                    mImageViews[i].setBackgroundResource(R.drawable.dotcopy4);
//                } else {
//                    mImageViews[i].setBackgroundResource(R.drawable.dotcopy42);
//                }
//            }
            if (getIntent().getBooleanExtra("login",false)) {
                // 滑动到最后pager时显示“立刻体验”按钮并监听
                if (position == mList.size() - 1) {
                    tvLogin.setVisibility(View.VISIBLE);
                } else {
                    tvLogin.setVisibility(View.GONE);
                }
            }
//        // 滑动到最后pager时显示“立刻体验”按钮并监听
//            if (position == mList.size() - 1) {
//                btnImExp.setVisibility(View.VISIBLE);
//            } else {
//                btnImExp.setVisibility(View.INVISIBLE);
//            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
