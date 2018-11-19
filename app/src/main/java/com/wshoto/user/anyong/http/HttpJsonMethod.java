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
    public static final String BASE_URL = "https://anyong.wshoto.com";
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


    public void login(Subscriber<JSONObject> subscriber, String username, String pass, String language, String device_token) {
        movieService.login(username, pass, language, device_token)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void forget(Subscriber<JSONObject> subscriber, String mobile, String verification,
                       String password, String confirmpassword, String language) {
        movieService.forget(mobile, verification, "fix", password, confirmpassword, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void userRisgist(Subscriber<JSONObject> subscriber, String number, String name, String mobile,
                            String verification, String password, String confirmpassword, String invitecode, String language) {
        movieService.userRisgist(number, name, mobile, verification, password, confirmpassword, invitecode, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void sendCode(Subscriber<JSONObject> subscriber, String mobile, String play, String language) {
        movieService.sendCode(mobile, play, language)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void checknum(Subscriber<JSONObject> subscriber, String num, String name, String language) {
        movieService.checknum(num, name, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void userInfo(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.userInfo(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void creditDetail(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.creditDetail(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void mesageList(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.mesageList(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void locate(Subscriber<JSONObject> subscriber, String session, String lal, String language, String latitude, String longitude) {
        movieService.locate(session, lal, language, latitude, longitude)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void healthTask(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.healthTask(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void healthCommit(Subscriber<JSONObject> subscriber, String session, String health_id, String language) {
        movieService.healthCommit(session, health_id, language)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void thankList(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.thankList(session, language)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void thankObjectList(Subscriber<JSONObject> subscriber, String storeId, String keywords, String language) {
        movieService.thankObjectList(storeId, keywords, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void previewThank(Subscriber<JSONObject> subscriber, String receive, String themeid,
                             String picture, String session, String content, String language) {
        movieService.previewThank(receive, themeid, picture, session, content, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void sendThank(Subscriber<JSONObject> subscriber, String id, String session, String language) {
        movieService.sendThank(id, session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void thankTheme(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.thankTheme(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void thankContent(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.thankContent(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void calendar(Subscriber<JSONObject> subscriber, String session, String province, String city, String language) {
        movieService.calendar(session, city, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void myCalendar(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.myCalendar(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void timeCalendar(Subscriber<JSONObject> subscriber, String session, String time, String province, String city, String language) {
        movieService.timeCalendar(session, time, city, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void activityInfo(Subscriber<JSONObject> subscriber, String session, String id, String language) {
        movieService.activityInfo(session, id, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void joinActivity(Subscriber<JSONObject> subscriber, String session, String id, String language) {
        movieService.joinActivity(session, id, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void uploadImg(Subscriber<JSONObject> subscriber, String file, String language) {
        movieService.uploadImg(file, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void myRadiusList(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.myRadiusList(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void friendInfo(Subscriber<JSONObject> subscriber, String session, String id, String language) {
        movieService.friendInfo(session, id, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void newfriendList(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.newfriendList(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void newFriendOperate(Subscriber<JSONObject> subscriber, String session, String id,
                                 String status, String language) {
        movieService.newFriendOperate(session, id, status, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void addFriend(Subscriber<JSONObject> subscriber, String session, String id, String language) {
        movieService.addFriend(session, id, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void logout(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.logout(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getAva(Subscriber<JSONObject> subscriber, String session, String ava, String language) {
        movieService.getAva(session, ava, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void givePoint(Subscriber<JSONObject> subscriber, String session, String id, String num, String language) {
        movieService.givePoint(session, id, num, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void scan(Subscriber<JSONObject> subscriber, String session, String data, String language) {
        movieService.scan(session, data, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void footstep(Subscriber<JSONObject> subscriber, String session, String language) {
        movieService.footstep(session, language)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void newer(Subscriber<JSONObject> subscriber, String job_no, String language) {
        movieService.newer(job_no, language)
//                .map(new HttpResultFunc<>())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
