package com.example.android.unatrans;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.unatrans.data.PregledLogiranogKorisnikaVM;
import com.example.android.unatrans.data.ZakupAutobusaAddVM;
import com.example.android.unatrans.helper.MyApiRequest;
import com.example.android.unatrans.helper.MyRunnableBaza;
import com.example.android.unatrans.helper.MySession;

public class LoginActivity extends AppCompatActivity {

    private EditText mail;
    private EditText lozinka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ((AppCompatActivity) this).getSupportActionBar().hide();


        mail = findViewById(R.id.email);
        lozinka = findViewById(R.id.lozinka);
        Button prijava=findViewById(R.id.btnPrijava);
        TextView registracija=findViewById(R.id.registracija);

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              do_registracija();
            }
        });


            prijava.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                        do_prijava();
                    }


            });


    }

    private void do_registracija() {

        startActivity(new Intent(this, RezervacijaActivity.class));
    }

    private PregledLogiranogKorisnikaVM korisnik=new PregledLogiranogKorisnikaVM();
    private void do_prijava() {


            String email=mail.getText().toString();
            String pass=lozinka.getText().toString();

        if (email.equals("") || pass.equals("")) {
            Toast.makeText(this, "Potrebno unijeti podatke za prijavu!", Toast.LENGTH_SHORT).show();
        }
        else {

            MyRunnableBaza<PregledLogiranogKorisnikaVM> nekiCallback = new MyRunnableBaza<PregledLogiranogKorisnikaVM>() {
                @Override
                public void Run(PregledLogiranogKorisnikaVM novi) {
                    korisnik = novi;
                    provjeri(korisnik);

                }
            };

            MyApiRequest.get(this, "Login/GetKorisnika/" + email + "/" + pass, nekiCallback);
        }

    }

    private void provjeri(PregledLogiranogKorisnikaVM korisnik) {
        if(korisnik.id==0)
        {
            Toast.makeText(this, "Pogrešan email ili lozinka!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            MySession.setKorisnik(this, korisnik);
            startActivity(new Intent(this, MainActivity.class));
        }
    }



}
