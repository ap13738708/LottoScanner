package com.example.scannerproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    Context mContext = this;
    EditText lottogroup, name, phone;
    Button send_data, test;
    ArrayList<String> num_list;
    MediaPlayer beapSound;
    String all;

    ArrangeNum fromScan = null;

    final String BASE_URL = "http://119.59.123.156:2222/";
//    final String BASE_URL = "http://192.168.43.189:2222/";

    private List<ArrangeNum> tablesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArrangeNumAdapter mAdapter;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        test = findViewById(R.id.test);

        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        lottogroup = findViewById(R.id.lottoGroup);

        send_data = findViewById(R.id.send_data);
        beapSound = MediaPlayer.create(this, R.raw.censor_beep_01);
        initial();
        setRecycleView();

        intent = getIntent();
        int requestCode = intent.getIntExtra("requestCode", -1);
        if(requestCode == 2405) {
            addNewDeleteOld();
        }

//        ArrangeNum one = new ArrangeNum(pseudoData().split(","),"lotto1");
//        tablesList.add(one);
//        String[] testtest = {"1234","2345","4567"};
//        one = new ArrangeNum(pseudoData().split(","),"lotto2");
//        tablesList.add(one);
//        one = new ArrangeNum(testtest,"lotto3");
//        tablesList.add(one);


        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all = pseudoData();
                Number number = new Number(name.getText().toString(), phone.getText().toString(), all, lottogroup.getText().toString());
                Toast.makeText(mContext, "Done", Toast.LENGTH_SHORT).show();
                sendNetworkRequest(number);

            }
        });

        send_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all = pseudoData();
                if (all == null) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

                    alertDialogBuilder.setTitle("Error");

                    alertDialogBuilder
                            .setMessage("No Data")
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {

                    String[] array = all.split(",");
                    num_list = new ArrayList<>(Arrays.asList(array));
                    Number number = new Number(name.getText().toString(), phone.getText().toString(), all, lottogroup.getText().toString());
//                    sendNetworkRequest(number);
                    ArrangeNum fake = new ArrangeNum(array, lottogroup.getText().toString(), "fake time");
                    Intent intent = new Intent(MainActivity.this, TableActivity.class);
                    //intent.putStringArrayListExtra("ArrayList", num_list);
//                    intent.putStringArrayListExtra("ArrayList", num_list);
                    intent.putExtra("Obj", fake);
                    startActivity(intent);
                }
            }
        });
    }
    public void initial(){
        SharedPreferences p = getSharedPreferences("rial", 0);
        Gson gson = new Gson();
        String json = p.getString("MyObject", null);
        String nameText = p.getString("name",null);
        String phoneText = p.getString("phone", null);
        if(nameText != null && phoneText != null){
            name.setText(nameText);
            phone.setText(phoneText);
        }
        if(json != null) {
            Type type = new TypeToken<ArrayList<ArrangeNum>>() {
            }.getType();
            tablesList = gson.fromJson(json, type);
        }
    }

    public void onPause(){
        super.onPause();
        SharedPreferences p = getSharedPreferences("rial",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = gson.toJson(tablesList);
        p.edit().putString("MyObject", json).apply();
        p.edit().putString("name",name.getText().toString()).apply();
        p.edit().putString("phone", phone.getText().toString()).apply();
    }

    public void scan(View view) {
        int requestCode = 2404;
        String lottoName = lottogroup.getText().toString();
        if (name.getText().toString().length() != 0 && lottoName.length() != 0 && phone.toString().length() != 0) {
            String title;
            for (ArrangeNum a : tablesList){
                    title = a.lottogroup;
                if(title.equals(lottoName)){
                    alertBox("ลองใหม่อีกครั้ง","ชื่อชุดที่ซ้ำ กรุณาใช้ชื่ออื่น");
                    requestCode = -1;
                    break;
                }
            }
            if(requestCode == 2404){
                Intent intent = new Intent(MainActivity.this.getApplicationContext(), Scanner.class);
                intent.putExtra("lottogroup", lottoName);
                intent.putExtra("name", name.getText().toString());
                intent.putExtra("phone", phone.getText().toString());
                intent.putExtra("requestCode", requestCode);
                lottogroup.setText("");

                startActivityForResult(intent, requestCode);
                SharedPreferences p = getSharedPreferences("rial",MODE_PRIVATE);
                Gson gson = new Gson();
                String json = gson.toJson(tablesList);
                p.edit().putString("MyObject", json).apply();
                p.edit().putString("name",name.getText().toString()).apply();
                p.edit().putString("phone", phone.getText().toString()).apply();
            }
        } else {
            alertBox("ลองใหม่อีกครั้ง","กรุณากรอกรายละเอียดให้ครบทุกช่อง");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2404 && data != null) {
                all = data.getStringExtra("allNum");
                fromScan =  (ArrangeNum) data.getSerializableExtra("Obj");
                tablesList.add(fromScan);
                //Toast.makeText(getApplicationContext(),"Hello from 2404",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendNetworkRequest(Number num) {
        //Create retrofit instance
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        //Get client & call object for request
        PostNumber client = retrofit.create(PostNumber.class);
        Call<Void> call = client.sendNum(num);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(MainActivity.this, "Send successful", Toast.LENGTH_SHORT).show();
                //Log.i("check", response.body());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(MainActivity.this, "something went wrong :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String pseudoData(){
        String data = "0280,2220,2250,2340,3070,3540,4730,5960,6000,6080,6600,7130,7360,7670,8640,0221,0451,1611,3761," +
                "3921,4881,5891,6001,7011,7131,7491,8501,8671,9581,0132,0232,1342,2352,4262,4492,4602,7142,7492,9452,9492," +
                "0133,1073,1693,2783," +
                "3613,4073,5893,5973,7453,8563,8633,8683,9493,9913,0214,0294,1074,2214,2304,2304,3714,4334,4954,5944,6734,6934," +
                "8644,9574,9924,0215,1135,1555,2215,2925,4735,5935,7035,7375,8295,8425,0146,1156,2346,2366,2376,3026,3756,4736,5106," +
                "5976,6766,7966,8516,8576," +
                "9436,9436,9436,2347,3027,4367,4647,5987,6017,6697,7007,7427,9457,9457,9817,9847,0228,2308,3038,3888," +
                "5888,6748,6928,8368," +
                "9628,9838,0299,1689,2219,3139,3899,4379,4729,5889,5949,5969,6929,7309,7369,7459,8469,9509,9789"+
                "0280,2220,2250,2340,3070,3540,4730,5960,6000,6080,6600,7130,7360,7670,8640,0221,0451,1611,3761," +
                "3921,4881,5891,6001,7011,7131,7491,8501,8671,9581,0132,0232,1342,2352,4262,4492,4602,7142,7492,9452,9492," +
                "0133,1073,1693,2783," +
                "3613,4073,5893,5973,7453,8563,8633,8683,9493,9913,0214,0294,1074,2214,2304,2304,3714,4334,4954,5944,6734,6934," +
                "8644,9574,9924,0215,1135,1555,2215,2925,4735,5935,7035,7375,8295,8425,0146,1156,2346,2366,2376,3026,3756,4736,5106," +
                "5976,6766,7966,8516,8576," +
                "9436,9436,9436,2347,3027,4367,4647,5987,6017,6697,7007,7427,9457,9457,9817,9847,0228,2308,3038,3888," +
                "5888,6748,6928,8368," +
                "9628,9838,0299,1689,2219,3139,3899,4379,4729,5889,5949,5969,6929,7309,7369,7459,8469,9509,9789";
        return data;
    }

    public void setRecycleView() {
        recyclerView = findViewById(R.id.recycler_view);

        mAdapter = new ArrangeNumAdapter(tablesList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT,  this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                ArrangeNum arrangeNum = tablesList.get(position);
                Intent intent = new Intent(MainActivity.this, TableActivity.class);
                intent.putExtra("Obj", arrangeNum);
                startActivity(intent);
//                Toast.makeText(getApplicationContext(), arrangeNum.getLottogroup() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof ArrangeNumAdapter.MyViewHolder) {
            // remove the item from recycler view
            mAdapter.removeItem(viewHolder.getAdapterPosition());

        }
    }

    public void addNewDeleteOld(){
        fromScan = (ArrangeNum) intent.getSerializableExtra("Obj");
        String idFromScan = fromScan.lottogroup;
        Log.i("position", idFromScan);
        String idFromList;
        int position = -1;

        for (ArrangeNum a : tablesList){
            idFromList = a.lottogroup;
            Log.i("position", idFromList);
            if(idFromList.equals(idFromScan)){
                position = tablesList.indexOf(a);
                mAdapter.removeItem(position);
                break;
            }
        }
        Log.i("position", String.valueOf(position));
        tablesList.add(fromScan);
    }
    public void alertBox(String title, String msg){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(msg)
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
}

