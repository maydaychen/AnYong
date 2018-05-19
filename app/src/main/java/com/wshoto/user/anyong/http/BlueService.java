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
    @GET("/app/index.php?m=wshoto_shop_v3&i=5&do=mobile&c=entry&r=app.insurance.login&comefrom=wxapp&username=&password=")
    rx.Observable<JSONObject> login(@Query("username") String username, @Query("password") String password);

    @GET("/app/index.php?m=wshoto_shop_v3&i=5&do=mobile&c=entry&r=app.insurance.refresh&comefrom=wxapp&id=")
    rx.Observable<JSONObject> userInfo(@Query("id") String id);

    @GET("/app/index.php?m=wshoto_shop_v3&i=5&do=mobile&c=entry&r=app.insurance.couponlist&comefrom=wxapp")
    rx.Observable<JSONObject> ticketList(@Query("id") String id);

    @GET("/app/index.php?m=wshoto_shop_v3&i=5&do=mobile&c=entry&r=app.insurance.bindcoupon&comefrom=wxapp")
    rx.Observable<JSONObject> applyTicket(@Query("uid") String uid, @Query("license_plate") String licensePlate,
                                          @Query("card_detail") String cardDetail);

    @POST("/app/index.php?m=wshoto_shop_v3&i=5&do=mobile&c=entry&r=app.insurance.insuranceOrders&comefrom=wxapp")
    @FormUrlEncoded
    rx.Observable<JSONObject> orderList(@Field("store_id") String storeId);

    @POST("/app/index.php?i=5&do=mobile&c=entry&r=app.insurance.updateInsuranceOrderStatus&comefrom=wxapp&m=wshoto_shop_v3")
    @FormUrlEncoded
    rx.Observable<JSONObject> changeOrder(@Field("store_id") String storeId, @Field("order_sn") String orderSn,
                                          @Field("insurances_order") String insurances_order, @Field("status") String status, @Field("seller") String seller);
}