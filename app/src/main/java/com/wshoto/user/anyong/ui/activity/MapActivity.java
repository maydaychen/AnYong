package com.wshoto.user.anyong.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends InitActivity implements BaiduMap.OnMapLoadedCallback, BDLocationListener {

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private boolean ifFrist = true;
    private String city = "";
    private SubscriberOnNextListener<JSONObject> locateOnNext;
    @BindView(R.id.mv_map)
    MapView mvMap;
    @BindView(R.id.tv_qiandao_location)
    TextView tvQiandaoLocation;

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mvMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mvMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mvMap.onDestroy();
    }

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
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
        initMap();
        mBaiduMap = mvMap.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mBaiduMap.setMyLocationEnabled(true);
    }

    private void initMap() {
        mLocationClient = new LocationClient(MapActivity.this); //声明LocationClient类
        mLocationClient.registerLocationListener(this);//注册监听函数
        initLocation();
        mLocationClient.start();//开启定位
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
        mLocationClient.setLocOption(option);
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
//        MyLocationData locData = new MyLocationData.Builder()
//                .accuracy(bdLocation.getRadius())
//                // 此处设置开发者获取到的方向信息，顺时针0-360
//                .direction(100).latitude(bdLocation.getLatitude())
//                .longitude(bdLocation.getLongitude()).build();
//        mBaiduMap.setMyLocationData(locData);

        if (bdLocation == null)
            return;
        MyLocationData locData = new MyLocationData.Builder()
                .accuracy(bdLocation.getRadius())
// 此处设置开发者获取到的方向信息，顺时针0-360
                .direction(100).latitude(bdLocation.getLatitude())
                .longitude(bdLocation.getLongitude()).build();
        mBaiduMap.setMyLocationData(locData);

        if (ifFrist) {
            ifFrist = false;
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(u);
            city = bdLocation.getCity();
        }
    }

    @Override
    public void onMapLoaded() {

    }


    @OnClick({R.id.iv_comfirm_back, R.id.iv_qiandao})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.iv_qiandao:
//                HttpJsonMethod.getInstance().locate(
//                        new ProgressSubscriber(locateOnNext, MapActivity.this),
//                        (String) SharedPreferencesUtils.getParam(this, "session", ""),
//                        city,(String) SharedPreferencesUtils.getParam(this, "language", "zh"));
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
        popupWindow.showAtLocation(MapActivity.this.findViewById(R.id.ll_map),
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

    }
}
