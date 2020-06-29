package com.example.scannerproject.matched;

import android.util.Log;

import com.example.scannerproject.di.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MatchedManager {

    private HashMap<String, ArrayList<DetailDao>> mapDao;

    private HashMap<Integer, ArrayList<String>> map;
    private ArrayList<UserDao> userDaoList;

    public void setMatchedHashMap(HashMap<String, ArrayList<DetailDao>> map) {
        this.mapDao = map;
    }

    public ArrayList<UserDao> getUserDaoList() {
        return this.userDaoList;
    }


    public HashMap<Integer , ArrayList<String>> getMatchedData() {
        map = new HashMap<>();
        userDaoList = new ArrayList<>();
        Iterator<String> itr = mapDao.keySet().iterator();
        UserDao tempUserInfo;
        while(itr.hasNext()) {
            String key = itr.next();
            ArrayList<DetailDao> detailDaoList = mapDao.get(key);
            for (int i = 0 ; i < detailDaoList.size(); i++) {
                // In this loop focus on user data because number value in this loop is the same
                DetailDao detailDao = detailDaoList.get(i);
                tempUserInfo = detailDao.getUserDao();
                int userID = tempUserInfo.getId();
                if (map.containsKey(userID)) {
                    map.get(userID).add(detailDao.getNumber());
                } else {
                    ArrayList<String> numStorage = new ArrayList<>();
                    map.put(userID, numStorage);
                    userDaoList.add(tempUserInfo);
                    numStorage.add(detailDao.getNumber());
                }

            }
        }
        return map;
    }
}
