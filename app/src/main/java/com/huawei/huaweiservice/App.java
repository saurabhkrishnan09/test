package com.huawei.huaweiservice;


import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static Context context;
    private static DBHandler dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this.getApplicationContext();
        dbHelper = new DBHandler();
        DatabaseManager.initializeInstance(dbHelper);

    }

    public static Context getContext() {
        return context;
    }

}