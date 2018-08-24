package com.sawant.database_demo;

import android.app.Application;

import com.sawant.database_demo.library.DatabaseConnectionManager;

public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
    }
    private void initDatabase() {
        DatabaseConnectionManager.getInstance(getApplicationContext()).setDbConfiguration("Test.db");
    }
}
