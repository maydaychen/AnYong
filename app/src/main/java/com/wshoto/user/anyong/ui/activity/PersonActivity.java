package com.wshoto.user.anyong.ui.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.wshoto.user.anyong.Bean.UserInfoBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.Utils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressErrorSubscriber;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextAndErrorListener;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;
import com.wshoto.user.anyong.ui.widget.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class PersonActivity extends InitActivity implements EasyPermissions.PermissionCallbacks {
    @BindView(R.id.iv_person_logo)
    RoundImageView ivPersonLogo;
    @BindView(R.id.tv_person_name)
    TextView tvPersonName;
    @BindView(R.id.tv_person_number)
    TextView tvPersonNumber;
    @BindView(R.id.tv_person_bumen)
    TextView tvPersonBumen;
    @BindView(R.id.tv_person_position)
    TextView tvPersonPosition;
    @BindView(R.id.tv_person_tele)
    TextView tvPersonTele;
    @BindView(R.id.tv_person_email)
    TextView tvPersonEmail;
    @BindView(R.id.tv_person_recommend)
    TextView tvPersonRecommend;
    @BindView(R.id.iv_birthday)
    ImageView ivBirthday;
    private UserInfoBean.DataBean mDataBean;
    private SubscriberOnNextAndErrorListener<JSONObject> uploadOnNext;
    private SubscriberOnNextListener<JSONObject> changeOnNext;
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    final public static int REQUEST_WRITE = 222;
    private ProgressDialog updateDialog = null;
    private Bitmap bmp;
    private File picFile;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).writeDebugLogs().build();
        // 初始化
        ImageLoader.getInstance().init(configuration);
    }

    @Override
    public void initData() {
        mDataBean = (UserInfoBean.DataBean) getIntent().getSerializableExtra("person");
        if (isDate2Bigger(mDataBean.getBirthday())) {
            ivBirthday.setVisibility(View.VISIBLE);
        }
        loadImage(mDataBean.getAvatar());
        tvPersonName.setText(mDataBean.getUsername());
        tvPersonNumber.setText(mDataBean.getJob_no());
        tvPersonBumen.setText(mDataBean.getDepartment());
        tvPersonPosition.setText(mDataBean.getPosition());
        tvPersonTele.setText(mDataBean.getMobile());
        tvPersonEmail.setText(mDataBean.getEmail());
        tvPersonRecommend.setText(mDataBean.getInvitecode());
        changeOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(PersonActivity.this, getText(R.string.upload_success), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(PersonActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        uploadOnNext = new SubscriberOnNextAndErrorListener<JSONObject>() {
            @Override
            public void onNext(JSONObject jsonObject) throws JSONException {
                if (updateDialog != null) {
                    updateDialog.dismiss();
                    updateDialog = null;
                }
                if (jsonObject.getInt("code") == 1) {
                    String url = jsonObject.getString("data");
                    loadImage(url);
                    HttpJsonMethod.getInstance().getAva(
                            new ProgressSubscriber<>(changeOnNext, PersonActivity.this),
                            (String) SharedPreferencesUtils.getParam(PersonActivity.this, "session", ""), url);
                }
                deletePic();

            }

            @Override
            public void onError(Throwable e) {
                if (updateDialog != null) {
                    updateDialog.dismiss();
                }
                Toast.makeText(PersonActivity.this, getText(R.string.upload_fail), Toast.LENGTH_SHORT).show();
                Log.d("wjj", "err");
                deletePic();
                e.printStackTrace();
            }
        };
    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_person_setting, R.id.iv_person_logo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_person_setting:
                startActivity(new Intent(getApplicationContext(), SettingActivity.class));
                break;
            case R.id.iv_person_logo:
                showType();
                break;
        }
    }

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void showType() {
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Dialog dialog = new Dialog(this, R.style.BottomDialog);
            View inflate = LayoutInflater.from(this).inflate(R.layout.pop_avatar, null);
            Button camera = inflate.findViewById(R.id.camera);
            Button imgLib = inflate.findViewById(R.id.img_lib);
            Button cancel = inflate.findViewById(R.id.btn_cancel);
            camera.setOnClickListener(v -> {
                dialog.dismiss();
                camera();
            });
            imgLib.setOnClickListener(v -> {
                selectImage();
                dialog.dismiss();
            });
            cancel.setOnClickListener(v -> dialog.dismiss());
            dialog.setContentView(inflate);
            Window dialogWindow = dialog.getWindow();
            dialogWindow.setGravity(Gravity.BOTTOM);
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.y = 20;
            lp.width = -1;
            dialogWindow.setAttributes(lp);
            dialog.show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.permition),
                    RC_LOCATION_CONTACTS_PERM, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d("chenyi", "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d("chenyi", "onPermissionsDenied:" + requestCode + ":" + perms.size());

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    public void camera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(PersonActivity.this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PersonActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CALL_PHONE);
            } else {
                //上面已经写好的拍照方法
                write(true);
            }
        } else {
            //上面已经写好的拍照方法
            write(true);
        }
    }

    public void write(boolean iscamera) {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(PersonActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(PersonActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
            } else {
                if (iscamera) {
                    //上面已经写好的拍照方法
                    takePhoto();
                } else {
                    selectImage();
                }
            }
        } else {
            if (iscamera) {
                //上面已经写好的拍照方法
                takePhoto();
            } else {
                selectImage();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
//         从图库裁减返回
                    Log.d("wjj", "100");
                    if (data != null) {
                        Uri uri = data.getData();
                        ContentResolver cr = this.getContentResolver();
                        try {
                            assert uri != null;
                            bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            Matrix matrix = new Matrix();
                            matrix.setScale(0.5f, 0.5f);
                            bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                                    bmp.getHeight(), matrix, true);
                            upDataHeadImg();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }

                    break;
                case 101:
                    // 从拍照返回
                    if (data != null) {
                        Bundle bundle = data.getExtras();
                        assert bundle != null;
                        bmp = (Bitmap) bundle.get("data");
                        Matrix matrix = new Matrix();
                        matrix.setScale(0.5f, 0.5f);
                        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
                                bmp.getHeight(), matrix, true);
//                        ivThankPic.setImageBitmap(bmp);
                        upDataHeadImg();
                    }
                    break;
                default:
                    break;
            }
        }
    }


    public void deletePic() {
        if (picFile.exists()) {
            picFile.delete();
            if (bmp != null) {
                bmp.recycle();
            }
        }
    }

    /**
     * 拍照
     */
    private void takePhoto() {
        createPicFile();
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 101);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从相册选择
     */
    private void selectImage() {
        createPicFile();
        Intent intent;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            intent.setType("image/*");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
        }
        if (isIntentAvailable(PersonActivity.this, intent)) {
            startActivityForResult(Intent.createChooser(intent, "选择图片"), 100);
        } else {
            Toast.makeText(PersonActivity.this, "请安装相关图片查看应用。", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建上传图片文件
     */
    private void createPicFile() {
        String sdStatus = Environment.getExternalStorageState();
        // 检测sd是否可用
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(PersonActivity.this, getText(R.string.check_sd), Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(Environment.getExternalStorageDirectory().toString());
        if (!file.exists()) {
            file.mkdirs();
        }
        picFile = new File(file
                + "/seawaterHeadImg.jpg");
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }

    /**
     * 上传用户头像
     */
    private void upDataHeadImg() {
        if (updateDialog == null) {
            updateDialog = ProgressDialog.show(PersonActivity.this, getText(R.string.update_img), getText(R.string.update_img_ing), true, false);
        }
        HttpJsonMethod.getInstance().uploadImg(
                new ProgressErrorSubscriber<>(uploadOnNext, PersonActivity.this), Utils.bitmaptoString(bmp));
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
        if (dt1.getMonth() == dt2.getMonth() && dt1.getDay() == dt2.getDay()) {
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
                        ivPersonLogo.setImageBitmap(arg2);

                    }

                    @Override
                    public void onLoadingCancelled(String arg0, View arg1) {
                        // TODO Auto-generated method stub

                    }
                });

    }
}
