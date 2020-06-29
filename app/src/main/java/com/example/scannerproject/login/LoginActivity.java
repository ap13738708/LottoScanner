package com.example.scannerproject.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scannerproject.MainActivity;
import com.example.scannerproject.R;
import com.example.scannerproject.di.ApiService;
import com.example.scannerproject.di.Injection;
import com.example.scannerproject.signup.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username;
    EditText password;
    Button btnLogin, btnRegister;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        Call<AuthDao> call = ApiService.httpManagerWithoutToken().login(username.getText().toString()
                , password.getText().toString());
        call.enqueue(new Callback<AuthDao>() {
            @Override
            public void onResponse(Call<AuthDao> call, Response<AuthDao> response) {
                if (response.isSuccessful()) {
                    Injection.setToken( response.body().getTokenType() + " " + response.body().getToken());
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(), "Successful login", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "รหัสไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AuthDao> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "เกิดข้อผิดพลาด", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
