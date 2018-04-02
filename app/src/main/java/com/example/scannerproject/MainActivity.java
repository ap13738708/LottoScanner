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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements RecyclerItemTouchHelper.RecyclerItemTouchHelperListener {

    Context mContext = this;
    EditText lottogroup, name, phone;
    Button type_data ;
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


        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        lottogroup = findViewById(R.id.lottoGroup);

//        type_data = findViewById(R.id.type_data);
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

//        phone.addTextChangedListener(new TextWatcher() {
//            int countText = 0;
//            int pastCount;
//            boolean check = true;
//            int pastDelete;
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
//                pastCount = s.length();
//                pastDelete = (int) s.charAt(s.length() - 1 );
//                Log.d("TAG", "beforeTextChanged " + ", " + countText + "| pastCount :" + pastCount);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//
//                Log.d("TAG", "onTextChanged " + ", " + countText + "| Current count :" + s.length());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if(pastCount > s.length()){
//                    if(countText != 0)countText--;
//                    if(pastDelete == 45)
//                } else {
//                    countText++;
//                }
//               if(countText == 4 && check){
//                   phone.append("-");
//                   countText = 0;
//               } else {
//
//               }
//
//                Log.d("TAG", "afterTextChanged "+ ", " + countText + "| count :" + s.length());
//            }
//        });


//        type_data.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                all = pseudoData();
//                if (all == null) {
//                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
//
//                    alertDialogBuilder.setTitle("Error");
//
//                    alertDialogBuilder
//                            .setMessage("No Data")
//                            .setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alertDialog = alertDialogBuilder.create();
//                    alertDialog.show();
//                } else {
//
//                    String[] array = all.split(",");
//                    num_list = new ArrayList<>(Arrays.asList(array));
//                    Number number = new Number(name.getText().toString(), phone.getText().toString(), all, lottogroup.getText().toString());
////                    sendNetworkRequest(number);
//                    ArrangeNum fake = new ArrangeNum(array, lottogroup.getText().toString(), "fake time" , name.getText().toString(), phone.getText().toString());
//                    Intent intent = new Intent(MainActivity.this, TableActivity.class);
//                    //intent.putStringArrayListExtra("ArrayList", num_list);
////                    intent.putStringArrayListExtra("ArrayList", num_list);
//                    intent.putExtra("Obj", fake);
//                    startActivity(intent);
//                }
//            }
//        });
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
//        String data = "0280,2220,2250,2340,3070,3540,4730,5960,6000,6080,6600,7130,7360,7670,8640,0221,0451,1611,3761," +
//                "3921,4881,5891,6001,7011,7131,7491,8501,8671,9581,0132,0232,1342,2352,4262,4492,4602,7142,7492,9452,9492," +
//                "0133,1073,1693,2783," +
//                "3613,4073,5893,5973,7453,8563,8633,8683,9493,9913,0214,0294,1074,2214,2304,2304,3714,4334,4954,5944,6734,6934," +
//                "8644,9574,9924,0215,1135,1555,2215,2925,4735,5935,7035,7375,8295,8425,0146,1156,2346,2366,2376,3026,3756,4736,5106," +
//                "5976,6766,7966,8516,8576," +
//                "9436,9436,9436,2347,3027,4367,4647,5987,6017,6697,7007,7427,9457,9457,9817,9847,0228,2308,3038,3888," +
//                "5888,6748,6928,8368," +
//                "9628,9838,0299,1689,2219,3139,3899,4379,4729,5889,5949,5969,6929,7309,7369,7459,8469,9509,9789"+
//                "0280,2220,2250,2340,3070,3540,4730,5960,6000,6080,6600,7130,7360,7670,8640,0221,0451,1611,3761," +
//                "3921,4881,5891,6001,7011,7131,7491,8501,8671,9581,0132,0232,1342,2352,4262,4492,4602,7142,7492,9452,9492," +
//                "0133,1073,1693,2783," +
//                "3613,4073,5893,5973,7453,8563,8633,8683,9493,9913,0214,0294,1074,2214,2304,2304,3714,4334,4954,5944,6734,6934," +
//                "8644,9574,9924,0215,1135,1555,2215,2925,4735,5935,7035,7375,8295,8425,0146,1156,2346,2366,2376,3026,3756,4736,5106," +
//                "5976,6766,7966,8516,8576," +
//                "9436,9436,9436,2347,3027,4367,4647,5987,6017,6697,7007,7427,9457,9457,9817,9847,0228,2308,3038,3888," +
//                "5888,6748,6928,8368," +
//                "9628,9838,0299,1689,2219,3139,3899,4379,4729,5889,5949,5969,6929,7309,7369,7459,8469,9509,9789";
        String data = "0120,0150,0220,0240,0380,0450,0470,0490,0740,0910,0910,1130,1360,1380,1730,1830,1850,2080,2190,2430,2520,2540,2920,2930,2970,3080,3180,3220,3270,3390," +
                "3840,4110,4140,4460,4470,4670,4820,4980,5130,5240,5310,5480,5670,5910,6080,6230,6240,6480,6790,6820,7250,7280,7320,7520,7660,7690,8520,8780,9330,9520,9900," +
                "9910,0311,0681,0851,0891,0951,1011,1291,1311,1451,1551,1671,1681,1721,1961,2471,2741,2911,2971,3151,3271,3281,3341,3391,3601,4131,4321,4471,4521,4571,4631,4911,5011," +
                "5061,5091,5211,5581,5851,5861,6081,6171,6211,6241,6271,6351,6421,6701,6751,6901,6911,7141,7171,7321,7331,7371,7441,7541,7551,7571,8101,8321,8511,8661," +
                "8701,8811,8901,9191,9201,9461,9521,9621,9811,9901,0182,0462,0632,0792,0902,0932,0942,0972,0982,1232,1402,1592,1632,2022,2102,2152,2402," +
                "2462,2502,2732,2742,2752,2782,2812,2842,2902,3072,3092,3302,3392,3392,3462,3742,3782,3882,3922,4042,4082,4312,4612,4822,5212,5242,5332,5802,5842,5892,6042,6102,6142,6152,6352," +
                "6442,6552,6942,6992,7072,7082,7602,7712,7742,8162,8612,8672,8982,9042,9152,9242,9272,9322,9632,9842,0473,0483,0513,0733,0883,0893,0913,1053,1133,1523,1563,1613,1733,1903,2373,2563," +
                "2863,3073,3083,3373,3613,3813,3843,3873,4133,4143,4323,4443,4583,4833,4843,5073,5493,5673,5783,5903,5963,6073,6143,6163,6313,6333,6403,6473,6533,6553,6573,7103,7193,7253,7333,7643,7693," +
                "8153,8173,8253,8283,8313,8323,8343,8433,8583,8733,8813,9243,9433,9623,9653,9703,9913,0054,0154,0194,0434,0454,0474,0734,0774,0954,1234,1394,1434,1554,1924,2174,2744,2744,2774," +
                "2814,2874,3264,3554,3684,4104,4114,4254,4404,4514,4624,4814,5294,5304,5504,5814,5894,5964,6084,6104,6204,6324,6334,6524,6734,7134,7174,7214,7244,7344,7374,7544," +
                "7834,7874,8144,8174,8314,8364,8434,8444,8494,8544,8574,8614,8794,8934,8984,9364,9434,9504,9514,9584,9684,9774,9784,9824,0105,0155,0805,0905,1095,1115,1295," +
                "1405,1495,1575,1745,1875,2165,2175,2295,2535,2565,2745,2905,2935,3455,3495,3645,3765,3805,4185,4195,4245,4305,4685,5025,5175,5255,5565,5875,5985," +
                "6235,6245,6385,6535,6655,6825,7045,7175,7475,7505,7605,7685,7785,7965,8315,8355,8405,8415,8475,8525,8585,8615,8825,8905,9335,9685,9735," +
                "9775,9785,0186,0456,0476,0516,0646,0656,0776,0796,0876,0966,0986,1246,1456,1526,1726,1796,2036,2076,2146,2516,2646,2736,2756,2936,3266,3396,3486,3536," +
                "3756,4286,4306,4346,4556,4776,4896,5066,5106,5246,5756,5796,5996,6236,6286,6416,6476,6726,7036,7136,7176,7256,7366,7406,7436,7696,8036,8316,8846,8906," +
                "8976,8986,9046,9066,9126,9176,9286,9666,9716,9746,9826,0287,0807,0817,0867,1097,1377,1397,1507,1527,1657,1727,1947,2197,2217,2327,2337,2417,2537,2727,2847," +
                "3047,3487,3557,3667,4137,4277,4387,4437,4607,4677,4757,4997,5597,5677,6307,6357,6417,6447,6817,7047,7177,7217,7227,7267,7277,7287,7317,7347,7417,7557,7877,8527,8597," +
                "8607,8687,8817,9057,9157,9247,9427,9577,9687,0198,0598,0668,0778,0898,1058,1248,1298,1458,1538,1678,1708,1728,1928,2208,2268,2468,2648,2698,2708,2898,3148,3218," +
                "3718,3738,3808,3978,4118,4228,4328,4608,4828,4888,5018,5028,5048,5678,5708,5868,5968,6248,6338,6348,6548,6578,6918,7018,7228,7248,7258,7428,7578,8298,8318,8528,8588,8688" +
                ",8728,8828,8878,9208,9278,9318,9358,9618,9928,9948,9978,0169,0399,0599,0809,0969,0989,1239,1389,1499,1539,1709,1879,1889,1959,2089,2169,2199,2229,2469,2569,2589,2819,3089,3799," +
                "3809,3839,3849,3869,4119,4159,4189,4389,4559,4579,4719,4739,4829,5069,5249,5299,5459,5549,5569,5649,5909,5949,5999," +
                "6019,6109,6659,6809,6829,6939,7329,7449,7579,8159,8359,8599,8759,8789,8809,8909,9009,9169,9329,9439,9829,9859,9869,9929";
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

