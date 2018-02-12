package com.example.scannerproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by P on 1/21/2018.
 */

public interface PostNumber {

    @POST("insertlotto")
    Call<Void> sendNum(@Body Number num);
}
