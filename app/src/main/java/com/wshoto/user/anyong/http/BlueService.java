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
    rx.Observable<JSONObject> login(@Field("email") String email, @Field("password") String password,
                                    @Field("language") String language, @Field("device_token") String device_token, @Field("invitecode") String invitecode);

    @POST("/index.php?r=api/account/forget")
    @FormUrlEncoded
    rx.Observable<JSONObject> forget(@Field("mobile") String mobile, @Field("Verification") String Verification, @Field("play") String play,
                                     @Field("password") String password, @Field("confirmpassword") String confirmpassword, @Field("language") String language);

    @POST("/index.php?r=api/account/sms")
    @FormUrlEncoded
    rx.Observable<JSONObject> sendCode(@Field("mobile") String mobile, @Field(" play") String play, @Field("language") String language, @Field("areacode") String areacode);

    @GET("/index.php?r=api/account/job-num")
    rx.Observable<JSONObject> checknum(@Query("job_no") String job_no, @Query("first_name") String first_nam,
                                       @Query("language") String language);

    @POST("/index.php?r=api/account/register-new")
    @FormUrlEncoded
    rx.Observable<JSONObject> userRisgist(@Field("email") String number, @Field("gpn") String name,
                                          @Field("mobile") String mobile, @Field("code") String verification,
                                          @Field("language") String language, @Field("password") String password);

    @POST("/index.php?r=api/user/user-info")
    @FormUrlEncoded
    rx.Observable<JSONObject> userInfo(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/user/integral")
    @FormUrlEncoded
    rx.Observable<JSONObject> creditDetail(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/user/message")
    @FormUrlEncoded
    rx.Observable<JSONObject> mesageList(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/user/message-read")
    @FormUrlEncoded
    rx.Observable<JSONObject> mesageRead(@Field("session") String session, @Field("broadid") String id);

    @POST("/index.php?r=api/user/broadcast-del")
    @FormUrlEncoded
    rx.Observable<JSONObject> mesageDel(@Field("id") String id);

    @POST("/index.php?r=api/signin/index")
    @FormUrlEncoded
    rx.Observable<JSONObject> locate(@Field("session") String session, @Field("place") String lal,
                                     @Field("language") String language, @Field("latitude") String latitude,
                                     @Field("longitude") String longitude);

    @POST("/index.php?r=api/healthy-live/task-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> healthTask(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/healthy-live/finish-task")
    @FormUrlEncoded
    rx.Observable<JSONObject> healthCommit(@Field("session") String session, @Field("health_id") String health_id, @Field("language") String language);

    @POST("/index.php?r=api/thank/thank-view")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankList(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/thank/search")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankObjectList(@Field("session") String session, @Field("keywords") String keywords, @Field("language") String language);

    @POST("/index.php?r=api/thank/thank-send-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankSentList(@Field("session") String session);

    @POST("/index.php?r=api/thank/thank-receive-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankReceiveList(@Field("session") String session);

    @POST("/index.php?r=api/thank/send-thanks")
    @FormUrlEncoded
    rx.Observable<JSONObject> previewThank(@Field("receivers") String receive, @Field("template_id") String themeid,
                                           @Field("thumb") String picture, @Field("session") String session,
                                           @Field("content") String content, @Field("language") String language);

    @POST("/index.php?r=api/thank/submit-send")
    @FormUrlEncoded
    rx.Observable<JSONObject> sendThank(@Field("id") String id, @Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/template/template-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankTheme(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/thank/template-content")
    @FormUrlEncoded
    rx.Observable<JSONObject> thankContent(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/activity/all-time")
    @FormUrlEncoded
    rx.Observable<JSONObject> calendar(@Field("session") String session, @Field("city") String city, @Field("language") String language);

    @POST("/index.php?r=api/activity/activity-join")
    @FormUrlEncoded
    rx.Observable<JSONObject> myCalendar(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/activity/activity-time")
    @FormUrlEncoded
    rx.Observable<JSONObject> timeCalendar(@Field("session") String session, @Field("time") String time, @Field("city") String city, @Field("language") String language);

    @POST("/index.php?r=api/activity/activity-info")
    @FormUrlEncoded
    rx.Observable<JSONObject> activityInfo(@Field("session") String session, @Field("id") String id, @Field("language") String language);

    @POST("/index.php?r=api/activity/join-activity")
    @FormUrlEncoded
    rx.Observable<JSONObject> joinActivity(@Field("session") String session, @Field("id") String id, @Field("language") String language);

    @POST("/index.php?r=api/upload/img-upload")
    @FormUrlEncoded
    rx.Observable<JSONObject> uploadImg(@Field("file") String file, @Field("language") String language);

    @POST("/index.php?r=api/contact/friend-list")
    @FormUrlEncoded
    rx.Observable<JSONObject> myRadiusList(@Field("session") String session, @Field("language") String language);

//    @POST("/index.php?r=api/user/radius")
//    @FormUrlEncoded
//    rx.Observable<JSONObject> myRadiusList(@Field("session") String session);

    @POST("/index.php?r=api/contact/friend-info")
    @FormUrlEncoded
    rx.Observable<JSONObject> friendInfo(@Field("session") String session, @Field("id") String id, @Field("language") String language);

    @POST("/index.php?r=api/contact/new-friend")
    @FormUrlEncoded
    rx.Observable<JSONObject> newfriendList(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/contact/submit-add")
    @FormUrlEncoded
    rx.Observable<JSONObject> newFriendOperate(@Field("session") String session, @Field("id") String id,
                                               @Field("status") String status, @Field("language") String language);

    @POST("/index.php?r=api/contact/add-friends")
    @FormUrlEncoded
    rx.Observable<JSONObject> addFriend(@Field("session") String session, @Field("ids") String id,
                                        @Field("language") String language);

    @GET("/index.php?r=api/account/logout")
    rx.Observable<JSONObject> logout(@Query("session") String session, @Query("language") String language);

    @POST("/index.php?r=api/user/head-img")
    @FormUrlEncoded
    rx.Observable<JSONObject> getAva(@Field("session") String session, @Field("img") String img, @Field("language") String language);

    @POST("/index.php?r=api/contact/give-integral")
    @FormUrlEncoded
    rx.Observable<JSONObject> givePoint(@Field("session") String session, @Field("id") String id,
                                        @Field("number") String number, @Field("language") String language);

    @POST("/index.php?r=api/qr-code/scan")
    @FormUrlEncoded
    rx.Observable<JSONObject> scan(@Field("session") String session, @Field("data") String data, @Field("language") String language);

    @POST("/index.php?r=api/healthy-live/put-num")
    @FormUrlEncoded
    rx.Observable<JSONObject> footstep(@Field("session") String session, @Field("language") String language);

    @POST("/index.php?r=api/integral/newer-task")
    @FormUrlEncoded
    rx.Observable<JSONObject> newer(@Field("job_no") String job_no, @Field("language") String language);

    @POST("/index.php?r=api/setting/setting-info")
    @FormUrlEncoded
    rx.Observable<JSONObject> update(@Field("job_no") String job_no);

}