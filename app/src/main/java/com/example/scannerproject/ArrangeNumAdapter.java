package com.example.scannerproject;

/**
 * Created by P on 2/12/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ArrangeNumAdapter extends RecyclerView.Adapter<ArrangeNumAdapter.MyViewHolder> {

    private List<ArrangeNum> tablesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, time;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            year = view.findViewById(R.id.year);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }


    public ArrangeNumAdapter(List<ArrangeNum> tablesList) {
        this.tablesList = tablesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.table_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ArrangeNum arrangeNum = tablesList.get(position);
        holder.title.setText(arrangeNum.getLottogroup());
//        holder.genre.setText(arrangeNum.getGenre());
//        holder.year.setText(arrangeNum.getYear());
    }

    @Override
    public int getItemCount() {
        return tablesList.size();
    }

    public void removeItem(int position) {
        tablesList.remove(position);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyItemRemoved(position);
    }
}
