package com.example.scannerproject;


import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("number")
    private String number;
    @SerializedName("amount")
    private int amount;

    public Data(String number, int amount) {
        this.number = number;
        this.amount = amount;
    }

    public void incrementAmount() {
        amount++;
    }

    public String toString() {
        return "{" + this.number + "," + this.amount + "}";
    }
}
