package com.wshoto.user.anyong.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.MessageCenterBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.MessageCenterAdapter;
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

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MessageCenterActivity extends InitActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.rv_points)
    RecyclerView rvPoints;
    @BindView(R.id.tv_hint)
    TextView hint;

    private SubscriberOnNextListener<JSONObject> messageOnNext;
    private SubscriberOnNextListener<JSONObject> readOnNext;
    private MessageCenterBean mMessageBean;
    private Gson mGson = new Gson();
    private String ID = "";
    private int position = 0;
    private static final int RC_BBS = 126;//跳转不定期更新页面申请摄像头权限

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_message_center);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        messageOnNext = jsonObject -> {
            Log.i("chenyi", "message: " + jsonObject.toString());
            if (jsonObject.getInt("code") == 1) {
                mMessageBean = mGson.fromJson(jsonObject.toString(), MessageCenterBean.class);
                rvPoints.setLayoutManager(new LinearLayoutManager(this));
                MessageCenterAdapter messageCenterAdapter = new MessageCenterAdapter(getApplicationContext(), mMessageBean.getData());
                rvPoints.setAdapter(messageCenterAdapter);
                messageCenterAdapter.setOnItemClickListener((view, data) -> {
                    ID = mMessageBean.getData().get(data).getType();
                    position = data;
                    HttpJsonMethod.getInstance().mesageRead(
                            new ProgressSubscriber(readOnNext, MessageCenterActivity.this),
                            (String) SharedPreferencesUtils.getParam(MessageCenterActivity.this, "session", ""),
                            mMessageBean.getData().get(data).getId());
                });
            } else {
                rvPoints.setVisibility(View.GONE);
                hint.setVisibility(View.VISIBLE);
            }
        };
        readOnNext = jsonObject -> {
            switch (ID) {
                case "1":
                    Intent intent = new Intent(MessageCenterActivity.this, MessageDetailActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("info", mMessageBean.getData().get(position));
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case "2":
                    startActivity(new Intent(MessageCenterActivity.this, MyRadiiActivity.class));
                    break;
                case "3":
                    startActivity(new Intent(MessageCenterActivity.this, ThankYouActivity.class));
                    break;
                case "4":
                    startActivity(new Intent(MessageCenterActivity.this, HealthyLifeActivity.class));
                    break;
                case "5":
                    startActivity(new Intent(MessageCenterActivity.this, DeleteActivity.class));
                    break;
                case "6":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{CAMERA}, RC_BBS);
                        } else {
                            goBBS();
                        }
                    } else {
                        goBBS();
                    }
                    break;
            }
        };


    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().mesageList(
                new ProgressSubscriber(messageOnNext, MessageCenterActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }

    @AfterPermissionGranted(RC_BBS)
    public void goBBS() {
        String[] perms = {ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent bbs = new Intent(MessageCenterActivity.this, BBSActivity.class);
            bbs.putExtra("id", getIntent().getStringExtra("id"));
            startActivity(bbs);
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location),
                    RC_BBS, perms);
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
//        if (requestCode == RC_CAMERA_PERM) {
//            Intent intent = new Intent(this, CaptureActivity.class);
//            startActivityForResult(intent, SHOW_SUBACTIVITY);
//        }
//        if (requestCode == RC_MAP_CONTACTS_PERM) {
//            Intent intent = new Intent(this, MapTestActivity.class);
//            startActivity(intent);
//        }
//        if (requestCode == RC_STORAGE_CONTACTS_PERM) {
//            saveImg();
//        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("chenyi", "onPermissionsDenied:" + requestCode + ":" + perms.size());
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }
}
