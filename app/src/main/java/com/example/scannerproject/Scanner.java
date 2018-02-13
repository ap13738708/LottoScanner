package com.example.scannerproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    ZXingScannerView zXingScannerView;
    MediaPlayer beapSound;
//    ArrayList<String> num_list;
    String all = "";
    String lottogroup;
    final private int REQUEST_CODE = 123;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scanner);

        Intent intent = getIntent();
        lottogroup  = intent.getStringExtra("lottogroup");
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
//        zXingScannerView.setAspectTolerance(0.5f);
        beapSound = MediaPlayer.create(this,R.raw.censor_beep_01);
//        num_list = new ArrayList< >();
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            Log.i("test", "Permission is granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
            return;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        beapSound.start();
        Toast.makeText(getApplicationContext(),result.getText().substring(9,13),Toast.LENGTH_SHORT).show();
//        Log.i("result", result.getText());
        all += result.getText().substring(9,13) + ",";
//        num_list.add(result.getText().substring(9,13));
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        zXingScannerView.resumeCameraPreview(Scanner.this);
                        Log.i("tag", "This'll run 1 sec later");
                    }
                }, 200);
    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu_scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent= new Intent();
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case android.R.id.home:
                setResult(RESULT_OK);
                finish();
                return true;
            case R.id.btn_save :
//                intent.putStringArrayListExtra("num_list", num_list);
                if (all.length() != 0) {

                    String[] num = all.split(",");
                    ArrangeNum obj = new ArrangeNum(num,lottogroup);
                    intent.putExtra("allNum", all.substring(0, all.length() - 1));
                    intent.putExtra("Obj", obj);
                    setResult(RESULT_OK, intent);
                    finish();
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "No data to save", Toast.LENGTH_SHORT).show();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted
                    Intent intent = getIntent();
                    zXingScannerView = new ZXingScannerView(getApplicationContext());
                    setContentView(zXingScannerView);
//                  zXingScannerView.setAspectTolerance(0.5f);
                    beapSound = MediaPlayer.create(this,R.raw.censor_beep_01);
//                    num_list = new ArrayList< >();
                    zXingScannerView.setResultHandler(this);
                    zXingScannerView.startCamera();
                    Log.i("path", "OK");
                } else {
                    Toast.makeText(Scanner.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }



}
