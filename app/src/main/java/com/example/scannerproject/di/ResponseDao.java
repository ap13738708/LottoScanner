package com.example.scannerproject.di;

import com.google.gson.annotations.SerializedName;

public class ResponseDao {

    @SerializedName("message")
    private String message;
//    private Error error;

    public ResponseDao(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

//    class Error {
//
//    }
}
