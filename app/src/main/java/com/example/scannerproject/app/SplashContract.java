package com.example.scannerproject.app;

public interface SplashContract {

    interface View {
        void signIn();
        void main();
    }

    interface Presenter {
        void checkStatus();
    }
}
