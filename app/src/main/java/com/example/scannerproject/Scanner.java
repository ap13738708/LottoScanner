package com.example.scannerproject;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;
    MediaPlayer beapSound;
    ArrayList<String> num_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scanner);
        Intent intent = getIntent();
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        // zXingScannerView.setAspectTolerance(0.5f);
        beapSound = MediaPlayer.create(this,R.raw.censor_beep_01);
        num_list = new ArrayList< >();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

    }



    @Override
    protected void onPause() {
        super.onPause();
        //zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        beapSound.start();
        Toast.makeText(getApplicationContext(),result.getText().substring(9,13),Toast.LENGTH_SHORT).show();

        num_list.add(result.getText().substring(9,13));
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        zXingScannerView.resumeCameraPreview(Scanner.this);
                        Log.i("tag", "This'll run 1 sec later");
                    }
                }, 200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent= new Intent();
                intent.putStringArrayListExtra("num_list", num_list);
                setResult(RESULT_OK, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
