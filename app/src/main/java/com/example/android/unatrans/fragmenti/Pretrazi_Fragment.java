package com.example.android.unatrans.fragmenti;


import android.app.DatePickerDialog;
import android.app.DialogFragment;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.unatrans.R;
import com.example.android.unatrans.data.PotrebniPodaci;
import com.example.android.unatrans.data.PregledGradovaVM;
import com.example.android.unatrans.data.PregledLinijaVM;
import com.example.android.unatrans.helper.MyApiRequest;
import com.example.android.unatrans.helper.MyRunnable;
import com.example.android.unatrans.helper.MyRunnableBaza;
import com.example.android.unatrans.helper.MyUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Pretrazi_Fragment extends Fragment {
    private TextView polaziste;
    private TextView odrediste;
    private DatePickerDialog.OnDateSetListener picker;
    private TextView datum;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    // TODO: Rename and change types and number of parameters
    public static Pretrazi_Fragment newInstance() {
        Pretrazi_Fragment fragment = new Pretrazi_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }
   private PotrebniPodaci potrebniPodaci= new PotrebniPodaci();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_pretrazi_, container, false);


        polaziste = view.findViewById(R.id.txtPolaziste);
        odrediste = view.findViewById(R.id.txtOdrediste);
        datum = view.findViewById(R.id.txtDatumPolaska);
        Button dalje=view.findViewById(R.id.btnPretrazi);
        ImageButton nazad=view.findViewById(R.id.btn_Nazad1);

        if(getArguments()!=null)
        {
           potrebniPodaci=(PotrebniPodaci)getArguments().getSerializable("potrebniPodaci");

           polazisteid=potrebniPodaci.polazisteId;
           odredisteid=potrebniPodaci.odredisteId;
           polaziste.setText(potrebniPodaci.polaziste);
           odrediste.setText(potrebniPodaci.odrediste);
           datum.setText(potrebniPodaci.datum);
        }

        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.dodajFragment(getActivity(), R.id.mjesto, Pocetni_Fragment.newInstance());
            }
        });

        dalje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dat=datum.getText().toString();

                if(polazisteid==0 || odredisteid==0 || dat=="")
                {
                    Toast.makeText(getActivity(), "Potrebno unijeti podatke!", Toast.LENGTH_LONG).show();
                }
                else
                {

                    MyUtils.dodajFragment(getActivity(), R.id.mjesto, Lista_Polazci.newInstance(polazisteid, odredisteid, dat,polazisteString,odredisteString));
                }

            }
        });

        datum.setOnClickListener(new View.OnClickListener() {
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

                datum.setText(Integer.toString(dayOfMonth)+"." + Integer.toString(month+1) + "." +  Integer.toString(year));
            }
        };

            polaziste.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OdabirGrada odabirGrada=OdabirGrada.newInstance(pola);
                    odabirGrada.show(getFragmentManager(), "p");
                }
            });

        odrediste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OdabirGrada odabirGrada=OdabirGrada.newInstance(odre);
                odabirGrada.show(getFragmentManager(), "p");
            }
        });




        return view;
    }


String polazisteString;
String odredisteString;

    private int polazisteid;
    private int odredisteid;
    MyRunnable pola=new MyRunnable<PregledGradovaVM.Row>() {
        @Override
        public void run(PregledGradovaVM.Row o) {
            polazisteid=o.id;
            polazisteString=o.naziv;
            polazisteString=o.naziv;
            polaziste.setText(o.naziv);
        }
    };

    MyRunnable odre=new MyRunnable<PregledGradovaVM.Row>() {
        @Override
        public void run(PregledGradovaVM.Row o) {
            odredisteid=o.id;
            odredisteString=o.naziv;
            odrediste.setText(o.naziv);
        }
    };

}
