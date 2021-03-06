package com.wshoto.user.anyong.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.view.RxView;
import com.loopj.android.image.SmartImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wshoto.user.anyong.Bean.UpdateBean;
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
import static com.wshoto.user.anyong.Utils.compareVersion;

/**
 * The type Main 2 activity.
 */
public class Main2Activity extends InitActivity implements EasyPermissions.PermissionCallbacks {
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
    @BindView(R.id.tv_user_friend_num)
    TextView tvUserFriendNum;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    @BindView(R.id.iv_birthday)
    SmartImageView birthday;
    @BindView(R.id.ll_zuobiao)
    RelativeLayout mLlMap;
    @BindView(R.id.ll_main_background)
    LinearLayout llBackground;
    @BindView(R.id.iv_background)
    ImageView ivBackground;
    @BindView(R.id.ll_part8)
    LinearLayout Llprat8;
    @BindView(R.id.view_dot)
    TextView mViewDot;
    @BindView(R.id.rl_dot)
    View mRlDot;
    private SubscriberOnNextListener<JSONObject> infoOnNext;
    private SubscriberOnNextListener<JSONObject> newerOnNext;
    private SubscriberOnNextListener<JSONObject> scanOnNext;
    private SubscriberOnNextListener<JSONObject> updateOnNext;
    private UpdateBean mUpdateBean;
    private Gson mGson = new Gson();
    private boolean isBind = false;
    private String fileName = Environment.getExternalStorageDirectory() + "/anyong.png";
    private Context mContext;
    private String path;
    private String userID;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        setupService();

    }

    //每次onResume都会重新获取用户信息以及检查更新
    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().userInfo(
                new ProgressSubscriber(infoOnNext, Main2Activity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        HttpJsonMethod.getInstance().update(
                new ProgressSubscriber(updateOnNext, Main2Activity.this));
    }

    @Override
    public void initData() {
        mContext = this;
        userID = (String) SharedPreferencesUtils.getParam(this, "user_id", "1");
        path = mContext.getExternalFilesDir(userID).toString() + "/background.jpg";
        if ((Boolean) SharedPreferencesUtils.getParam(this, userID, false)) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            Drawable drawable = new BitmapDrawable(getResources(), bitmap);
//            llBackground.setBackground(drawable);
            ivBackground.setImageDrawable(drawable);
        }
        saveImg();
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).writeDebugLogs().build();
        // 初始化ImageLoader
        ImageLoader.getInstance().init(configuration);
        newerOnNext = jsonObject -> {
        };
        updateOnNext = jsonObject -> {
            mUpdateBean = mGson.fromJson(jsonObject.toString(), UpdateBean.class);
            checkUpdate(mUpdateBean.getData().getVandroid());
        };
        infoOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                userInfoBean = mGson.fromJson(jsonObject.toString(), UserInfoBean.class);
                mViewDot.setText(userInfoBean.getData().getCounts() + "");
                if (userInfoBean.getData().getCounts() != 0) {
                    mRlDot.setVisibility(View.VISIBLE);
                } else {
                    mRlDot.setVisibility(View.GONE);
                }
                SharedPreferencesUtils.setParam(getApplicationContext(), "user_id", userInfoBean.getData().getIntegral());//存储用户id
                SharedPreferencesUtils.setParam(getApplicationContext(), "user_name", userInfoBean.getData().getEnglish_name());//存储用户id
                tvMainName.setText(userInfoBean.getData().getEnglish_name());
                tvUserCredit.setText(String.format((String) getResources().getText(R.string.credit1), userInfoBean.getData().getIntegral() + ""));
                tvUserLevel.setText(String.format((String) getResources().getText(R.string.level), userInfoBean.getData().getNickname()));
                tvUserFriendNum.setText(String.format((String) getResources().getText(R.string.frined), userInfoBean.getData().getFirend_num()));
//                tvUserLevel.setText(userInfoBean.getData().getNickname());
                if (userInfoBean.getData().getAvatar().equals("")) {
                    ivMainLogo.setImageDrawable(getResources().getDrawable(R.drawable.tx));
                } else {
                    loadImage(userInfoBean.getData().getAvatar());
                }
                ivMainLogo.setOnClickListener(v -> {
                    Intent person = new Intent(Main2Activity.this, PersonActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("person", userInfoBean.getData());
                    person.putExtras(bundle);
                    startActivity(person);
                });
                if (isDate2Bigger(userInfoBean.getData().getBirthday())) {
                    birthday.setImageDrawable(getResources().getDrawable(R.drawable.hg));
                } else if (userInfoBean.getData().getSwitcherHat() == 1) {
                    birthday.setImageUrl(userInfoBean.getData().getHatUrl());
                }
                if (userInfoBean.getData().getSwitchX() == 0) {
                    Llprat8.setClickable(false);
                }
                HttpJsonMethod.getInstance().newer(
                        new ProgressSubscriber(newerOnNext, Main2Activity.this), userInfoBean.getData().getJob_no(),
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
            Toast.makeText(Main2Activity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            if (jsonObject.getJSONObject("data").getInt("is_report") == 1) {
                Intent intent = new Intent(Main2Activity.this, EventDetailActivity.class);
                intent.putExtra("mine", true);
                intent.putExtra("id", "" + jsonObject.getJSONObject("data").getInt("activity_id"));
                startActivity(intent);
            } else {
                Intent intent = new Intent(Main2Activity.this, EventDetailActivity.class);
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

    /**
     * On view clicked.
     *
     * @param view the view
     */
    @OnClick({R.id.iv_main_sao, R.id.iv_main_email, R.id.iv_main_guide,
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
                Intent intent = new Intent(Main2Activity.this, MessageCenterActivity.class);
                intent.putExtra("id", userInfoBean.getData().getId());
                startActivity(intent);
                break;
            case R.id.iv_main_guide:
                Intent intent1 = new Intent(Main2Activity.this, GuideActivity.class);
                intent1.putExtra("login", false);
                startActivity(intent1);
                break;
            case R.id.ll_part2:
                startActivity(new Intent(Main2Activity.this, ThankYouActivity.class));
                break;
            case R.id.ll_part3:
                Intent honour = new Intent(Main2Activity.this, HonourActivity.class);
                honour.putExtra("id", userInfoBean.getData().getId());
                startActivity(honour);
                break;
            case R.id.ll_part4:
                startActivity(new Intent(Main2Activity.this, MyRadiiActivity.class));
//                startActivity(new Intent(Main2Activity.this, FriendInfoActivity.class));
                break;
            case R.id.ll_part5:
                startActivity(new Intent(Main2Activity.this, HealthyLifeActivity.class));
                break;
            case R.id.ll_part6:
                startActivity(new Intent(Main2Activity.this, ShuashuaActivity.class));
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
                Intent anni = new Intent(Main2Activity.this, AnniversaryActivity.class);
                anni.putExtra("id", userInfoBean.getData().getId());
                startActivity(anni);
                break;
            case R.id.tv_user_credit:
                startActivity(new Intent(Main2Activity.this, PointActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * Init.
     */
    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void init() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            Intent intent = new Intent(this, CaptureActivity.class);
            startActivityForResult(intent, SHOW_SUBACTIVITY);
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    /**
     * Gomap.
     */
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

    /**
     * Go bbs.
     */
    @AfterPermissionGranted(RC_BBS)
    public void goBBS() {
        String[] perms = {ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent bbs = new Intent(Main2Activity.this, BBSActivity.class);
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
                    new ProgressSubscriber(scanOnNext, Main2Activity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    data.getStringExtra("code"), (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        finish();
        Intent intent1 = new Intent(Main2Activity.this, Main2Activity.class);
        startActivity(intent1);
    }

    /**
     * Show.
     */
    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
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
            Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
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

    /**
     * Is date 2 bigger boolean.
     *
     * @param str1 the str 1
     * @return the boolean
     */
//比较日期大小
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

    //将网络图片url加载成bitmao
    private void loadImage(String url) {
        // 图片路径
        String uri = (url);
        // 图片大小
        ImageSize mImageSize = new ImageSize(300, 300);
        // 图片的配置
        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        //imageloader读取图片
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

    /**
     * Save img.
     */
    //保存图片到手机
    //注意需要动态获取权限
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

    /**
     * checkUpdate
     * 检查版本是否为最新
     */
    private void checkUpdate(String nowVersion) {
        String version = "";
        try {
            PackageManager manager = getApplicationContext().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getApplicationContext().getPackageName(), 0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (compareVersion(version, nowVersion) < 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(Main2Activity.this);
            builder.setMessage(getText(R.string.update));
            builder.setTitle(R.string.app_name);
            builder.setCancelable(false);

            builder.setPositiveButton(getText(R.string.confirm), (dialog, which) -> {
                dialog.dismiss();
            });

            builder.setNegativeButton(getText(R.string.cancel), (dialog, which) -> dialog.dismiss());

            builder.create().show();
        }
    }
}
