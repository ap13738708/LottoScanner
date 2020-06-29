package com.example.scannerproject;

import com.example.scannerproject.di.ResponseDao;
import com.example.scannerproject.di.SummaryDao;
import com.example.scannerproject.login.AuthDao;
import com.example.scannerproject.matched.DetailDao;
import com.example.scannerproject.signup.SignUp;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

/**
 * Created by P on 1/21/2018.
 */

public interface PostNumber {

    @POST("insertlotto")
    Call<Void> sendNum(@Body Number num);

    @PUT("insertlotto")
    Call<Void> updateNum(@Body Number num);



    @POST("api/lotto")
    Call<ResponseDao> sendData(@Body DataList data);

    @POST("api/auth/signup")
    Call<ResponseDao> register(@Body SignUp signUp);

    @FormUrlEncoded
    @POST("api/auth/login")
    Call<AuthDao> login(@Field("tel") String tel, @Field("password") String password);

    // Test

    @POST("post")
    Call<ResponseBody> getRequestData(@Body DataList data);

    /////////////////////////////

    @GET("api/summary")
    Call<SummaryDao> getSummaryDaoInfo();

    @GET("api/auth/logout")
    Call<ResponseDao> logout();

    @GET("api/match")
    Call<HashMap<String, ArrayList<DetailDao>>> getMatchedNum();


    @DELETE("api/lotto/{lotto_id}")
    Call<ResponseDao> deleteNum(String id);

}
