package com.wshoto.user.anyong.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wshoto.user.anyong.Bean.UserInfoBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.step.service.StepService;
import com.wshoto.user.anyong.ui.widget.InitActivity;
import com.wshoto.user.anyong.ui.widget.RoundImageView;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;

public class MainActivity extends InitActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_STORAGE_CONTACTS_PERM = 124;
    private static final int RC_MAP_CONTACTS_PERM = 125;
    private static final int RC_BBS = 126;//跳转不定期更新页面申请摄像头权限

    private static final int SHOW_SUBACTIVITY = 1;

    private UserInfoBean userInfoBean;

    @BindView(R.id.iv_main_logo)
    RoundImageView ivMainLogo;
    @BindView(R.id.tv_main_name)
    TextView tvMainName;
    @BindView(R.id.tv_user_credit)
    TextView tvUserCredit;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.iv_birthday)
    ImageView birthday;
    @BindView(R.id.ll_zuobiao)
    LinearLayout mLlMap;
    private SubscriberOnNextListener<JSONObject> infoOnNext;
    private SubscriberOnNextListener<JSONObject> newerOnNext;
    private SubscriberOnNextListener<JSONObject> scanOnNext;
    private Gson mGson = new Gson();
    private boolean isBind = false;
    private String fileName = Environment.getExternalStorageDirectory() + "/anyong.png";


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupService();

    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().userInfo(
                new ProgressSubscriber(infoOnNext, MainActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
    }

    @Override
    public void initData() {
        saveImg();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).writeDebugLogs().build();
        // 初始化
        ImageLoader.getInstance().init(configuration);
        newerOnNext = jsonObject -> {
        };
        infoOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                userInfoBean = mGson.fromJson(jsonObject.toString(), UserInfoBean.class);
                tvMainName.setText(userInfoBean.getData().getEnglish_name());
                tvUserCredit.setText(userInfoBean.getData().getIntegral() + "");
                tvUserLevel.setText(userInfoBean.getData().getNickname());
                if (userInfoBean.getData().getAvatar().equals("")) {
                    ivMainLogo.setImageDrawable(getResources().getDrawable(R.drawable.tx));
                } else {
                    loadImage(userInfoBean.getData().getAvatar());
                }
                if (isDate2Bigger(userInfoBean.getData().getBirthday())) {
                    birthday.setVisibility(View.VISIBLE);
                }
                HttpJsonMethod.getInstance().newer(
                        new ProgressSubscriber(newerOnNext, MainActivity.this), userInfoBean.getData().getJob_no(),
                        (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
            } else if (jsonObject.getJSONObject("message").getString("status").equals("session错误")) {
                show();
            } else if (jsonObject.getJSONObject("message").getString("status").equals("session error!")) {
                show();
            } else {
                Toast.makeText(this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
        scanOnNext = jsonObject -> {
            Toast.makeText(MainActivity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            if (jsonObject.getJSONObject("data").getInt("is_report") == 1) {
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                intent.putExtra("mine", true);
                intent.putExtra("id", "" + jsonObject.getJSONObject("data").getInt("activity_id"));
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
                intent.putExtra("mine", false);
                intent.putExtra("id", "" + jsonObject.getJSONObject("data").getInt("activity_id"));
                startActivity(intent);
            }
        };
        RxView.clicks(mLlMap)
                .throttleFirst(3, TimeUnit.SECONDS)
                .subscribe((o -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            requestPermissions(new String[]{ACCESS_FINE_LOCATION}, RC_MAP_CONTACTS_PERM);
                        } else {
                            gomap();
                        }
                    } else {
                        gomap();
                    }
                }));

    }

    @OnClick({R.id.iv_main_sao, R.id.iv_main_email, R.id.iv_main_logo, R.id.tv_main_point, R.id.iv_main_guide,
            R.id.ll_part2, R.id.ll_part3, R.id.ll_part4, R.id.ll_part5, R.id.ll_part6,
            R.id.ll_part7, R.id.ll_part8, R.id.tv_user_credit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_main_sao:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{CAMERA}, RC_CAMERA_PERM);
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
                startActivity(new Intent(MainActivity.this, DeleteActivity.class));
                break;
            case R.id.ll_part7:
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
            case R.id.tv_user_credit:
                startActivity(new Intent(MainActivity.this, PointActivity.class));
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

    @AfterPermissionGranted(RC_MAP_CONTACTS_PERM)
    public void gomap() {
        String[] perms = {ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent intent = new Intent(this, MapTestActivity.class);
            startActivityForResult(intent, SHOW_SUBACTIVITY);
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location),
                    RC_MAP_CONTACTS_PERM, perms);
        }
    }

    @AfterPermissionGranted(RC_BBS)
    public void goBBS() {
        String[] perms = {ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent bbs = new Intent(MainActivity.this, BBSActivity.class);
            bbs.putExtra("id", userInfoBean.getData().getId());
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (null != data) {
//            String code = data.getStringExtra("code").substring(data.getStringExtra("code").indexOf("=") + 1);
//            Toast.makeText(this, data.getStringExtra("code"), Toast.LENGTH_SHORT).show();
            HttpJsonMethod.getInstance().scan(
                    new ProgressSubscriber(scanOnNext, MainActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    data.getStringExtra("code"), (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        Intent intent1 = new Intent(MainActivity.this, MainActivity.class);
        startActivity(intent1);
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(getText(R.string.logout_error));
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);

        builder.setPositiveButton(getText(R.string.confirm), (dialog, which) -> {
            dialog.dismiss();
            boolean auto = (boolean) SharedPreferencesUtils.getParam(this, "language_auto", true);
            String lang = (String) SharedPreferencesUtils.getParam(this, "language", "zh");
            String device_token = (String) SharedPreferencesUtils.getParam(this, "device_token", "");
            SharedPreferencesUtils.clear(getApplicationContext());
            SharedPreferencesUtils.setParam(getApplicationContext(), "first", false);
            SharedPreferencesUtils.setParam(getApplicationContext(), "language_auto", auto);
            SharedPreferencesUtils.setParam(getApplicationContext(), "language", lang);
            SharedPreferencesUtils.setParam(getApplicationContext(), "device_token", device_token);
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        });

//        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
//            mTvHealthyNumber.setText(stepService.getStepCount() + "");
            SharedPreferencesUtils.setParam(getApplicationContext(), "step", stepService.getStepCount() + "");

            //设置步数监听回调
            stepService.registerCallback(stepCount ->
                    SharedPreferencesUtils.setParam(getApplicationContext(), "step", stepCount + ""));
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }

    public boolean isDate2Bigger(String str1) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = null;
        String current = sdf.format(new Date(System.currentTimeMillis()));
        Date dt2 = null;
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(current);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getMonth() == dt2.getMonth() && dt1.getDate() == dt2.getDate()) {
            isBigger = true;
        }
        return isBigger;
    }

    private void loadImage(String url) {
        // 图片路径
        String uri = (url);
        // 图片大小
        ImageSize mImageSize = new ImageSize(300, 300);
        // 图片的配置
        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();

        ImageLoader.getInstance().loadImage(uri, mImageSize, mOptions,
                new ImageLoadingListener() {

                    @Override
                    public void onLoadingStarted(String arg0, View arg1) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLoadingFailed(String arg0, View arg1,
                                                FailReason arg2) {
                        // TODO Auto-generated method stub

                    }

                    @Override
                    public void onLoadingComplete(String arg0, View arg1,
                                                  Bitmap arg2) {
                        ivMainLogo.setImageBitmap(arg2);

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        // TODO Auto-generated method stub

                    }
                });
    }

    @AfterPermissionGranted(RC_STORAGE_CONTACTS_PERM)
    public void saveImg() {
        String[] perms = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permissions, do the thing!
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
            File file = new File(fileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, fos);
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location),
                    RC_STORAGE_CONTACTS_PERM, perms);
        }
    }
}
