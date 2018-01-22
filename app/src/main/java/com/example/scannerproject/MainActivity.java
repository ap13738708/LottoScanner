package com.example.scannerproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    EditText lottogroup;
    Button send_data;
    TextView textView;
    ArrayList<String> num_list;
    MediaPlayer beapSound;
    String[] num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lottogroup = findViewById(R.id.lottoGroup);
        send_data = findViewById(R.id.send_data);
        textView = findViewById(R.id.textView);
        beapSound = MediaPlayer.create(this, R.raw.censor_beep_01);
        send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String all="";
                for (String num : num_list){
                    all += num + "\n";
                }
                textView.setText(all);
                num = num_list.toArray(new String[num_list.size()]);
                Number number = new Number(num,lottogroup.getText().toString());
                sendNetworkRequest(number);
            }
        });
    }

    public void scan(View view) {
        Intent intent = new Intent(MainActivity.this.getApplicationContext(), Scanner.class);
        startActivityForResult(intent, 2404);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2404 && data != null) {
                num_list = data.getStringArrayListExtra("num_list");
            }
        }
    }

    private void sendNetworkRequest(Number num) {
        //Create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.1.49:3000/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        //Get client & call object for request
        PostNumber client = retrofit.create(PostNumber.class);
        Call<Number> call = client.sendNum(num);

        call.enqueue(new Callback<Number>() {
            @Override
            public void onResponse(Call<Number> call, Response<Number> response) {
                Toast.makeText(MainActivity.this,"Send successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Number> call, Throwable t) {
                Toast.makeText(MainActivity.this,"something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
