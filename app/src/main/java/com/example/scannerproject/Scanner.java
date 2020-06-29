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
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

public class Scanner extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    public static final List<BarcodeFormat> ALL_FORMATS = new ArrayList();
    ZXingScannerView zXingScannerView;
    MediaPlayer beapSound;
//    ArrayList<String> num_list;
    String all = "";
    final private int REQUEST_CODE = 123;
    Intent intent;
    final String BASE_URL = "http://119.59.123.156:2222/";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_scanner);
        ALL_FORMATS.add(BarcodeFormat.DATA_MATRIX);
        intent = getIntent();
        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
//        zXingScannerView.setAspectTolerance(0.5f);
        beapSound = MediaPlayer.create(this,R.raw.censor_beep_01);
        zXingScannerView.setFormats(ALL_FORMATS);
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
                }, 500);
    }
    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu_scan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int requestCode = intent.getIntExtra("requestCode", -1);
        String lottogroup = intent.getStringExtra("lottogroup");
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

//            case android.R.id.home:
//                setResult(RESULT_OK);
//                finish();
//                return true;
            case R.id.btn_save :

                if (all.length() != 0) {
                    switch (requestCode){

                        case 2404 : {
                            String name = intent.getStringExtra("name");
                            String phone = intent.getStringExtra("phone");
//                            Number number = new Number(name, phone, all.substring(0, all.length() - 1), lottogroup);
//                            sendNetworkRequest(number);

                            String[] num = all.split(",");
                            ArrangeNum obj = new ArrangeNum(num, lottogroup, getDate(),name,phone);
                            intent.putExtra("allNum", all.substring(0, all.length() - 1));
                            intent.putExtra("Obj", obj);
                            setResult(RESULT_OK, intent);
                            finish();
                            return true;
                        }
                        case 2405 : {
//                            String name = intent.getStringExtra("name");
//                            String phone = intent.getStringExtra("phone");
                            ArrangeNum numSort = (ArrangeNum) intent.getSerializableExtra("Obj");

                            String[] num = all.split(",");
//                            ArrangeNum obj = new ArrangeNum(num, lottogroup, getDate(),name,phone);

                            numSort.add(num);
                            numSort.setTime(getDate());
                            Intent intent1 = new Intent(Scanner.this.getApplicationContext(), MainActivity.class);
//                            intent1.putExtra("allNum", all.substring(0, all.length() - 1));
                            intent1.putExtra("requestCode", 2405);
                            intent1.putExtra("Obj", numSort);
                            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | FLAG_ACTIVITY_CLEAR_TASK );
                            startActivity(intent1);
//                            setResult(RESULT_OK, intent);
                            finish();
                            return true;
                        }
                    }

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


    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String result = dateFormat.format(date);
        return result;
    }
}
