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
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.android.unatrans.data.PregledLogiranogKorisnikaVM;
import com.example.android.unatrans.helper.MyApiRequest;
import com.example.android.unatrans.helper.MyRunnableBaza;

public class RezervacijaActivity extends AppCompatActivity {

    private EditText ime;
    private EditText prezime;
    private EditText email;
    private EditText lozinka;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rezervacija);

        ime = findViewById(R.id.txtIme);
        prezime = findViewById(R.id.Prezime);
        email = findViewById(R.id.txtEmail);
        lozinka = findViewById(R.id.txtLozinka);

        Button registracija=findViewById(R.id.btnRegistracija);
        ImageButton nazad=findViewById(R.id.btn_Nazad4);

        registracija.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                do_registracija();
            }
        });

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });
    }

   PregledLogiranogKorisnikaVM novi=new PregledLogiranogKorisnikaVM();
    private void do_registracija() {

            novi.ime=ime.getText().toString();
            novi.prezime=prezime.getText().toString();
            novi.email=email.getText().toString();
            novi.lozinka=lozinka.getText().toString();


            MyRunnableBaza<PregledLogiranogKorisnikaVM> nekiCallback = new MyRunnableBaza<PregledLogiranogKorisnikaVM>() {
                @Override
                public void Run(PregledLogiranogKorisnikaVM t) {
//                 View parentLayout=findViewById(android.R.id.content);
                    login();
       //             Snackbar.make(parentLayout,"Rezervacija uspješno spremljena",Snackbar.LENGTH_LONG).show();

                }
            };

            MyApiRequest.post(this, "Login/Add",novi, nekiCallback);
        }

    private void login() {

        startActivity(new Intent(this, LoginActivity.class));
    }

}
