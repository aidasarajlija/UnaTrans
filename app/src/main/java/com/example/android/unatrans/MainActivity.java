package com.example.android.unatrans;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.unatrans.data.PregledLogiranogKorisnikaVM;
import com.example.android.unatrans.fragmenti.Pocetni_Fragment;
import com.example.android.unatrans.helper.MySession;
import com.example.android.unatrans.helper.MyUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        MyUtils.dodajFragment(this, R.id.mjesto, Pocetni_Fragment.newInstance());

//        PregledLogiranogKorisnikaVM x=MySession.getKorisnik(this);
//        if(x==null)
//        {
//            startActivity(new Intent(this, LoginActivity.class));
//        }
//        else {
//            startActivity(new Intent(this, MainActivity.class));
//
//        }
    }
}
