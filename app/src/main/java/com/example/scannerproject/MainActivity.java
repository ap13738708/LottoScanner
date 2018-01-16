package com.example.scannerproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    Button btnSound;
    TextView textView;
    String all = "";
    MediaPlayer beapSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSound = findViewById(R.id.btnSound);
        textView = findViewById(R.id.textView);
        beapSound = MediaPlayer.create(this, R.raw.censor_beep_01);
        btnSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(all);
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
                all = data.getStringExtra("param");
            }
        }
    }
}
