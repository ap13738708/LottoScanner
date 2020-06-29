package com.example.scannerproject.signup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scannerproject.R;
import com.example.scannerproject.di.ApiService;
import com.example.scannerproject.di.ResponseDao;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText name, surname, line, phone, password, password2;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.etName);
        surname = findViewById(R.id.etSurname);
        line = findViewById(R.id.etLine);
        phone = findViewById(R.id.etTel);
        password = findViewById(R.id.etPassword);
        password2 = findViewById(R.id.etPasswordConfirm);
        btnSignUp = findViewById(R.id.btnSignUp);

//        name.setText("Thanakorn");
//        surname.setText("Prayoonkittikul");
//        line.setText("peexyzpee");
//        phone.setText("0955158371");
//        password.setText("1234");
//        password2.setText("1234");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(password2.getText().toString())) {
                    signUp();
                } else {
                    Toast.makeText(getApplicationContext(), "รหัสไม่ตรงกัน", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void signUp() {
        SignUp signUp = new SignUp(name.getText().toString(), surname.getText().toString()
                , line.getText().toString(), phone.getText().toString(), password.getText().toString()
                , password2.getText().toString());
        Call<ResponseDao> call = ApiService.httpManagerWithoutToken().register(signUp);
        call.enqueue(new Callback<ResponseDao>() {
            @Override
            public void onResponse(Call<ResponseDao> call, Response<ResponseDao> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("onResponse", response.body().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "This is error: " + response.body(), Toast.LENGTH_SHORT).show();
                    Log.e("onResponseError", response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseDao> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_SHORT).show();
                Log.e("singUpError", t.toString());
            }
        });
    }
}
