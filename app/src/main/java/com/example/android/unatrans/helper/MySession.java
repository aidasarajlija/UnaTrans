package com.example.android.unatrans.helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.unatrans.data.PregledLogiranogKorisnikaVM;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by mrmai on 11/10/2018.
 */

public class MySession {

    public static PregledLogiranogKorisnikaVM getKorisnik(Context context){

        SharedPreferences sharedPreferences=context.getSharedPreferences("DatotekaZaSharedPreferences",context.MODE_PRIVATE);
        String strJson = sharedPreferences.getString("spKey", "");

        if(strJson.length()==0)
            return null;

        Gson gson=new GsonBuilder().create();
        PregledLogiranogKorisnikaVM korisnik = MyGson.build().fromJson(strJson,PregledLogiranogKorisnikaVM.class);

        return korisnik;

    }

    public static void setKorisnik(Context context, PregledLogiranogKorisnikaVM k){

        String strJson =k!=null ? MyGson.build().toJson(k):"";

        SharedPreferences sharedPreferences=context.getSharedPreferences("DatotekaZaSharedPreferences",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("spKey",strJson);
        editor.apply();
    }

}
