package com.example.scannerproject.di;

import com.google.gson.annotations.SerializedName;

public class UserDao {

    private int id;
    private String name;
    private String surname;
    private String tel;
    private String line;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

    public UserDao(int id, String name, String surname, String tel, String line, String createdAt, String updatedAt) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.tel = tel;
        this.line = line;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getTel() {
        return tel;
    }

    public String getLine() {
        return line;
    }
}
