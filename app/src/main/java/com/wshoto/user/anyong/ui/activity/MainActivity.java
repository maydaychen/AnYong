package com.wshoto.user.anyong.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.CAMERA;

public class MainActivity extends InitActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_PERM = 123;
    private static final int SHOW_SUBACTIVITY = 1;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_main_sao, R.id.iv_main_email, R.id.iv_main_logo, R.id.tv_main_point, R.id.iv_main_guide, R.id.ll_zuobiao, R.id.ll_part2, R.id.ll_part3, R.id.ll_part4, R.id.ll_part5, R.id.ll_part6, R.id.ll_part7, R.id.ll_part8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_sao:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{CAMERA},
                                MY_PERMISSIONS_REQUEST_CALL_PHONE);
                    } else {
                        init();
                    }
                } else {
                    init();
                }
                break;
            case R.id.iv_main_email:
                startActivity(new Intent(MainActivity.this, MessageCenterActivity.class));
                break;
            case R.id.iv_main_guide:
                startActivity(new Intent(MainActivity.this, GuideActivity.class));
                break;
            case R.id.tv_main_point:
                startActivity(new Intent(MainActivity.this, PointActivity.class));
                break;
            case R.id.ll_zuobiao:
                startActivity(new Intent(MainActivity.this, MapActivity.class));
                break;
            case R.id.ll_part2:
                startActivity(new Intent(MainActivity.this, ThankYouActivity.class));
                break;
            case R.id.ll_part3:
                startActivity(new Intent(MainActivity.this, HonourActivity.class));
                break;
            case R.id.ll_part4:
                startActivity(new Intent(MainActivity.this, MyRadiiActivity.class));
                break;
            case R.id.ll_part5:
                startActivity(new Intent(MainActivity.this, HealthyLifeActivity.class));
                break;
            case R.id.ll_part6:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                break;
            case R.id.ll_part7:
                startActivity(new Intent(MainActivity.this, BBSActivity.class));
                break;
            case R.id.ll_part8:
                startActivity(new Intent(MainActivity.this, AnniversaryActivity.class));
                break;
            case R.id.iv_main_logo:
                startActivity(new Intent(MainActivity.this, PersonActivity.class));
                break;
            default:
                break;
        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void init() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, SHOW_SUBACTIVITY);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d("chenyi", "onPermissionsGranted:" + requestCode + ":" + perms.size());
        if (requestCode == RC_CAMERA_PERM) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, SHOW_SUBACTIVITY);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("chenyi", "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
            //todo 扫码获得信息后回调
        }
    }
}
