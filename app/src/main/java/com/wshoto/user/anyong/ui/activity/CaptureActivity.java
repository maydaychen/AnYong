package com.wshoto.user.anyong.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.camera.CameraManager;
import com.wshoto.user.anyong.decoding.CaptureActivityHandler;
import com.wshoto.user.anyong.decoding.InactivityTimer;
import com.wshoto.user.anyong.ui.view.ViewfinderView;

import java.io.IOException;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.CALL_PHONE;


public class CaptureActivity extends Activity implements Callback {

    @BindView(R.id.iv_flash)
    ImageView mIvFlash;
    @BindView(R.id.tv_flash)
    TextView mTvFlash;
    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private boolean IS_OPEN_FLASH = false;
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cato);
        ButterKnife.bind(this);
        init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE) {
            if (permissions[0].equals(CALL_PHONE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //用户同意使用write
                init();
            } else {
                //用户不同意，自行处理即可
                Toast.makeText(this, "没有相机相关权限，请设置！", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        // 初始化 CameraManager
        CameraManager.init(getApplication());
        viewfinderView = findViewById(R.id.viewfinder_view);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        }
        mIvFlash.setOnClickListener(v -> {
            IS_OPEN_FLASH = !IS_OPEN_FLASH;
            if (IS_OPEN_FLASH) {
                CameraManager.get().turnOn();
//                mIvFlash.setImageResource(R.drawable.shou_dian_icon);
                mTvFlash.setText("关闭手电");
            } else {
                CameraManager.get().turnOff();
//                mIvFlash.setImageResource(R.drawable.shou_dian_tong);
                mTvFlash.setText("打开手电");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public void handleDecode(final Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        Intent intent = new Intent();
        intent.putExtra("code", obj.getText());
        setResult(RESULT_OK, intent);
        finish();
//		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//		if (barcode == null)
//		{
//			dialog.setIcon(null);
//		}
//		else
//		{
//
//			Drawable drawable = new BitmapDrawable(barcode);
//			dialog.setIcon(drawable);
//		}
//		dialog.setTitle("扫描结果");
//		dialog.setMessage(obj.getText());
//		dialog.setNegativeButton("ȷ��", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				Intent intent = new Intent();
//				intent.putExtra("code", obj.getText());
//				setResult(RESULT_OK, intent);
//				finish();
//			}
//		});
//		dialog.setPositiveButton("ȡ��", new DialogInterface.OnClickListener()
//		{
//			@Override
//			public void onClick(DialogInterface dialog, int which)
//			{
//				finish();
//			}
//		});
//		dialog.create().show();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}