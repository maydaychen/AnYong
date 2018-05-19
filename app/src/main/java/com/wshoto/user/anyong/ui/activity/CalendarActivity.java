package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codbking.calendar.CaledarAdapter;
import com.codbking.calendar.CalendarBean;
import com.codbking.calendar.CalendarDateView;
import com.codbking.calendar.CalendarUtil;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CalendarActivity extends InitActivity {
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.calendarDateView)
    CalendarDateView mCalendarDateView;
    //    @BindView(R.id.list)
//    ListView mList;
    @BindView(R.id.tb_main)
    TabLayout mTbMain;
    @BindView(R.id.rv_calendar_activity)
    RecyclerView rvCalendarActivity;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_calendar);
        ButterKnife.bind(this);

        mCalendarDateView.setAdapter((convertView, parentView, bean) -> {

            if (convertView == null) {
                convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_xiaomi, null);
            }

            TextView chinaText = convertView.findViewById(R.id.chinaText);
            TextView text = convertView.findViewById(R.id.text);

            text.setText("" + bean.day);
            if (bean.mothFlag != 0) {
                text.setTextColor(0xff808080);
                chinaText.setTextColor(0xff808080);
            } else {
                text.setTextColor(0xffffffff);
                chinaText.setTextColor(0xffffffff);
            }
            chinaText.setText(bean.chinaDay);
            return convertView;
        });

        mCalendarDateView.setOnItemClickListener((view, postion, bean) -> mTitle.setText(bean.year + "/" + bean.moth + "/" + bean.day));

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);
        mTbMain.addTab(mTbMain.newTab().setText("本日活动"));
        mTbMain.addTab(mTbMain.newTab().setText("我报名的活动"));
    }

    @Override
    public void initData() {
    }


    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }
}
