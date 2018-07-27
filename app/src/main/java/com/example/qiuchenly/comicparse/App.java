package com.example.qiuchenly.comicparse;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.qiuchenly.comicparse.VolleyImp.CookieHelper;

public class App extends Application {
    private static final String TAG = "App";

    private static RequestQueue Queue;
    private static CookieHelper CookieMaster;
    static SharedPreferences sp;
    public static Context ctx;

    public static CookieHelper getCookieMaster() {
        return CookieMaster;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ctx=this;
        Queue = Volley.newRequestQueue(this);
        CookieMaster = new CookieHelper();
        sp = this.getSharedPreferences("qcly", MODE_PRIVATE);
        CookieMaster.addCookie(sp.getString("Cookie", ""));
    }

    public static void closedApp() {
        Save();
        Queue = null;
        CookieMaster = null;
        sp = null;
        System.exit(0);
    }

    public static RequestQueue getQueue() {
        return Queue;
    }

    public static void Save() {
        sp.edit().putString("Cookie", CookieMaster.toString()).apply();
    }
}
