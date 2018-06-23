package com.wshoto.user.anyong.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ldf.calendar.Utils;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.wshoto.user.anyong.Bean.CalendarDayEventBean;
import com.wshoto.user.anyong.Bean.CalendarDetailBean;
import com.wshoto.user.anyong.Bean.CalendarEventBean;
import com.wshoto.user.anyong.Bean.CalendarMineBean;
import com.wshoto.user.anyong.Bean.UserInfoBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.CalendarEventListAdapter;
import com.wshoto.user.anyong.adapter.CalendarMineListAdapter;
import com.wshoto.user.anyong.adapter.MessageCenterAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.CustomDayView;
import com.wshoto.user.anyong.ui.widget.InitActivity;
import com.wshoto.user.anyong.ui.widget.ThemeDayView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeleteActivity extends InitActivity {
    @BindView(R.id.tb_main)
    TabLayout mTbMain;
    @BindView(R.id.rv_calendar_activity)
    RecyclerView rvCalendarActivity;
    @BindView(R.id.title)
    TextView mTitle;

    MonthPager monthPager;
    private SubscriberOnNextListener<JSONObject> listOnNext;
    private SubscriberOnNextListener<JSONObject> mylistOnNext;
    private SubscriberOnNextListener<JSONObject> timelistOnNext;
    private SubscriberOnNextListener<JSONObject> joinOnNext;
    private SubscriberOnNextListener<JSONObject> detailOnNext;

    private ArrayList<Calendar> currentCalendars = new ArrayList<>();
    private CalendarViewAdapter calendarAdapter;
    private OnSelectDateListener onSelectDateListener;
    private Context context;
    private CalendarDate currentDate;
    private boolean initiated = false;
    private int position = 0;//活动标签切换
    private CalendarEventBean mCalendarEventBean;
    private CalendarDayEventBean mCalendarDayEventBean;
    private CalendarMineBean mCalendarMineBean;
    private Gson mGson = new Gson();

    /*
     * 如果你想以周模式启动你的日历，请在onResume是调用
     * Utils.scrollTo(content, rvToDoList, monthPager.getCellHeight(), 200);
     * calendarAdapter.switchToWeek(monthPager.getRowIndex());
     * */
    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().calendar(
                new ProgressSubscriber(listOnNext, DeleteActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""));
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_delete);
        ButterKnife.bind(this);

        context = this;
        monthPager = findViewById(R.id.calendar_view);
//        monthPager.setViewHeight(Utils.dpi2px(context, 270));
        mTbMain.addTab(mTbMain.newTab().setText("本日活动"));
        mTbMain.addTab(mTbMain.newTab().setText("我报名的活动"));
    }

    @Override
    public void initData() {
        initCurrentDate();
        initCalendarView();
        Log.e("ldf", "OnCreated");
        listOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mCalendarEventBean = mGson.fromJson(jsonObject.toString(), CalendarEventBean.class);
                HashMap<String, String> markData = new HashMap<>();
                for (CalendarEventBean.DataBeanX dataBeanX : mCalendarEventBean.getData()) {
                    markData.put(dataBeanX.getTime(), "0");
                }
                calendarAdapter.setMarkData(markData);
                calendarAdapter.notifyDataChanged();
            } else {
                Toast.makeText(DeleteActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        joinOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(context, "参加成功!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(DeleteActivity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
        detailOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                CalendarDetailBean detailBean = mGson.fromJson(jsonObject.toString(), CalendarDetailBean.class);
                CalendarDetailBean.DataBean dataBean = detailBean.getData();
                String detail = "开始时间：" + dataBean.getStart_time() + "\n结束时间：" + dataBean.getEnd_time() + "\n内容：" + dataBean.getContent();
                if (position == 1) {
                    show1(dataBean.getTitle(), detail, dataBean.getId());
                } else {
                    show(dataBean.getTitle(), detail, dataBean.getId());
                }
            }
        };
        mylistOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mCalendarMineBean = mGson.fromJson(jsonObject.toString(), CalendarMineBean.class);
                rvCalendarActivity.setLayoutManager(new LinearLayoutManager(DeleteActivity.this) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                CalendarMineListAdapter messageCenterAdapter = new CalendarMineListAdapter(getApplicationContext(), mCalendarMineBean.getData());
                rvCalendarActivity.setAdapter(messageCenterAdapter);
                messageCenterAdapter.setOnItemClickListener((view, data) -> HttpJsonMethod.getInstance().activityInfo(
                        new ProgressSubscriber(detailOnNext, DeleteActivity.this),
                        (String) SharedPreferencesUtils.getParam(DeleteActivity.this, "session", ""), mCalendarDayEventBean.getData().get(data).getId()));
            }
        };
        timelistOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mCalendarDayEventBean = mGson.fromJson(jsonObject.toString(), CalendarDayEventBean.class);
                rvCalendarActivity.setLayoutManager(new LinearLayoutManager(DeleteActivity.this) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                });
                CalendarEventListAdapter messageCenterAdapter = new CalendarEventListAdapter(getApplicationContext(), mCalendarDayEventBean.getData());
                rvCalendarActivity.setAdapter(messageCenterAdapter);
                messageCenterAdapter.setOnItemClickListener((view, data) -> HttpJsonMethod.getInstance().activityInfo(
                        new ProgressSubscriber(detailOnNext, DeleteActivity.this),
                        (String) SharedPreferencesUtils.getParam(DeleteActivity.this, "session", ""), mCalendarDayEventBean.getData().get(data).getId()));
            }
        };

        mTbMain.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                getData();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        getData();
    }


    /**
     * onWindowFocusChanged回调时，将当前月的种子日期修改为今天
     *
     * @return void
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !initiated) {
            refreshMonthPager();
            initiated = true;
        }
    }


    /**
     * 初始化currentDate
     *
     * @return void
     */
    private void initCurrentDate() {
        currentDate = new CalendarDate();
        mTitle.setText(currentDate.getYear() + "/" + currentDate.getMonth() + "/" + currentDate.getDay());
    }

    /**
     * 初始化CustomDayView，并作为CalendarViewAdapter的参数传入
     */
    private void initCalendarView() {
        initListener();
        CustomDayView customDayView = new CustomDayView(context, R.layout.custom_day);
        calendarAdapter = new CalendarViewAdapter(
                context,
                onSelectDateListener,
                CalendarAttr.WeekArrayType.Monday,
                customDayView);
        calendarAdapter.setOnCalendarTypeChangedListener(type -> {
//                rvToDoList.scrollToPosition(0);
        });
        initMarkData();
        initMonthPager();
    }

    /**
     * 初始化标记数据，HashMap的形式，可自定义
     * 如果存在异步的话，在使用setMarkData之后调用 calendarAdapter.notifyDataChanged();
     */
    private void initMarkData() {
        HashMap<String, String> markData = new HashMap<>();
        calendarAdapter.setMarkData(markData);
    }

    private void initListener() {
        onSelectDateListener = new OnSelectDateListener() {
            @Override
            public void onSelectDate(CalendarDate date) {
                refreshClickDate(date);
            }

            @Override
            public void onSelectOtherMonth(int offset) {
                //偏移量 -1表示刷新成上一个月数据 ， 1表示刷新成下一个月数据
                monthPager.selectOtherMonth(offset);
            }
        };
    }


    private void refreshClickDate(CalendarDate date) {
        currentDate = date;
        mTitle.setText(date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
        getData();
    }

    /**
     * 初始化monthPager，MonthPager继承自ViewPager
     *
     * @return void
     */
    private void initMonthPager() {
        monthPager.setAdapter(calendarAdapter);
        monthPager.setCurrentItem(MonthPager.CURRENT_DAY_INDEX);
        monthPager.setPageTransformer(false, (page, position) -> {
            position = (float) Math.sqrt(1 - Math.abs(position));
            page.setAlpha(position);
        });
        monthPager.addOnPageChangeListener(new MonthPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentCalendars = calendarAdapter.getPagers();
                if (currentCalendars.get(position % currentCalendars.size()) != null) {
                    CalendarDate date = currentCalendars.get(position % currentCalendars.size()).getSeedDate();
                    currentDate = date;
                    mTitle.setText(date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void refreshMonthPager() {
        CalendarDate today = new CalendarDate();
        calendarAdapter.notifyDataChanged(today);

        mTitle.setText(today.getYear() + "/" + today.getMonth() + "/" + today.getDay());
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }

    private void getData() {
        if (position == 0) {
            HttpJsonMethod.getInstance().timeCalendar(
                    new ProgressSubscriber(timelistOnNext, DeleteActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""), currentDate.toString());
        } else {
            HttpJsonMethod.getInstance().myCalendar(
                    new ProgressSubscriber(mylistOnNext, DeleteActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""));
        }

    }

    //展示并参加
    public void show(String title, String detail, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
        builder.setTitle(title);
        builder.setMessage(detail);
        builder.setPositiveButton("参加", (dialog, which) -> {
            HttpJsonMethod.getInstance().joinActivity(
                    new ProgressSubscriber(joinOnNext, DeleteActivity.this),
                    (String) SharedPreferencesUtils.getParam(DeleteActivity.this, "session", ""), id)
            ;

        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    //已参加展示
    public void show1(String title, String detail, String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeleteActivity.this);
        builder.setTitle(title);
        builder.setMessage(detail);
//        builder.setPositiveButton("参加", (dialog, which) -> {
//        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}
