package com.example.scannerproject.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.scannerproject.R;
import com.example.scannerproject.di.Injection;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        SharedPreferences p = getSharedPreferences("rial", 0);
        Injection.setToken(p.getString("token", null));
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frameLayoutSplashFragmentContainer, SplashFragment.getInstance()).commit();
        }
    }
}
