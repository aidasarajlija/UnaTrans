package com.example.android.unatrans.fragmenti;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.unatrans.LoginActivity;
import com.example.android.unatrans.MainActivity;
import com.example.android.unatrans.R;
import com.example.android.unatrans.RezervacijaActivity;
import com.example.android.unatrans.helper.MySession;
import com.example.android.unatrans.helper.MyUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pocetni_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pocetni_Fragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters




    // TODO: Rename and change types and number of parameters
    public static Pocetni_Fragment newInstance() {
        Pocetni_Fragment fragment = new Pocetni_Fragment();

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
        View view=inflater.inflate(R.layout.fragment_pocetni_, container, false);

        Button red=view.findViewById(R.id.btnRedVoznje);
        Button zakup=view.findViewById(R.id.btnZakupAutobusa);
        TextView odjava=view.findViewById(R.id.btnOdjava);

        odjava.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MySession.setKorisnik(getActivity(), null);
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });



        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.dodajFragment(getActivity(), R.id.mjesto, Pretrazi_Fragment.newInstance());
            }
        });

        zakup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.dodajFragment(getActivity(), R.id.mjesto, Zakup_Fragment.newInstance());

            }
        });


        return view;
    }





}
