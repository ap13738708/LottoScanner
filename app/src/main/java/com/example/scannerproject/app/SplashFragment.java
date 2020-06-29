package com.example.scannerproject.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.scannerproject.MainActivity;
import com.example.scannerproject.di.Injection;
import com.example.scannerproject.login.LoginActivity;

public class SplashFragment extends Fragment implements SplashContract.View {

    private static SplashFragment instance;

    public static SplashFragment getInstance() {
        if (instance == null) instance = new SplashFragment();
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injection.getSplashPresenter(this).checkStatus();
    }

    @Override
    public void signIn() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void main() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
