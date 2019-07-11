package com.wshoto.user.anyong.http;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.ui.activity.LoginActivity;

import org.json.JSONException;

import rx.Subscriber;

/**
 * 作者：JTR on 2016/11/25 14:54
 * 邮箱：2091320109@qq.com
 */
public class ProgressSubscriber<JSONObject> extends Subscriber<org.json.JSONObject> {

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private Context mContext;

    public ProgressSubscriber(SubscriberOnNextListener mSubscriberOnNextListener, Context context) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.mContext = context;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onCompleted() {
//        Toast.makeText(context, "Get Top Movie Completed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Throwable e) {
//        Toast.makeText(context, "err" + e.getMessage(), Toast.LENGTH_SHORT).show();
        Log.e("======================>", e.getMessage());
    }

    @Override
    public void onNext(org.json.JSONObject t) {
        try {
            if (t.getJSONObject("message").getString("status").equals("session错误")) {
                show();
            } else if (t.getJSONObject("message").getString("status").equals("session error!")) {
                show();
            }
            mSubscriberOnNextListener.onNext(t);
        } catch (JSONException e) {
            e.printStackTrace();
            try {
                mSubscriberOnNextListener.onNext(t);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(mContext.getText(R.string.logout_error));
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);

        builder.setPositiveButton(mContext.getText(R.string.confirm), (dialog, which) -> {
            dialog.dismiss();
            boolean auto = (boolean) SharedPreferencesUtils.getParam(mContext, "language_auto", true);
            String lang = (String) SharedPreferencesUtils.getParam(mContext, "language", "zh");
            String device_token = (String) SharedPreferencesUtils.getParam(mContext, "device_token", "");
            SharedPreferencesUtils.clear(mContext);
            SharedPreferencesUtils.setParam(mContext, "first", false);
            SharedPreferencesUtils.setParam(mContext, "language_auto", auto);
            SharedPreferencesUtils.setParam(mContext, "language", lang);
            SharedPreferencesUtils.setParam(mContext, "device_token", device_token);
            Intent intent = new Intent(mContext, LoginActivity.class);
            mContext.startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
        });

//        builder.setNegativeButton("取消", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}