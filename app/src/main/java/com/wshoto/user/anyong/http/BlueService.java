package com.wshoto.user.anyong.http;

import org.json.JSONObject;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 作者：JTR on 2016/11/24 14:15
 * 邮箱：2091320109@qq.com
 */
public interface BlueService {
    @POST("/index.php?r=api/account/login")
    @FormUrlEncoded
    rx.Observable<JSONObject> login(@Field("email") String email, @Field("password") String password);

    @POST("/index.php?r=api/account/forget")
    @FormUrlEncoded
    rx.Observable<JSONObject> forget(@Field("mobile") String mobile, @Field("verification") String Verification, @Field("play") String play,
                                     @Field("password") String password, @Field("confirmpassword") String confirmpassword);

    @POST("/index.php?r=api/account/sms")
    @FormUrlEncoded
    rx.Observable<JSONObject> sendCode(@Field("mobile") String mobile, @Field(" play") String play);

    @GET("/index.php?r=api/account/job-num")
    rx.Observable<JSONObject> checknum(@Query("job_no") String job_no, @Query("first_name") String first_nam);

    @POST("/index.php?r=api/account/register")
    @FormUrlEncoded
    rx.Observable<JSONObject> userRisgist(@Field("job_no") String number, @Field("englishname") String name,
                                          @Field("mobile") String mobile, @Field("verification") String verification,
                                          @Field("password") String password, @Field("confirmpassword") String confirmpassword,
                                          @Field("invitecode") String invitecode);

    @POST("/index.php?r=api/user/user-info")
    @FormUrlEncoded
    rx.Observable<JSONObject> userInfo(@Field("session") String session);

    @POST("/index.php?r=api/user/integral")
    @FormUrlEncoded
    rx.Observable<JSONObject> creditDetail(@Field("session") String session);

    @POST("/index.php?r=api/user/message")
    @FormUrlEncoded
    rx.Observable<JSONObject> mesageList(@Field("session") String session);

    @POST("/index.php?r=api/signin/index")
    @FormUrlEncoded
    rx.Observable<JSONObject> locate(@Field("session") String session, @Field("place") String lal);

    @POST("/index.php?r=api/healthy-live/task-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> healthTask(@Field("session") String session);

    @POST("/index.php?r=api/healthy-live/finish-task")
    @FormUrlEncoded
    rx.Observable<JSONObject> healthCommit(@Field("session") String session, @Field("health_id") String health_id);

    @POST("/index.php?r=api/thank/thank-view")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankList(@Field("session") String session);

    @POST("/index.php?r=api/thank/search")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankObjectList(@Field("session") String session, @Field("keywords") String keywords);

    @POST("/index.php?r=api/thank/send-thank")
    @FormUrlEncoded
    rx.Observable<JSONObject> sendThank(@Field("receive") String receive, @Field("themeid") String themeid,
                                        @Field("picture") String picture, @Field("session") String session,
                                        @Field("content") String content);

    @POST("/index.php?r=api/template/template-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankTheme(@Field("session") String session);

    @POST("/index.php?r=api/activity/activity-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> calendar(@Field("session") String session);

    @GET("/index.php?r=api/user/radius")
    rx.Observable<JSONObject> myRadiusList(@Query("session") String session);

    @GET("/index.php?r=api/contact/friend-info")
    rx.Observable<JSONObject> friendInfo(@Query("session") String session, @Query("id") String id);

    @GET("/index.php?r=api/contact/new-friend")
    rx.Observable<JSONObject> friendList(@Query("session") String session);

    @GET("/index.php?r=api/contact/friend-info")
    rx.Observable<JSONObject> newFriendOperate(@Query("session") String session, @Query("id") String id, @Query("play") String play);

    @GET("/index.php?r=api/account/logout")
    rx.Observable<JSONObject> logout(@Query("session") String session);

}