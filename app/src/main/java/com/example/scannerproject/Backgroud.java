package com.example.scannerproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class Backgroud extends AppCompatActivity {

    ImageView background;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backgroud);


        background = findViewById(R.id.background);
        Intent intent = getIntent();
        byte[] bytes = intent.getByteArrayExtra("BMP");
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        background.setImageBitmap(bitmap);

    }
}
