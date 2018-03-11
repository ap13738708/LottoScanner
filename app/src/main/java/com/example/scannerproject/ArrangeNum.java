package com.example.scannerproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by P on 1/29/2018.
 */

public class ArrangeNum implements Serializable {
    String matched = "";
    String lottogroup;
    String time;
    ArrayList<String> zero = new ArrayList<String>();
    ArrayList<String> one = new ArrayList<String>();
    ArrayList<String> two = new ArrayList<String>();
    ArrayList<String> three = new ArrayList<String>();
    ArrayList<String> four = new ArrayList<String>();
    ArrayList<String> five = new ArrayList<String>();
    ArrayList<String> six = new ArrayList<String>();
    ArrayList<String> seven = new ArrayList<String>();
    ArrayList<String> eight = new ArrayList<String>();
    ArrayList<String> nine = new ArrayList<String>();
    ArrayList<String> all = new ArrayList<String>();
    String[] arrayAll;

    public ArrangeNum(String[] array, String name, String time) {
        this.lottogroup = name;
        this.time = time;
        add(array);
        sortAll();
    }

    public void sortAll() {
        Collections.sort(zero);
        Collections.sort(one);
        Collections.sort(two);
        Collections.sort(three);
        Collections.sort(four);
        Collections.sort(five);
        Collections.sort(six);
        Collections.sort(seven);
        Collections.sort(eight);
        Collections.sort(nine);
    }

    public void add(String[] array) {
        int i;
        for (i = 0; i < array.length; i++) {
            if(all.contains(array[i])){
                matched += array[i] + ",";
            }
            all.add(array[i]);

            switch (array[i].substring(3)) {
                case "0":
                    zero.add(array[i]);
                    break;
                case "1":
                    one.add(array[i]);
                    break;
                case "2":
                    two.add(array[i]);
                    break;
                case "3":
                    three.add(array[i]);
                    break;
                case "4":
                    four.add(array[i]);
                    break;
                case "5":
                    five.add(array[i]);
                    break;
                case "6":
                    six.add(array[i]);
                    break;
                case "7":
                    seven.add(array[i]);
                    break;
                case "8":
                    eight.add(array[i]);
                    break;
                case "9":
                    nine.add(array[i]);
                    break;
                default:
                    System.out.println("Not number " + array[i]);
                    break;
            }
        }
        sortAll();
    }

    public String toString(ArrayList<String> list) {
        String all = "";
        for (String num : list) {
            all += num + "\n";
        }
        return all;
    }

    public String[] getAllArray(){
        arrayAll = all.toArray(new String[all.size()]);
        return arrayAll;
    }

    public String getLottogroup(){
        return this.lottogroup;
    }

    public String getTime(){ return  this.time; }

    public void setTime(String time) {
        this.time = time;
    }
}

