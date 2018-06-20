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
    rx.Observable<JSONObject> forget(@Field("mobile") String mobile, @Field("Verification") String Verification,
                                     @Field("password") String password, @Field("confirmpassword") String confirmpassword);

    @POST("/index.php?r=api/account/sms")
    @FormUrlEncoded
    rx.Observable<JSONObject> sendCode(@Field("mobile") String mobile,@Field(" play") String  play);

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

    @GET("/index.php?r=api/user/message-notice")
    rx.Observable<JSONObject> mesageList(@Query("session") String session);

    @POST("/index.php?r=api/signin/index")
    @FormUrlEncoded
    rx.Observable<JSONObject> locate(@Field("session") String session, @Field("place") String lal);

    @GET("/index.php?r=api/thank/thank-view")
    rx.Observable<JSONObject> thankList(@Query("session") String session);

    @GET("/index.php?r=api/thank/search")
    rx.Observable<JSONObject> thankObjectList(@Query("session") String session, @Query("keywords") String keywords);

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