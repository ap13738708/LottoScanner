package com.example.scannerproject;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DataList {

    @SerializedName("data")
    private ArrayList<Data> data;

    public DataList( ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> getData() {
        return data;
    }

}
