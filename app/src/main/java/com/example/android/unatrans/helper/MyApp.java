package com.example.android.unatrans.helper;

import android.app.Application;
import android.content.Context;

/**
 * Created by mrmai on 11/10/2018.
 */

public class MyApp extends Application {
    private static Context context;

    public static Context getContext(){return context;}

    @Override
    public void onCreate(){
        super.onCreate();
        context=getApplicationContext();
    }
}

