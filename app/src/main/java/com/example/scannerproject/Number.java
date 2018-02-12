package com.example.scannerproject;

import com.google.gson.annotations.SerializedName;

/**
 * Created by P on 1/10/2018.
 */

public class Number {
    @SerializedName("stagename")
    private String name;
    @SerializedName("lottonum")
    private String num;
    @SerializedName("lottogroup")
    private String lottoGroup;
    @SerializedName("phone")
    private String phone;

    public Number(String name, String phone,String num, String lottoGroup ) {
        this.num = num;
        this.lottoGroup = lottoGroup;
        this.phone = phone;
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public String getLottoGroup() {
        return lottoGroup;
    }

    public void setLottoGroup(String lottoGroup) {
        this.lottoGroup = lottoGroup;
    }
}
