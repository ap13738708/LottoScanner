package com.example.scannerproject.di;

import com.google.gson.annotations.SerializedName;

public class SummaryDao {

    @SerializedName("user")
    private int user;
    @SerializedName("book_amount")
    private String bookAmount;

    public SummaryDao(int user, String bookAmount) {
        this.user = user;
        this.bookAmount = bookAmount;
    }

    public int getUser() {
        return user;
    }

    public String getBookAmount() {
        return bookAmount;
    }
}
