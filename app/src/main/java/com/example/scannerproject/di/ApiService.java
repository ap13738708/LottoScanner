package com.example.scannerproject.di;

import com.example.scannerproject.PostNumber;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static OkHttpClient httpClient;
    private static final String BASE_URL = "http://35.240.137.130/";


    public ApiService() {

    }

    public static PostNumber httpManager() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request().newBuilder().addHeader("Authorization", Injection.getToken()).build();
                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json" )
                                .addHeader("Accept", "application/json")
                                .addHeader("Authorization", Injection.getToken()).build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        return retrofit.create(PostNumber.class);
    }

    public static PostNumber httpManagerWithoutToken() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request().newBuilder().addHeader("Authorization", Injection.getToken()).build();
                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json" )
                                .addHeader("Accept", "application/json").build();
                        return chain.proceed(request);
                    }
                }).build();

//        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        //Get client & call object for request
        return retrofit.create(PostNumber.class);
    }

    public static PostNumber httpManagerTest() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging.setLevel(HttpLoggingInterceptor.Level.BODY))
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
//                        Request request = chain.request().newBuilder().addHeader("Authorization", Injection.getToken()).build();
                        Request request = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json" )
                                .addHeader("Accept", "application/json").build();
//                                .addHeader("Authorization", Injection.getToken())
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://httpbin.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();


        return retrofit.create(PostNumber.class);
    }
}
