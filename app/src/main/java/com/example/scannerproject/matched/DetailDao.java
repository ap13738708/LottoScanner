package com.example.scannerproject.matched;

import com.example.scannerproject.di.UserDao;
import com.google.gson.annotations.SerializedName;

public class DetailDao {

    private String id;
    private String number;
    private int amount;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    @SerializedName("user")
    private UserDao userDao;

    public DetailDao(String id, String number, int amount, String createdAt, String updatedAt, UserDao userDao) {
        this.id = id;
        this.number = number;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userDao = userDao;
    }

    public String getNumber() {
        return number;
    }

    public int getAmount() {
        return amount;
    }

    public UserDao getUserDao() {
        return userDao;
    }
}
