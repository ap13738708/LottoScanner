package com.example.scannerproject.matched;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.scannerproject.R;
import com.example.scannerproject.di.UserDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MatchedAdapter extends RecyclerView.Adapter<MatchedAdapter.ViewHolder> {

    private ArrayList<UserDao> userDaoList;
    private HashMap<Integer, ArrayList<String>> map;

    public MatchedAdapter(ArrayList<UserDao> userDaoList, HashMap<Integer, ArrayList<String>> map) {
        this.userDaoList = userDaoList;
        this.map = map;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_matched_num, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int pos) {

        UserDao userDao = userDaoList.get(pos);
        ArrayList<String> temp = map.get(userDao.getId());
        holder.tvName.setText(userDao.getName());
        holder.tvPhone.setText(userDao.getTel());
        holder.tvNum.setText(transformData(temp));


    }

    @Override
    public int getItemCount() {
        return userDaoList.size();
    }

    public String transformData(ArrayList<String> list) {
        Collections.sort(list);
        String result = "";
        for (String num : list) {
            result += num + ", ";
        }
        return result.trim().substring(0, result.length() - 1);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName, tvPhone, tvNum;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvPhone = view.findViewById(R.id.tvPhone);
            tvNum = view.findViewById(R.id.tvNum);
        }
    }
}
