package com.example.scannerproject;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TableActivity extends AppCompatActivity {
    Context mContext = this;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9, tv10, tv11, tv12, tv13;
    Button btn_showMatched;
    ArrayList<String> num_list;
    String[] num;
    ArrangeNum numSort;
    final private int REQUEST_CODE = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Intent intent = getIntent();
        num_list = intent.getStringArrayListExtra("ArrayList");
        num = num_list.toArray(new String[num_list.size()]);
        numSort = new ArrangeNum(num);

        btn_showMatched = findViewById(R.id.btn_showMatched);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4 = findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);
        tv7 = findViewById(R.id.tv7);
        tv8 = findViewById(R.id.tv8);
        tv9 = findViewById(R.id.tv9);
        tv10 = findViewById(R.id.tv10);
//        tv11 = findViewById(R.id.tv11);
//        tv12 = findViewById(R.id.tv12);
//        tv13 = findViewById(R.id.tv13);

        tv1.setText(numSort.toString(numSort.zero));
        tv2.setText(numSort.toString(numSort.one));
        tv3.setText(numSort.toString(numSort.two));
        tv4.setText(numSort.toString(numSort.three));
        tv5.setText(numSort.toString(numSort.four));
        tv6.setText(numSort.toString(numSort.five));
        tv7.setText(numSort.toString(numSort.six));
        tv8.setText(numSort.toString(numSort.seven));
        tv9.setText(numSort.toString(numSort.eight));
        tv10.setText(numSort.toString(numSort.nine));
//        tv11.setText("1230\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470");
//        tv12.setText("1230\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470");
//        tv13.setText("1230\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470\n2340\n3450\n3450\n3470\n1230\n2340\n3450\n3450\n3470");

        btn_showMatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matched = numSort.matched;
                if(matched == ""){
                    matched = "No Matched";
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                // set title
                alertDialogBuilder.setTitle("Number Matched");

                // set dialog message
                alertDialogBuilder
                        .setMessage(matched.substring(0,matched.length() - 1 ))
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close
                                // current activity
                                dialog.cancel();
                            }
                        });
//                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // if this button is clicked, just close
//                            // the dialog box and do nothing
//                            dialog.cancel();
//                        }
//                    });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case R.id.mybutton:
                saveToInternalStorage(getImage());

//                Intent intent = new Intent(this, Backgroud.class);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                getImage().compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] bytes = stream.toByteArray();
//                intent.putExtra("BMP", bytes);
//                startActivity(intent);


                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    public Bitmap getImage() {
        View u = ((Activity) mContext).findViewById(R.id.scrollView);

        HorizontalScrollView z = ((Activity) mContext).findViewById(R.id.scrollView);
        int totalHeight = z.getChildAt(0).getHeight();
        int totalWidth = z.getChildAt(0).getWidth();

        Bitmap b = getBitmapFromView(u, totalHeight, totalWidth);
        return b;
    }

    public Bitmap getBitmapFromView(View view, int totalHeight, int totalWidth) {

        Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth, totalHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return returnedBitmap;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveToInternalStorage(Bitmap bitmapImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmapImage.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Log.i("test", "Permission is granted");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            return;
        }
        //Why savedImageURL is null.
        String savedImageURL = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmapImage,
                getDate(),
                "Image of bird");
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission Granted
                    saveToInternalStorage(getImage());
                    Log.i("path", "OK");
                } else {
                    Toast.makeText(TableActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
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

