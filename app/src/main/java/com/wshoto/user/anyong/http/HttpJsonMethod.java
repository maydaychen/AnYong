package com.wshoto.user.anyong.http;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by user on 2017/7/13.
 */

public class HttpJsonMethod {
    public static final String BASE_URL = "https://ruixi.wshoto.com";
    private static final int DEFAULT_TIMEOUT = 5;

    private BlueService movieService;

    private HttpJsonMethod() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS).build();


        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
//                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(JsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        movieService = retrofit.create(BlueService.class);
    }

    private static class SingletonHolder {
        private static final HttpJsonMethod INSTANCE = new HttpJsonMethod();
    }

    //获取单例
    public static HttpJsonMethod getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult) {
            return httpResult.getOthers();
        }
    }


    public void login(Subscriber<JSONObject> subscriber, String username, String pass) {
        movieService.login(username, pass)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void userInfo(Subscriber<JSONObject> subscriber, String userid) {
        movieService.userInfo(userid)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void ticketList(Subscriber<JSONObject> subscriber) {
        movieService.ticketList("1")
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void applyTicket(Subscriber<JSONObject> subscriber, String uid, String license_plate, String card_detail) {
        movieService.applyTicket(uid, license_plate, card_detail)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void orderList(Subscriber<JSONObject> subscriber, String storeId) {
        movieService.orderList(storeId)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void changeOrder(Subscriber<JSONObject> subscriber, String storeId, String orderSn, String insurances_order, String seller) {
        movieService.changeOrder(storeId, orderSn, insurances_order, "1", seller)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
