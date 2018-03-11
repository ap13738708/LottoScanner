package com.example.scannerproject;

import retrofit2.Call;
import retrofit2.http.Body;
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
}
