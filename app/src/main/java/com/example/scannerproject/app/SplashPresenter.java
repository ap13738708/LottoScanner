package com.example.scannerproject.app;


import com.example.scannerproject.di.Injection;

public class SplashPresenter implements SplashContract.Presenter {

    SplashContract.View view;

    public SplashPresenter(SplashContract.View view) {
        this.view = view;
    }

    @Override
    public void checkStatus() {
        if (Injection.getToken() == null ) view.signIn();
        else view.main();

    }
}
