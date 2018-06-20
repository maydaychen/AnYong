package com.wshoto.user.anyong.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.UserInfoBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
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
    private UserInfoBean userInfoBean;

    @BindView(R.id.iv_main_logo)
    SmartImageView ivMainLogo;
    @BindView(R.id.tv_main_name)
    TextView tvMainName;
    @BindView(R.id.tv_user_credit)
    TextView tvUserCredit;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    private SubscriberOnNextListener<JSONObject> infoOnNext;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().userInfo(
                new ProgressSubscriber(infoOnNext, MainActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""));
    }

    @Override
    public void initData() {
        infoOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                userInfoBean = mGson.fromJson(jsonObject.toString(), UserInfoBean.class);
                tvMainName.setText(userInfoBean.getData().getEnglish_name());
                tvUserCredit.setText(userInfoBean.getData().getIntegral() + "");
                tvUserLevel.setText(userInfoBean.getData().getNickname());
                ivMainLogo.setImageUrl(userInfoBean.getData().getAvatar());
            } else {
                Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
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
                Intent honour = new Intent(MainActivity.this, HonourActivity.class);
                honour.putExtra("id", userInfoBean.getData().getId());
                startActivity(honour);
                break;
            case R.id.ll_part4:
                startActivity(new Intent(MainActivity.this, MyRadiiActivity.class));
//                startActivity(new Intent(MainActivity.this, FriendInfoActivity.class));
                break;
            case R.id.ll_part5:
                startActivity(new Intent(MainActivity.this, HealthyLifeActivity.class));
                break;
            case R.id.ll_part6:
                startActivity(new Intent(MainActivity.this, CalendarActivity.class));
                break;
            case R.id.ll_part7:
                Intent bbs = new Intent(MainActivity.this, BBSActivity.class);
                bbs.putExtra("id", userInfoBean.getData().getId());
                startActivity(bbs);
                break;
            case R.id.ll_part8:
                Intent anni = new Intent(MainActivity.this, AnniversaryActivity.class);
                anni.putExtra("id", userInfoBean.getData().getId());
                startActivity(anni);
                break;
            case R.id.iv_main_logo:
                Intent intent = new Intent(MainActivity.this, PersonActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("person", userInfoBean.getData());
                intent.putExtras(bundle);
                startActivity(intent);
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent1);
    }
}
