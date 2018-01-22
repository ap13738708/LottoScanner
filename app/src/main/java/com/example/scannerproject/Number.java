package com.example.scannerproject;

/**
 * Created by P on 1/10/2018.
 */

public class Number {

    private String[] num;
    private String lottoGroup;

    public Number(String[] num, String lottoGroup) {
        this.num = num;
        this.lottoGroup = lottoGroup;
    }

    public String[] getNum() {
        return num;
    }

    public String getLottoGroup() {
        return lottoGroup;
    }

    public void setLottoGroup(String lottoGroup) {
        this.lottoGroup = lottoGroup;
    }
}
