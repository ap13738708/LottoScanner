package com.example.scannerproject.di;

import com.example.scannerproject.app.SplashContract;
import com.example.scannerproject.app.SplashPresenter;

public class Injection {

    private static String token;

    public static void setToken(String token) {
        Injection.token = token;
    }

    public static String getToken() {
        return token;
    }

    public static SplashContract.Presenter getSplashPresenter(SplashContract.View view) {
        return new SplashPresenter(view);
    }
}
