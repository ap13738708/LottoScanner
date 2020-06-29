package com.example.scannerproject;

/**
 * Created by P on 2/12/2018.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ArrangeNumAdapter extends RecyclerView.Adapter<ArrangeNumAdapter.MyViewHolder> {

    private List<ArrangeNum> tablesList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, time;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            time = view.findViewById(R.id.time);
            viewBackground = view.findViewById(R.id.view_background);
            viewForeground = view.findViewById(R.id.view_foreground);
        }
    }

//    public class MyViewHolder2 extends RecyclerView.ViewHolder {
//        public RadioButton tick;
//
//        public MyViewHolder2(View itemView) {
//            super(itemView);
//        }
//    }


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
        holder.title.setText(arrangeNum.getLottogroup() + "  " + arrangeNum.getSize() + "เล่ม");
        holder.time.setText("แก้ไขล่าสุด : " + arrangeNum.getTime());
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

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 2 * 2;
    }

}
