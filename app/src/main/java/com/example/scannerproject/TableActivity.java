package com.example.scannerproject;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class TableActivity extends AppCompatActivity {
    Context mContext = this;
    Button btn_showMatched;
    TextView name;
    String[] num = null;
    ArrangeNum numSort;
    ArrangeNum addArrangeNum;
    final private int REQUEST_CODE = 123;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);
        name = findViewById(R.id.name);
        Intent intent = getIntent();
        addArrangeNum = (ArrangeNum) intent.getSerializableExtra("Obj");
        setTitle(addArrangeNum.lottogroup);
        if(num == null){
            num = addArrangeNum.getAllArray();
            numSort = new ArrangeNum(num, addArrangeNum.lottogroup, addArrangeNum.getTime(),addArrangeNum.name,addArrangeNum.phone);
        }
        btn_showMatched = findViewById(R.id.btn_showMatched);


        setView();

        btn_showMatched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matched = numSort.matched;
                if(matched == ""){
                    matched = "No Matched";
                } else{
                    matched = matched.substring(0,matched.length() - 1 );
                }
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                // set title
                alertDialogBuilder.setTitle("Number Matched");

                // set dialog message
                alertDialogBuilder
                        .setMessage(matched)
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
        int requestCode;
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button

            case R.id.btn_save: {
                saveToInternalStorage(getImage());
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.btn_add : {
                requestCode  = 2405;
                Intent intent = new Intent(TableActivity.this.getApplicationContext(), Scanner.class);
                intent.putExtra("requestCode", requestCode);
                intent.putExtra("Obj", numSort);
                startActivity(intent);
//                startActivityForResult(intent, requestCode);
//                Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            }
            case R.id.btn_minus : {
                requestCode  = 2406;
                Intent intent = new Intent(TableActivity.this.getApplicationContext(), RemoveNumActivity.class);
                intent.putExtra("requestCode", requestCode);
                intent.putExtra("Obj", numSort);
                startActivity(intent);
                finish();
                return true;
            }

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
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == 2405 && data != null) {
//                fromScan = (ArrangeNum) data.getSerializableExtra("Obj");
//                numSort.add(fromScan.getAllArray());
//                setView();
//                String test = data.getStringExtra("test");
//                Toast.makeText(getApplicationContext(),test,Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//

    public void setView() {
        TableRow tableRow =  findViewById(R.id.row1);
        name.setText(numSort.name + "   " + numSort.phone + "   " + getDate2() + "  "+numSort.lottogroup);
        name.setTypeface(null, Typeface.BOLD);
        name.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
//        TableLayout table = findViewById(R.id.table);
//        TextView name = new TextView(this);
//        name.setText(numSort.name + "  " + numSort.phone );
//
//        name.setPadding(60,0,0,0);
//        name.setLayoutParams(new TableRow.LayoutParams(
//                TableRow.LayoutParams.FILL_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        table.addView(name);

        String[] num;
        for(int i=0;i<10;i++) {
            int count = 0;
            num = numSort.arrayOfArrayList[i].toArray(new String[numSort.arrayOfArrayList[i].size()]);
            Log.i("check", String.valueOf(num.length));
            Log.i("check", Arrays.toString(num));
            String numText = "";
            while(num.length > count){
                TextView text = new TextView(this);
                text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                for (int j = 0 ; j < 40; j++){
                    if(num.length == count) break;
                    Log.i("check",num[count] + " " + count);
                    numText += num[count] + "\n";
                    count++;

                }
                text.setText(numText);
                numText = "";
                text.setPadding(10,0,0,5);
                tableRow.addView(text);
            }
        }

//        TextView valueTV = new TextView(this);
//        valueTV.setText("Hello");
//        valueTV.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//        valueTV.setLayoutParams(new TableRow.LayoutParams(
//                TableRow.LayoutParams.FILL_PARENT,
//                TableRow.LayoutParams.WRAP_CONTENT));
//        tableRow.addView(valueTV);
    }

    public String getDate2() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String result = dateFormat.format(date);
        return result;
    }
}

