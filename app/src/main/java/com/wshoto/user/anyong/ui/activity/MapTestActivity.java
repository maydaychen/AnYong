package com.wshoto.user.anyong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.map.LocationAdapter;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import kankan.wheel.widget.OnWheelChangedListener;
import kankan.wheel.widget.WheelView;
import kankan.wheel.widget.adapters.ArrayWheelAdapter;

public class MapTestActivity extends InitActivity implements AdapterView.OnItemClickListener,
        OnGetGeoCoderResultListener, BaiduMap.OnMapStatusChangeListener, View.OnClickListener, OnWheelChangedListener {

    @BindView(R.id.lv_location_position)
    ListView lv_location_position;
    @BindView(R.id.pb_location_load_bar)
    ProgressBar pb_location_load_bar;
    @BindView(R.id.img_location_back_origin)
    ImageView img_location_back_origin;
    @BindView(R.id.bmapView)
    MapView bmapView;
    @BindView(R.id.wheel_add_address)
    RelativeLayout mAddAddress;
    @BindView(R.id.id_province)
    WheelView mProvince;
    @BindView(R.id.id_city)
    WheelView mCity;
    @BindView(R.id.id_area)
    WheelView mArea;
    @BindView(R.id.iv_qiandao)
    ImageView ivQiandao;

    //放大缩小动画
    Animation mAnimation = null;
    private Context mContext;
    /**
     * 列表适配器
     */
    private LocationAdapter locatorAdapter;
    /**
     * 列表数据
     */
    private List<PoiInfo> datas;
    /**
     * 百度地图对象
     */
    private BaiduMap mBaiduMap;
    /**
     * 地理编码
     */
    private GeoCoder mSearch;
    /**
     * 定位
     */
    private LocationClient mLocClient;
    private MyLocationListener myLocationListener = new MyLocationListener();
    // MapView 中央对于的屏幕坐标
    private Point mCenterPoint = null;
    /**
     * 当前经纬度
     */
    private LatLng mLoactionLatLng;
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
    private boolean isTouch = true;
    private SubscriberOnNextListener<JSONObject> locateOnNext;
    private String city = "";
    private String lat = "";
    private String lon = "";


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
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_map_test);
        ButterKnife.bind(this);
        initUI();
    }

    @Override
    public void initData() {
        locateOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                showPopupWindow(jsonObject.getJSONObject("data").getString("number"), jsonObject.getJSONObject("data").getString("numberpercentage"));
            } else {
                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };

        initJsonData();
        initDatas();
        mAnimation = AnimationUtils.loadAnimation(this, R.anim.bigsmall);
        ivQiandao.setAnimation(mAnimation);
        mAnimation.start();


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
    }

    /**
     * 初始化Ui
     */
    private void initUI() {
        mContext = this;
        lv_location_position = findViewById(R.id.lv_location_position);
        pb_location_load_bar = findViewById(R.id.pb_location_load_bar);
        img_location_back_origin = findViewById(R.id.img_location_back_origin);
        bmapView = findViewById(R.id.bmapView);

        // 地图初始化
        mBaiduMap = bmapView.getMap();
        // 设置为普通矢量图地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        bmapView.setPadding(10, 0, 0, 10);
        bmapView.showZoomControls(false);
        // 设置缩放比例(500米)
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
        mBaiduMap.setOnMapTouchListener(touchListener);

        // 初始化当前 MapView 中心屏幕坐标
        mCenterPoint = mBaiduMap.getMapStatus().targetScreen;
        mLoactionLatLng = mBaiduMap.getMapStatus().target;

        // 地理编码
        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(this);

        // 地图状态监听
        mBaiduMap.setOnMapStatusChangeListener(this);
        // 定位初始化
        mLocClient = new LocationClient(getApplicationContext());
        mLocClient.registerLocationListener(myLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        // 可定位
        mBaiduMap.setMyLocationEnabled(true);

        // 列表初始化
        datas = new ArrayList();
        locatorAdapter = new LocationAdapter(this, datas);
        lv_location_position.setAdapter(locatorAdapter);

        // 注册监听
        lv_location_position.setOnItemClickListener(this);
        img_location_back_origin.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        img_location_back_origin.setImageResource(R.drawable.back_origin_normal);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            locatorAdapter.setSelectItemIndex(0);

            // 获取经纬度
            LatLng latLng = data.getParcelableExtra("LatLng");

            // 实现动画跳转
            MapStatusUpdate u = MapStatusUpdateFactory
                    .newLatLng(latLng);
            mBaiduMap.animateMapStatus(u);
            mSearch.reverseGeoCode((new ReverseGeoCodeOption())
                    .location(latLng));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_location_back_origin:  //回到原点
                if (mLoactionLatLng != null) {
                    // 实现动画跳转
                    img_location_back_origin.setImageResource(R.drawable.back_origin_select);
                    MapStatusUpdate u = MapStatusUpdateFactory
                            .newLatLng(mLoactionLatLng);
                    mBaiduMap.animateMapStatus(u);
                    mSearch.reverseGeoCode((new ReverseGeoCodeOption())
                            .location(mLoactionLatLng));
                }
                break;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || bmapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    .latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);

            Double mLatitude = location.getLatitude();
            Double mLongitude = location.getLongitude();

            LatLng currentLatLng = new LatLng(mLatitude, mLongitude);
            mLoactionLatLng = new LatLng(mLatitude, mLongitude);

            // 是否第一次定位
            if (isFirstLoc) {
                isFirstLoc = false;
                // 实现动画跳转
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(currentLatLng);
                mBaiduMap.animateMapStatus(u);

                mSearch.reverseGeoCode((new ReverseGeoCodeOption()).location(currentLatLng));
                city = location.getCity();
                lat = location.getLatitude() + "";
                lon = location.getLongitude() + "";
                return;
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    // 地图触摸事件监听器
    BaiduMap.OnMapTouchListener touchListener = new BaiduMap.OnMapTouchListener() {
        @Override
        public void onTouch(MotionEvent event) {
            isTouch = true;
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // 显示列表，查找附近的地点
                searchPoi();
                img_location_back_origin.setImageResource(R.drawable.back_origin_normal);
            }
        }
    };

    /**
     * 显示列表，查找附近的地点
     */
    public void searchPoi() {
        if (mCenterPoint == null) {
            return;
        }

        // 获取当前 MapView 中心屏幕坐标对应的地理坐标
        LatLng currentLatLng = mBaiduMap.getProjection().fromScreenLocation(
                mCenterPoint);
        // 发起反地理编码检索
        mSearch.reverseGeoCode((new ReverseGeoCodeOption())
                .location(currentLatLng));
        pb_location_load_bar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        bmapView.onDestroy();
        bmapView = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        bmapView.onPause();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        isTouch = false;
        // 设置选中项下标，并刷新
        locatorAdapter.setSelectItemIndex(position);
        locatorAdapter.notifyDataSetChanged();

        mBaiduMap.clear();
        PoiInfo info = (PoiInfo) locatorAdapter.getItem(position);
        LatLng la = info.location;
        // 获取位置
        mLocationValue = info.name;
        Toast.makeText(mContext, info.city, Toast.LENGTH_SHORT).show();

        // 动画跳转
        MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(la);
        mBaiduMap.animateMapStatus(u);

        img_location_back_origin.setImageResource(R.drawable.back_origin_normal);
    }

    @Override
    public void onGetGeoCodeResult(GeoCodeResult result) {
        // 正向地理编码指的是由地址信息转换为坐标点的过程
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
            return;
        }
        // 获取反向地理编码结果
        PoiInfo mCurrentInfo = new PoiInfo();
        mCurrentInfo.address = result.getAddress();
        mCurrentInfo.location = result.getLocation();
        mCurrentInfo.name = result.getAddress();
        mLocationValue = result.getAddress();
        datas.clear();
        if (!TextUtils.isEmpty(mLocationValue)) {
            datas.add(mCurrentInfo);
        }
        if (result.getPoiList() != null && result.getPoiList().size() > 0) {
            datas.addAll(result.getPoiList());
            city = result.getPoiList().get(0).city;
            lat = result.getPoiList().get(0).location.latitude + "";
            lon = result.getPoiList().get(0).location.longitude + "";
        }
        locatorAdapter.notifyDataSetChanged();
        pb_location_load_bar.setVisibility(View.GONE);
    }

    /**
     * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
     *
     * @param status 地图状态改变开始时的地图状态
     */
    public void onMapStatusChangeStart(MapStatus status) {

    }

    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

    }

    /**
     * 地图状态变化中
     *
     * @param status 当前地图状态
     */
    public void onMapStatusChange(MapStatus status) {
        if (isTouch) {
            datas.clear();
            mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(status.target));
            lv_location_position.setSelection(0);
            locatorAdapter.setSelectItemIndex(0);
        }
    }

    /**
     * 地图状态改变结束
     *
     * @param status 地图状态改变结束后的地图状态
     */
    public void onMapStatusChangeFinish(MapStatus status) {

    }

    @OnClick({R.id.iv_comfirm_back, R.id.iv_qiandao, R.id.tv_choose, R.id.tv_choose_cancel, R.id.tv_choose_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.iv_qiandao:
                if (city.equals("")) {
                    Toast.makeText(mContext, "请选择所在地区！", Toast.LENGTH_SHORT).show();
                } else {
                    HttpJsonMethod.getInstance().locate(
                            new ProgressSubscriber(locateOnNext, MapTestActivity.this),
                            (String) SharedPreferencesUtils.getParam(this, "session", ""),
                            city, (String) SharedPreferencesUtils.getParam(this, "language", "zh"), lat, lon);
                }
                break;
            case R.id.tv_choose:
                mAddAddress.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_choose_cancel:
                mAddAddress.setVisibility(View.GONE);
                break;
            case R.id.tv_choose_confirm:
                mAddAddress.setVisibility(View.GONE);
                HttpJsonMethod.getInstance().locate(
                        new ProgressSubscriber(locateOnNext, MapTestActivity.this),
                        (String) SharedPreferencesUtils.getParam(this, "session", ""),
                        mCurrentProviceName + mCurrentCityName, (String) SharedPreferencesUtils.getParam(this,
                                "language", "zh"), "", "");
                break;
        }
    }

    private void showPopupWindow(String number, String numberPercent) {
        View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_map, null);
        final TextView mTvDays = contentView.findViewById(R.id.tv_pop_days);
        final TextView mTvPercent = contentView.findViewById(R.id.tv_pop_percent);
        final TextView mTvMap = contentView.findViewById(R.id.tv_pop_map);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
        mTvDays.setText(number);
        mTvPercent.setText(Float.valueOf(numberPercent) * 100 + "");
        ColorDrawable dw = new ColorDrawable(0xcF000000);
        popupWindow.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });
        mTvMap.setOnClickListener(v -> popupWindow.dismiss());
        popupWindow.showAtLocation(MapTestActivity.this.findViewById(R.id.iv_comfirm_back),
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    /**
     * 根据当前的市，更新区WheelView的信息
     */
    private void updateAreas() {
        int pCurrent = mCity.getCurrentItem();
        mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
//        mCurrentCityId = mCitisDatasID.get(mCurrentProviceName)[pCurrent];
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
//        mCurrentProviceId = mProvinceIds[pCurrent];
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
                        /**
                         * Throws JSONException if the mapping doesn't exist or
                         * is not a JSONArray.
                         */
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
            InputStream inputStream = getApplication().getResources().getAssets().open("city.json");
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

//    private void showPopupWindow() {
//        View contentView = LayoutInflater.from(this).inflate(
//                R.layout.pop_wheel, null);
//        initJsonData();
//        mProvince = contentView.findViewById(R.id.id_province);
//        mCity = contentView.findViewById(R.id.id_city);
//        mArea = contentView.findViewById(R.id.id_area);
//
//        initDatas();
//        updateCities();
//        updateAreas();
//        mProvince.setViewAdapter(new ArrayWheelAdapter<>(this, mProvinceDatas));
//        // 添加change事件
//        mProvince.addChangingListener(this);
//        // 添加change事件
//        mCity.addChangingListener(this);
//        // 添加change事件
//        mArea.addChangingListener(this);
//
//        mProvince.setVisibleItems(5);
//        mCity.setVisibleItems(5);
//        mArea.setVisibleItems(5);
//
//
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT,
//                true);
//
//        ColorDrawable dw = new ColorDrawable(0x00000000);
//        popupWindow.setBackgroundDrawable(dw);
//        WindowManager.LayoutParams lp = getWindow().getAttributes();
//        lp.alpha = 0.4f;
//        getWindow().setAttributes(lp);
//        popupWindow.setOnDismissListener(() -> {
//            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
//            lp1.alpha = 1f;
//            getWindow().setAttributes(lp1);
//        });
//
//        popupWindow.showAtLocation(MapTestActivity.this.findViewById(R.id.iv_qiandao),
//                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//    }
}
