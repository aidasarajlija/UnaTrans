package com.example.android.unatrans.fragmenti;


import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.android.unatrans.R;
import com.example.android.unatrans.data.PregledGradovaVM;
import com.example.android.unatrans.data.ZakupAutobusaAddVM;
import com.example.android.unatrans.helper.MyApiRequest;
import com.example.android.unatrans.helper.MyRunnable;
import com.example.android.unatrans.helper.MyRunnableBaza;
import com.example.android.unatrans.helper.MySession;
import com.example.android.unatrans.helper.MyUtils;

import java.sql.Time;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Zakup_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class Zakup_Fragment extends Fragment {
    private TextView grad;
    private DatePickerDialog.OnDateSetListener picker;
    private DatePickerDialog.OnDateSetListener picker1;
    private EditText ime;
    private EditText jmbg;
    private EditText telefon;
    private EditText mail;
    private EditText ruta;
    private EditText broj;
    private EditText adresa;
    private TextView datumpolaska;
    private TextView vrijemePolaska;
    private TextView datumdolaska;
    private TextView vrijemeDolaska;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters




    // TODO: Rename and change types and number of parameters
    public static Zakup_Fragment newInstance() {
        Zakup_Fragment fragment = new Zakup_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        View view=inflater.inflate(R.layout.fragment_zakup_, container, false);

        ime = view.findViewById(R.id.txtImeFirma);
        jmbg = view.findViewById(R.id.txtJMBG);
        telefon = view.findViewById(R.id.txtTelefon);
        mail = view.findViewById(R.id.txtEmail);
        ruta = view.findViewById(R.id.txtRuta);
        broj = view.findViewById(R.id.txtBrojPutnika);
        adresa = view.findViewById(R.id.txtAdresaPolaska);
        datumpolaska = view.findViewById(R.id.txtDatumPolaskaZakup);
        vrijemePolaska = view.findViewById(R.id.txtVrijemePolaska);
        datumdolaska = view.findViewById(R.id.txtDatumDolaska);
        vrijemeDolaska = view.findViewById(R.id.txtVrijemeDolaska);
        grad = view.findViewById(R.id.txtGrad);
        Button posalji=view.findViewById(R.id.btnPosalji);
        ImageButton nazad=view.findViewById(R.id.btn_Nazad);

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.dodajFragment(getActivity(), R.id.mjesto, Pocetni_Fragment.newInstance());
            }
        });

        grad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OdabirGrada odabirGrada=OdabirGrada.newInstance(gradZakup);
                odabirGrada.show(getFragmentManager(), "p");
            }
        });

        datumpolaska.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();

                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int dan=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, picker, year, month, dan);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        picker=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                datumpolaska.setText(Integer.toString(dayOfMonth)+"/" + Integer.toString(month+1) + "/" +  Integer.toString(year));
            }
        };

        datumdolaska.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();

                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int dan=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, picker1, year, month, dan);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        picker1=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                datumdolaska.setText(Integer.toString(dayOfMonth)+"/" + Integer.toString(month+1) + "/" +  Integer.toString(year));
            }
        };

        vrijemePolaska.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                   @Override
                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                       vrijemePolaska.setText(hourOfDay + ":" + minute);
                   }
               },
               0, 0, false);

                timePickerDialog.show();

            }

        });

        vrijemeDolaska.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        vrijemeDolaska.setText(hourOfDay + ":" + minute);
                    }
                },
                        0, 0, false);

                timePickerDialog.show();

            }

        });


        final ZakupAutobusaAddVM zakup=new ZakupAutobusaAddVM();
        posalji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    zakup.adresaPolaska = adresa.getText().toString();
                    zakup.brojPutnika = Integer.parseInt(broj.getText().toString());
                    zakup.datumDolaska = datumdolaska.getText().toString();
                    zakup.datumPolaska = datumpolaska.getText().toString();
                    zakup.vrijemeDolaska = vrijemeDolaska.getText().toString();
                    zakup.vrijemePolaska = vrijemePolaska.getText().toString();
                    zakup.email = mail.getText().toString();
                    zakup.imePrezimeFirma = ime.getText().toString();
                    zakup.jMBG = jmbg.getText().toString();
                    zakup.rutaPutovanja = ruta.getText().toString();
                    zakup.telefon = telefon.getText().toString();
                    zakup.zakupGradId = gradId;
                    zakup.zakupKorisnikId=MySession.getKorisnik(getActivity()).id;


                    MyRunnableBaza<ZakupAutobusaAddVM> nekiCallback = new MyRunnableBaza<ZakupAutobusaAddVM>() {
                        @Override
                        public void Run(ZakupAutobusaAddVM zakupAutobusaAddVM) {
                            Toast.makeText(getActivity(), "Zakup autobusa je uspješno spremljen!", Toast.LENGTH_SHORT).show();
                            MyUtils.dodajFragment(getActivity(), R.id.mjesto, Pocetni_Fragment.newInstance());
                        }
                    };

                    MyApiRequest.post(getActivity(), "ZakupAutobusa/AddZakupAutobusa", zakup, nekiCallback);

                }
                catch (Exception w){
                    Toast.makeText(getActivity(), "Sva polja su obavezna!", Toast.LENGTH_SHORT).show();

                }



            }
        });


        return view;
    }


private int gradId;
    MyRunnable gradZakup=new MyRunnable<PregledGradovaVM.Row>() {
        @Override
        public void run(PregledGradovaVM.Row o) {
            gradId=o.id;
            grad.setText(o.naziv);
        }
    };

}
