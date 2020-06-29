package com.example.scannerproject.matched;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scannerproject.R;
import com.example.scannerproject.di.ApiService;
import com.example.scannerproject.di.SummaryDao;
import com.example.scannerproject.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MatchedActivity extends AppCompatActivity {

    TextView tvNumAmount;
    MatchedManager manager;

    HashMap mapMatched;

    RecyclerView recyclerView;
    MatchedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matched);

        manager = new MatchedManager();
        tvNumAmount = findViewById(R.id.tvNumAmount);
        recyclerView = findViewById(R.id.recycler_view);

        getSummaryDaoInfo();
        getMatched();

    }

    private void getSummaryDaoInfo() {
        Call<SummaryDao> call = ApiService.httpManager().getSummaryDaoInfo();
        call.enqueue(new Callback<SummaryDao>() {
            @Override
            public void onResponse(Call<SummaryDao> call, Response<SummaryDao> response) {
                if (response.isSuccessful()) {
                    SummaryDao SummaryDao = response.body();
                    tvNumAmount.setText(SummaryDao.getBookAmount());
                } else {
                    Utils.showToast(getApplicationContext(), "Can't fetched data");
                }
            }

            @Override
            public void onFailure(Call<SummaryDao> call, Throwable t) {
                Utils.showToast(getApplicationContext(), "Something went wrong");
            }
        });
    }

    private void getMatched() {
        Call<HashMap<String, ArrayList<DetailDao>>> call = ApiService.httpManager().getMatchedNum();
        call.enqueue(new Callback<HashMap<String, ArrayList<DetailDao>>>() {
            @Override
            public void onResponse(Call<HashMap<String, ArrayList<DetailDao>>> call, Response<HashMap<String, ArrayList<DetailDao>>> response) {
                if (response.isSuccessful()) {
                    Log.e("onResponse", "get successful");
                    manager.setMatchedHashMap(response.body());
                    mapMatched = manager.getMatchedData();
                    adapter = new MatchedAdapter(manager.getUserDaoList(),mapMatched);
                    Log.e("userDao", String.valueOf(manager.getUserDaoList().size()));
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Log.e("onResponseError", "Failed");
                }

            }

            @Override
            public void onFailure(Call<HashMap<String, ArrayList<DetailDao>>> call, Throwable t) {
                Log.e("onFailure", t.toString());
            }
        });
    }
}
