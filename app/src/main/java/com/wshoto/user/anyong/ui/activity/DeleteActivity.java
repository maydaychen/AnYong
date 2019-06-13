package com.wshoto.user.anyong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.ldf.calendar.component.CalendarAttr;
import com.ldf.calendar.component.CalendarViewAdapter;
import com.ldf.calendar.interf.OnSelectDateListener;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.Calendar;
import com.ldf.calendar.view.MonthPager;
import com.wshoto.user.anyong.Bean.CalendarDayEventBean;
import com.wshoto.user.anyong.Bean.CalendarEventBean;
import com.wshoto.user.anyong.Bean.CalendarMineBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.CalendarEventListAdapter;
import com.wshoto.user.anyong.adapter.CalendarMineListAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.CustomDayView;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class DeleteActivity extends InitActivity implements OnWheelChangedListener {
    @BindView(R.id.tb_main)
    TabLayout mTbMain;
    @BindView(R.id.rv_calendar_activity)
    RecyclerView rvCalendarActivity;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.wheel_add_address)
    RelativeLayout mAddAddress;
    @BindView(R.id.id_province)
    WheelView mProvince;
    @BindView(R.id.id_city)
    WheelView mCity;
    @BindView(R.id.id_area)
    WheelView mArea;
    @BindView(R.id.tv_hint)
    TextView hint;
    @BindView(R.id.tv_choose)
    TextView choose;

    MonthPager monthPager;
    private SubscriberOnNextListener<JSONObject> listOnNext;
    private SubscriberOnNextListener<JSONObject> mylistOnNext;
    private SubscriberOnNextListener<JSONObject> timelistOnNext;

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

    private LocationClient mLocClient;
    private MyLocationListener myLocationListener = new MyLocationListener();
    //    private MyLocationListener myLocationListener = new MyLocationListener();
// MapView 中央对于的屏幕坐标
    private Point mCenterPoint = null;

    /**
     * 是否第一次定位
     */
    private boolean isFirstLoc = true;
    /**
     * 获取的位置
     */
    private String mLocationValue;
    /**
     * 请求码
     */
    private final static int REQUEST_CODE = 0x123;
    private SubscriberOnNextListener<JSONObject> locateOnNext;
    private String city = "";
    private String provice = "";

    /**
     * 把全国的省市区的信息以json的格式保存，解析完成后赋值为null
     */
    private JSONObject mJsonObj;


    /**
     * 所有省
     */
    private String[] mProvinceDatas;
    private String[] mProvinceIds;
    /**
     * key - 省 value - 市s
     */
    private Map<String, String[]> mCitisDatasMap = new HashMap<>();
    private Map<String, String[]> mCitisDatasID = new HashMap<>();
    /**
     * key - 市 values - 区s
     */
    private Map<String, String[]> mAreaDatasMap = new HashMap<>();
    private Map<String, String[]> mAreaDatasId = new HashMap<>();

    /**
     * 当前省的名称
     */
    private String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    private String mCurrentCityName;
    /**
     * 当前区的名称
     */
    private String mCurrentAreaId;
    private String mCurrentAreaName;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_delete);
        ButterKnife.bind(this);

        context = this;
        monthPager = findViewById(R.id.calendar_view);
//        monthPager.setViewHeight(Utils.dpi2px(context, 270));
        mTbMain.addTab(mTbMain.newTab().setText(getText(R.string.today)));
        mTbMain.addTab(mTbMain.newTab().setText(getText(R.string.mine)));
    }

    @Override
    public void initData() {
        initCurrentDate();
        initCalendarView();
        listOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mCalendarEventBean = mGson.fromJson(jsonObject.toString(), CalendarEventBean.class);
                HashMap<String, String> markData = new HashMap<>();
                for (String s : mCalendarEventBean.getTimedata()) {
                    String[] list = s.split("-");
//                    if (list[2].startsWith("0")) {
//                        list[2] = list[2].substring(1);
//                    }
//                    if (list[1].startsWith("0")) {
//                        list[1] = list[1].substring(1);
//                    }
                    s = list[0] + "-" + list[1] + "-" + list[2];
                    markData.put(s, "0");
                }
                calendarAdapter.setMarkData(markData);
                calendarAdapter.notifyDataChanged();
            } else {
                Toast.makeText(DeleteActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                messageCenterAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(DeleteActivity.this, EventDetailActivity.class);
                    intent.putExtra("mine", true);
                    intent.putExtra("id", mCalendarMineBean.getData().get(data).getId());
                    startActivity(intent);

                });
            }
            if (mCalendarMineBean.getData().size() != 0) {
                rvCalendarActivity.setVisibility(View.VISIBLE);
                hint.setVisibility(View.GONE);
            } else {
                rvCalendarActivity.setVisibility(View.GONE);
                hint.setVisibility(View.VISIBLE);
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
                messageCenterAdapter.setOnItemClickListener((view, data) -> {
                    Intent intent = new Intent(DeleteActivity.this, EventDetailActivity.class);
                    intent.putExtra("mine", false);
                    intent.putExtra("id", mCalendarDayEventBean.getData().get(data).getId());
                    startActivity(intent);
                });
                if (mCalendarDayEventBean.getData().size() != 0) {
                    rvCalendarActivity.setVisibility(View.VISIBLE);
                    hint.setVisibility(View.GONE);
                } else {
                    rvCalendarActivity.setVisibility(View.GONE);
                    hint.setVisibility(View.VISIBLE);
                }
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
        initJsonData();
        initDatas();

        mProvince.setViewAdapter(new ArrayWheelAdapter<>(this, mProvinceDatas));
        // 添加change事件
        mProvince.addChangingListener(this);
        // 添加change事件
        mCity.addChangingListener(this);
        // 添加change事件
        mArea.addChangingListener(this);

        mProvince.setVisibleItems(5);
        mCity.setVisibleItems(5);
        mArea.setVisibleItems(5);
        updateCities();
        updateAreas();

        mLocClient = new LocationClient(DeleteActivity.this); //声明LocationClient类
        mLocClient.registerLocationListener(myLocationListener);//注册监听函数
        initLocation();
        mLocClient.start();//开启定位
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

    private void getData() {
        if (position == 0) {
            HttpJsonMethod.getInstance().timeCalendar(
                    new ProgressSubscriber(timelistOnNext, DeleteActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    currentDate.toString(), provice, city, (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        } else {
            HttpJsonMethod.getInstance().myCalendar(
                    new ProgressSubscriber(mylistOnNext, DeleteActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        }

    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_choose, R.id.tv_choose_cancel, R.id.tv_choose_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_choose:
                mAddAddress.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_choose_cancel:
                mAddAddress.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_choose_confirm:
                mAddAddress.setVisibility(View.GONE);
                city = mCurrentCityName;
                provice = mCurrentProviceName;
                choose.setText(city);
                HttpJsonMethod.getInstance().calendar(
                        new ProgressSubscriber(listOnNext, DeleteActivity.this),
                        (String) SharedPreferencesUtils.getParam(DeleteActivity.this, "session", ""), provice, city,
                        (String) SharedPreferencesUtils.getParam(DeleteActivity.this, "language", "zh"));
                getData();
                break;
        }
    }


    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
        String[] areas = mAreaDatasMap.get(mCurrentCityName);
        String[] areasId = mAreaDatasId.get(mCurrentCityName);
        mCurrentAreaName = areas[0];
        mCurrentAreaId = areasId[0];
        if (areas == null) {
            areas = new String[]{""};
        }
        mArea.setViewAdapter(new ArrayWheelAdapter<>(this, areas));
        mArea.setCurrentItem(0);
    }

    /**
     * 根据当前的省，更新市WheelView的信息
     */
    private void updateCities() {
        int pCurrent = mProvince.getCurrentItem();
        mCurrentProviceName = mProvinceDatas[pCurrent];
        String[] cities = mCitisDatasMap.get(mCurrentProviceName);
        if (cities == null) {
            cities = new String[]{""};
        }
        mCity.setViewAdapter(new ArrayWheelAdapter<>(this, cities));
        mCity.setCurrentItem(0);
        updateAreas();
    }

    /**
     * 解析整个Json对象，完成后释放Json对象的内存
     */
    private void initDatas() {
        try {
            JSONArray jsonArray = mJsonObj.getJSONArray("citylist");
            mProvinceDatas = new String[jsonArray.length()];
            mProvinceIds = new String[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonP = jsonArray.getJSONObject(i);// 每个省的json对象
                String province = jsonP.getString("name");// 省名字
                String provinceId = jsonP.getString("regionid");//省ID
                mProvinceDatas[i] = province;
                mProvinceIds[i] = provinceId;

                JSONArray jsonCs;
                try {
                    /**
                     * Throws JSONException if the mapping doesn't exist or is
                     * not a JSONArray.
                     */
                    jsonCs = jsonP.getJSONArray("children");
                } catch (Exception e1) {
                    continue;
                }
                String[] mCitiesDatas = new String[jsonCs.length()];
                String[] mCitiesIds = new String[jsonCs.length()];
                for (int j = 0; j < jsonCs.length(); j++) {
                    JSONObject jsonCity = jsonCs.getJSONObject(j);
                    String city = jsonCity.getString("name");// 市名字
                    String cityId = jsonCity.getString("regionid");// 市id
                    mCitiesDatas[j] = city;
                    mCitiesIds[j] = cityId;
                    JSONArray jsonAreas;
                    try {
                        jsonAreas = jsonCity.getJSONArray("children");
                    } catch (Exception e) {
                        continue;
                    }

                    String[] mAreasDatas = new String[jsonAreas.length()];// 当前市的所有区
                    String[] mAreasIds = new String[jsonAreas.length()];// 当前市的所有区
                    for (int k = 0; k < jsonAreas.length(); k++) {
                        String area = jsonAreas.getJSONObject(k).getString("name");// 区域的名称
                        String areaId = jsonAreas.getJSONObject(k).getString("regionid");// 区域的id
                        mAreasDatas[k] = area;
                        mAreasIds[k] = areaId;
                    }
                    mAreaDatasMap.put(city, mAreasDatas);
                    mAreaDatasId.put(city, mAreasIds);
                }
                mCitisDatasMap.put(province, mCitiesDatas);
                mCitisDatasID.put(province, mCitiesIds);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        mJsonObj = null;
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void initJsonData() {
        try {
//            StringBuilder sb = new StringBuilder();
//            InputStream is = getAssets().open("city.json");
//            int len;
//            byte[] buf = new byte[1024];
//            while ((len = is.read(buf)) != -1) {
//                sb.append(new String(buf, 0, len, "UTF-8"));
//            }
//            is.close();
            String resultString = "";
            InputStream inputStream = context.getResources().getAssets().open("city.json");
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            resultString = new String(buffer, "UTF-8");

            mJsonObj = new JSONObject(resultString);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * change事件的处理
     */
    @Override
    public void onChanged(WheelView wheel, int oldValue, int newValue) {
        if (wheel == mProvince) {
            updateCities();
        } else if (wheel == mCity) {
            updateAreas();
        } else if (wheel == mArea) {
            mCurrentAreaName = mAreaDatasMap.get(mCurrentCityName)[newValue];
            mCurrentAreaId = mAreaDatasId.get(mCurrentCityName)[newValue];
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高 精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
//        option.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当                                                                                                                                                                                                                                gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocClient.setLocOption(option);
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null) {
                return;
            }
            // 是否第一次定位
            if (isFirstLoc) {
                isFirstLoc = false;
                city = location.getCity();
                provice = location.getProvince();
                mLocClient.stop();
                choose.setText(city);
                HttpJsonMethod.getInstance().calendar(
                        new ProgressSubscriber(listOnNext, DeleteActivity.this),
                        (String) SharedPreferencesUtils.getParam(DeleteActivity.this, "session", ""), provice, city,
                        (String) SharedPreferencesUtils.getParam(DeleteActivity.this, "language", "zh"));
                getData();
                return;
            }
        }
    }

}
