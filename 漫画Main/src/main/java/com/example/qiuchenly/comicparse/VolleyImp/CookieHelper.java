package com.example.qiuchenly.comicparse.VolleyImp;

import com.example.qiuchenly.comicparse.App;

import java.util.ArrayList;
import java.util.List;


/**
 * Cookie辅助类 实现持久化存储Session
 */
public class CookieHelper {

    List<CookieSimple> mListCookie;

    private class CookieSimple {
        public String Key, Value;

        @Override
        public String toString() {
            return Key + "=" + Value;
        }
    }

    public CookieHelper() {
        mListCookie = new ArrayList<>();
    }

    public void addCookie(String collectionData) {
        if (collectionData.contains(";")) {
            for (String s : collectionData.split(";")) {
                String[] arr = s.split("=");
                if (arr.length == 2) {
                    //valid data & add this
                    addCookie(arr[0], arr[1]);
                }
            }
        }
    }

    public void addCookie(String key, String value) {
        int cnt = hasCookie(key);
        if (cnt == -1) {
            CookieSimple cookie = new CookieSimple();
            cookie.Key = key;
            cookie.Value = value;
            mListCookie.add(cookie);
        } else {
            mListCookie.get(cnt).Value = value;
        }
        App.Save();
    }

    public boolean removeCookie(String key) {
        int cnt = hasCookie(key);
        if (cnt != -1) {
            mListCookie.remove(cnt);
            return true;
        }
        return false;
    }

    private int hasCookie(String Key) {
        int thisCnt = 0;
        for (CookieSimple cookieSimple : mListCookie) {
            if (cookieSimple.Key.contains(Key))
                return thisCnt;
            thisCnt++;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < mListCookie.size(); i++) {
            str.append(mListCookie.get(i).toString()).append(";");
        }
        return str.toString();
    }
}
