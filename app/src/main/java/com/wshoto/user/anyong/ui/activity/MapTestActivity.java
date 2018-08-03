package com.wshoto.user.anyong.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapTestActivity extends InitActivity implements AdapterView.OnItemClickListener, OnGetGeoCoderResultListener, BaiduMap.OnMapStatusChangeListener, View.OnClickListener {
    private Context mContext;
    /**
     * 显示的地图
     */
    protected MapView bmapView;
    /**
     * 附近地点列表
     */
    private ListView lv_location_position;
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
    private android.graphics.Point mCenterPoint = null;
    /**
     * 当前经纬度
     */
    private LatLng mLoactionLatLng;
    /**
     * 是否第一次定位
     */
    private boolean isFirstLoc = true;
    /**
     * 进度条
     */
    private ProgressBar pb_location_load_bar;
    /**
     * 获取的位置
     */
    private String mLocationValue;
    /**
     * 按钮：回到原地
     */
    private ImageView img_location_back_origin;
    /**
     * 请求码
     */
    private final static int REQUEST_CODE = 0x123;
    private boolean isTouch = true;
    private SubscriberOnNextListener<JSONObject> locateOnNext;
    private String city = "";

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
    }

    /**
     * 初始化Ui
     */
    private void initUI() {
        mContext = this;
        lv_location_position = (ListView) findViewById(R.id.lv_location_position);
        pb_location_load_bar = (ProgressBar) findViewById(R.id.pb_location_load_bar);
        img_location_back_origin = (ImageView) findViewById(R.id.img_location_back_origin);
        bmapView = (MapView) findViewById(R.id.bmapView);

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
                MapStatusUpdate u = MapStatusUpdateFactory
                        .newLatLng(currentLatLng);
                mBaiduMap.animateMapStatus(u);

                mSearch.reverseGeoCode((new ReverseGeoCodeOption())
                        .location(currentLatLng));
                city = location.getCity();
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

    @OnClick({R.id.iv_comfirm_back, R.id.iv_qiandao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.iv_qiandao:
                if (city.equals("")) {
                    Toast.makeText(mContext, "请选择所在地区！", Toast.LENGTH_SHORT).show();
                }else {
                    HttpJsonMethod.getInstance().locate(
                            new ProgressSubscriber(locateOnNext, MapTestActivity.this), (String) SharedPreferencesUtils.getParam(this, "session", ""), city);
                }
               break;
        }
    }

    private void showPopupWindow(String number, String numberPercent) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.popwindow_map, null);

        final TextView mTvDays = contentView.findViewById(R.id.tv_pop_days);
        final TextView mTvPercent = contentView.findViewById(R.id.tv_pop_percent);
        final TextView mTvMap = contentView.findViewById(R.id.tv_pop_map);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT,
                true);
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
}
