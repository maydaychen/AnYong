package com.wshoto.user.anyong.http;

import android.content.Context;

import org.json.JSONException;

import rx.Subscriber;

/**
 * 作者：JTR on 2016/11/25 14:54
 * 邮箱：2091320109@qq.com
 */
public class ProgressErrorSubscriber<T> extends Subscriber<T> {

    private SubscriberOnNextAndErrorListener mSubscriberOnNextAndErrorListener;
    private Context context;

    public ProgressErrorSubscriber(SubscriberOnNextAndErrorListener mSubscriberOnNextAndErrorListener, Context context) {
        this.mSubscriberOnNextAndErrorListener = mSubscriberOnNextAndErrorListener;
        this.context = context;
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
        mSubscriberOnNextAndErrorListener.onError(e);
    }

    @Override
    public void onNext(T t) {
        try {
            mSubscriberOnNextAndErrorListener.onNext(t);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}