package com.wshoto.user.anyong.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class SettingActivity extends InitActivity {
    private SubscriberOnNextListener<JSONObject> logoutOnNext;
    private Bitmap bmp;
    private File picFile;
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    final public static int REQUEST_WRITE = 222;
    private Context mContext;
    private String path;
    private String userID;
    private Uri uri;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        mContext = this;
        userID = (String) SharedPreferencesUtils.getParam(this, "user_id", "1");
        path = mContext.getExternalFilesDir(userID).toString() + "/background.jpg";


        logoutOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                boolean auto = (boolean) SharedPreferencesUtils.getParam(this, "language_auto", true);
                String lang = (String) SharedPreferencesUtils.getParam(this, "language", "zh");
                String device_token = (String) SharedPreferencesUtils.getParam(this, "device_token", "");
                SharedPreferencesUtils.clear(getApplicationContext());
                SharedPreferencesUtils.setParam(getApplicationContext(), "first", false);
                SharedPreferencesUtils.setParam(getApplicationContext(), "language_auto", auto);
                SharedPreferencesUtils.setParam(getApplicationContext(), "language", lang);
                SharedPreferencesUtils.setParam(getApplicationContext(), "device_token", device_token);
                Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            } else {
                Toast.makeText(SettingActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @OnClick({R.id.iv_comfirm_back, R.id.tv_logout, R.id.tv_language_setting, R.id.tv_background_setting, R.id.tv_background_change_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_logout:
                show();
                break;
            case R.id.tv_language_setting:
                startActivity(new Intent(SettingActivity.this, LanguageActivity.class));
                break;
            case R.id.tv_background_setting:
                showType();
                break;
            case R.id.tv_background_change_pass:
                startActivity(new Intent(SettingActivity.this, ChangePassActivity.class));
                break;

        }
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
        builder.setMessage(getText(R.string.exit_hint));
        builder.setTitle(R.string.app_name);
        builder.setPositiveButton(getText(R.string.confirm), (dialog, which) -> {
            dialog.dismiss();
            HttpJsonMethod.getInstance().logout(
                    new ProgressSubscriber(logoutOnNext, SettingActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
        });
        builder.setNegativeButton(getText(R.string.cancel), (dialog, which) -> dialog.dismiss());
        builder.create().show();
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

    public void camera() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE_ASK_CALL_PHONE);
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
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE);
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
                    if (data != null) {
                        SharedPreferencesUtils.setParam(getApplicationContext(), userID, true);//存储用户是否更换照片
                        uri = data.getData();
//                        cropImageUri(uri, 103);
                        ContentResolver cr = this.getContentResolver();
                        try {
                            assert uri != null;
                            bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
//                            picFile = new File(path);
//                            if (!picFile.exists()) {
//                                picFile.getParentFile().mkdirs();
//                                picFile.createNewFile();
//                            }
                            FileOutputStream fos = new FileOutputStream(picFile);
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent1 = new Intent(this, Main2Activity.class);
                        startActivity(intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                    break;
                case 101:
                    // 从拍照返回
//                    if (data != null) {
                    bmp = BitmapFactory.decodeFile(path);
                    SharedPreferencesUtils.setParam(getApplicationContext(), userID, true);//存储用户是否更换照片
                    Intent intent = new Intent(this, Main2Activity.class);
                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    break;
                case 103:
                    if (data != null) {
                        SharedPreferencesUtils.setParam(getApplicationContext(), userID, true);//存储用户是否更换照片
//                        Uri uri = data.getData();
                        ContentResolver cr = this.getContentResolver();
                        try {
                            assert uri != null;
                            bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                            picFile = new File(path);
                            if (!picFile.exists()) {
                                picFile.getParentFile().mkdirs();
                                picFile.createNewFile();
                            }
                            FileOutputStream fos = new FileOutputStream(picFile);
                            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            fos.flush();
                            fos.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Intent intent1 = new Intent(this, Main2Activity.class);
                        startActivity(intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
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
            Uri imgUri = null;
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //如果在Android7.0以上,使用FileProvider获取Uri
//                intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
//                picFile = new File(path);
//                Uri contentUri = FileProvider.getUriForFile(SettingActivity.this, "com.wshoto.user.anyong", picFile);
                ContentValues contentValues = new ContentValues(1);
                contentValues.put(MediaStore.Images.Media.DATA, path);
                imgUri = getApplication().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            } else { //否则使用Uri.fromFile(file)方法获取Uri
                imgUri = Uri.fromFile(picFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
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
        if (isIntentAvailable(SettingActivity.this, intent)) {
            startActivityForResult(Intent.createChooser(intent, "选择图片"), 100);
        } else {
            Toast.makeText(SettingActivity.this, "请安装相关图片查看应用。", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 创建上传图片文件
     */
    private void createPicFile() {
        String sdStatus = Environment.getExternalStorageState();
        // 检测sd是否可用
        if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        picFile = new File(path);
    }

    /**
     * 拍照后图片裁减
     *
     * @param uri
     * @param requestCode
     */
    private void cropImageUri(Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1080);
        intent.putExtra("aspectY", 1920);
        intent.putExtra("outputX", 720);
        intent.putExtra("outputY", 1280);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES);
        return list.size() > 0;
    }
}
