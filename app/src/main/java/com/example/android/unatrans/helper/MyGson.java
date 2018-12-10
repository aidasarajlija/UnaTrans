package com.example.android.unatrans.helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by mrmai on 11/10/2018.
 */

public class MyGson {

    public static GsonBuilder builder(){

        GsonBuilder builder= new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        return builder;

    }
    public static Gson build(){
        return builder().create();
    }


}
