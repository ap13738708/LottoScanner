package com.example.scannerproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
    final String BASE_URL = "http://119.59.123.156:2222/";

    private List<ArrangeNum> tablesList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ArrangeNumAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        test = findViewById(R.id.test);

        phone = findViewById(R.id.phone);
        name = findViewById(R.id.name);
        lottogroup = findViewById(R.id.lottoGroup);

        send_data = findViewById(R.id.send_data);
        beapSound = MediaPlayer.create(this, R.raw.censor_beep_01);
        setRecycleView();
        ArrangeNum one = new ArrangeNum(pseudoData().split(","),"lotto1");
        tablesList.add(one);
        String[] testtest = {"1234","2345","4567"};
        one = new ArrangeNum(pseudoData().split(","),"lotto2");
        tablesList.add(one);
        one = new ArrangeNum(testtest,"lotto3");
        tablesList.add(one);
        one = new ArrangeNum(pseudoData().split(","),"lotto4");
        tablesList.add(one);
        one = new ArrangeNum(pseudoData().split(","),"lotto5");
        tablesList.add(one);
        one = new ArrangeNum(pseudoData().split(","),"lotto6");
        tablesList.add(one);
        one = new ArrangeNum(pseudoData().split(","),"lotto7");
        tablesList.add(one);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                all = pseudoData();
                Number number = new Number(name.getText().toString(), phone.getText().toString(), all, lottogroup.getText().toString());
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
                    ArrangeNum fake = new ArrangeNum(array, lottogroup.getText().toString());
                    Intent intent = new Intent(MainActivity.this, TableActivity.class);
                    //intent.putStringArrayListExtra("ArrayList", num_list);
//                    intent.putStringArrayListExtra("ArrayList", num_list);
                    intent.putExtra("Obj", fake);
                    startActivity(intent);
                }
            }
        });
    }

    public void scan(View view) {
        if (name.getText().toString().length() != 0 && lottogroup.getText().toString().length() != 0 && phone.toString().length() != 0) {
            Intent intent = new Intent(MainActivity.this.getApplicationContext(), Scanner.class);
            intent.putExtra("lottogroup", lottogroup.getText().toString());
            lottogroup.setText("");
            startActivityForResult(intent, 2404);
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle("ลองใหม่อีกครั้ง");

            // set dialog message
            alertDialogBuilder
                    .setMessage("กรุณากรอกรายละเอียดให้ครบทุกช่อง")
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 2404 && data != null) {
//                num_list = data.getStringArrayListExtra("num_list");
                all = data.getStringExtra("allNum");
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
}

