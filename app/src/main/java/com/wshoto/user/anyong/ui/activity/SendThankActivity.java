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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.ThankThemeBean;
import com.wshoto.user.anyong.Bean.ThankUserBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.Utils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressErrorSubscriber;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextAndErrorListener;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class SendThankActivity extends InitActivity implements EasyPermissions.PermissionCallbacks {

    @BindView(R.id.tv_thank_select)
    TextView mTvThankSelect;
    @BindView(R.id.iv_thank_upload)
    ImageView mIvThankUpload;
    @BindView(R.id.et_thank_content)
    EditText mEtThankContent;
    @BindView(R.id.spinner)
    Spinner spinner;
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    final public static int REQUEST_WRITE = 222;
    private SubscriberOnNextListener<JSONObject> sendOnNext;
    private SubscriberOnNextAndErrorListener<JSONObject> uploadOnNext;
    private ThankThemeBean mThankThemeBean;
    private ProgressDialog updateDialog = null;
    private Gson mGson = new Gson();
    private Bitmap bmp;
    private File picFile;
    private String userid = "";
    private String themeid = "";
    private String url = "";

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_send_thank);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        SubscriberOnNextListener<JSONObject> themeOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                List<String> list = new ArrayList();
                list.add(getText(R.string.temp_choose).toString());
                mThankThemeBean = mGson.fromJson(jsonObject.toString(), ThankThemeBean.class);
                for (ThankThemeBean.DataBean dataBean : mThankThemeBean.getData()) {
                    list.add(dataBean.getId());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //绑定 Adapter到控件
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        //showPrice(position);
                        TextView tv = (TextView) view;
                        tv.setTextColor(getResources().getColor(R.color.yellow));    //设置颜色
                        tv.setTextSize(14.0f);    //设置大小
                        tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                        if (position != 0) {
                            themeid = mThankThemeBean.getData().get(position - 1).getId();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
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
                    Toast.makeText(SendThankActivity.this, getText(R.string.upload_success), Toast.LENGTH_SHORT).show();
                    url = jsonObject.getString("data");
                }
                deletePic();

            }

            @Override
            public void onError(Throwable e) {
                if (updateDialog != null) {
                    updateDialog.dismiss();
                }
                Toast.makeText(SendThankActivity.this, getText(R.string.upload_fail), Toast.LENGTH_SHORT).show();
                Log.d("wjj", "err");
                deletePic();
                e.printStackTrace();
            }
        };
        sendOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Intent intent = new Intent(SendThankActivity.this,ThankPreviewActivity.class );
                intent.putExtra("id", jsonObject.getInt("data"));
                startActivity(intent);
            } else {
                Toast.makeText(SendThankActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        HttpJsonMethod.getInstance().thankTheme(
                new ProgressSubscriber(themeOnNext, SendThankActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""));
    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_thanku_send, R.id.iv_thank_upload, R.id.tv_thank_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_thanku_send:
                sendThank();
                break;
            case R.id.iv_thank_upload:
                showType();
                break;
            case R.id.tv_thank_select:
                startActivityForResult(new Intent(SendThankActivity.this, ThankSelectActivity.class), 99);
                break;
            default:
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
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SendThankActivity.this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SendThankActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CALL_PHONE);
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
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SendThankActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SendThankActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
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
                            mIvThankUpload.setImageBitmap(bmp);
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
                        mIvThankUpload.setImageBitmap(bmp);
                        upDataHeadImg();
                    }
                    break;
                case 99:
                    ThankUserBean.DataBean addressBean = (ThankUserBean.DataBean) data.getSerializableExtra("user");
                    mTvThankSelect.setText(addressBean.getEnglish_name());
                    userid = addressBean.getId();
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
        if (isIntentAvailable(SendThankActivity.this, intent)) {
            startActivityForResult(Intent.createChooser(intent, "选择图片"), 100);
        } else {
            Toast.makeText(SendThankActivity.this, "请安装相关图片查看应用。", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建上传图片文件
     */
    private void createPicFile() {
        String sdStatus = Environment.getExternalStorageState();
        // 检测sd是否可用
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(SendThankActivity.this, getText(R.string.check_sd), Toast.LENGTH_SHORT).show();
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

    private void sendThank() {
        if (userid.equals("") || themeid.equals("") || bmp == null || mEtThankContent.getText().toString().equals("")) {
            Toast.makeText(this, getText(R.string.text_error), Toast.LENGTH_SHORT).show();
            return;
        }
        HttpJsonMethod.getInstance().previewThank(
                new ProgressSubscriber(sendOnNext, SendThankActivity.this), userid, themeid, url,
                (String) SharedPreferencesUtils.getParam(this, "session", ""), mEtThankContent.getText().toString());
    }

    /**
     * 上传用户头像
     */
    private void upDataHeadImg() {
        if (updateDialog == null) {
            updateDialog = ProgressDialog.show(SendThankActivity.this, getText(R.string.update_img), getText(R.string.update_img_ing), true, false);
        }
        HttpJsonMethod.getInstance().uploadImg(
                new ProgressErrorSubscriber<>(uploadOnNext, SendThankActivity.this), Utils.bitmaptoString(bmp));
    }
}
