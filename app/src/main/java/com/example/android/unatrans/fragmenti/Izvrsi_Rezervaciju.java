package com.example.android.unatrans.fragmenti;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.unatrans.R;
import com.example.android.unatrans.data.PotrebniPodaci;

import com.example.android.unatrans.data.PregledCijenaKartaVM;
import com.example.android.unatrans.data.PregledKartiVM;
import com.example.android.unatrans.data.PregledLinijaVM;
import com.example.android.unatrans.data.RezervacijaAddVM;
import com.example.android.unatrans.helper.MyApiRequest;
import com.example.android.unatrans.helper.MyRunnableBaza;
import com.example.android.unatrans.helper.MySession;
import com.example.android.unatrans.helper.MyUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Izvrsi_Rezervaciju#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Izvrsi_Rezervaciju extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String REZ = "rezervacija";


    // TODO: Rename and change types of parameters
    private PregledLinijaVM.Rows odabranaLinija;
    private Spinner karte;
    private TextView gradovi;
    private TextView datum;
    private TextView vrijeme;

    private PotrebniPodaci potrebniPodaci= new PotrebniPodaci();


    // TODO: Rename and change types and number of parameters
    public static Izvrsi_Rezervaciju newInstance(PregledLinijaVM.Rows linija, PotrebniPodaci podaci) {
        Izvrsi_Rezervaciju fragment = new Izvrsi_Rezervaciju();
        Bundle args = new Bundle();
        args.putSerializable(REZ, linija);
        args.putSerializable("potrebniPodaci", podaci);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            odabranaLinija =(PregledLinijaVM.Rows) getArguments().getSerializable(REZ);
            potrebniPodaci=(PotrebniPodaci)getArguments().getSerializable("potrebniPodaci");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_izvrsi__rezervaciju, container, false);

        gradovi = view.findViewById(R.id.ispisRute);
        datum = view.findViewById(R.id.ispisDatuma);
        vrijeme = view.findViewById(R.id.ispisVrijeme);
        karte = view.findViewById(R.id.karte);
        Button rezervisi=view.findViewById(R.id.btnRezervacija);
        ImageButton nazad=view.findViewById(R.id.btn_Nazad3);


        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.dodajFragment(getActivity(), R.id.mjesto, Lista_Polazci.newInstance(potrebniPodaci.polazisteId,potrebniPodaci.odredisteId, potrebniPodaci.datum,potrebniPodaci.polaziste,potrebniPodaci.odrediste));

            }
        });

        gradovi.setText(odabranaLinija.polaziste + " - " + odabranaLinija.odrediste);
        vrijeme.setText(odabranaLinija.vrijemePolaska + " - " + odabranaLinija.vrijemeDolaska);
        datum.setText(odabranaLinija.datumDolaska );



        popuniPodatkeTask();
        final RezervacijaAddVM rezervacija=new RezervacijaAddVM();

        rezervisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preuzmiCijenuKarte();
                }
        });

        return view;
    }
    float cijena;
    PregledCijenaKartaVM cijenaKartaVM;


    private void preuzmiCijenuKarte() {
        int LinijaId=odabranaLinija.id;//redID
        int KartaId=SveKarte.rows.get(karte.getSelectedItemPosition()).id;//kartaId


        MyRunnableBaza<PregledCijenaKartaVM> nekiCallback = new MyRunnableBaza<PregledCijenaKartaVM>() {
            @Override
            public void Run(PregledCijenaKartaVM pregledCijenaKartaVM) {
                    cijenaKartaVM=pregledCijenaKartaVM;
                    cijena=pregledCijenaKartaVM.cijena;

                final AlertDialog.Builder adb=new AlertDialog.Builder(getActivity());
                adb.setTitle("Da li želite spremiti rezervaciju?");

                adb.setMessage("Cijena karte iznosi : " + cijena + " KM!");
                adb.setNegativeButton("Ne", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.setPositiveButton("Da", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        RezervacijaAddVM novaRezervacija= new RezervacijaAddVM();
                        novaRezervacija.kartaCijenaId=cijenaKartaVM.id;
                        novaRezervacija.korisnikRezervacijaId=MySession.getKorisnik(getActivity()).id;
                        MyRunnableBaza<RezervacijaAddVM> nekiCallback= new MyRunnableBaza<RezervacijaAddVM>() {
                            @Override
                            public void Run(RezervacijaAddVM rezervacijaAddVM) {
                                Toast.makeText(getActivity(), "Rezervacija uspješno izvršena!", Toast.LENGTH_LONG).show();
                                MyUtils.dodajFragment(getActivity(), R.id.mjesto, Pocetni_Fragment.newInstance());
                            }
                        };

                        MyApiRequest.post(getActivity(),"Rezervacija/Add",novaRezervacija,nekiCallback);


                    }
                });

                adb.setIcon(android.R.drawable.ic_dialog_alert);
                adb.show();
            }
        };

        MyApiRequest.get(getActivity(), "Karta/GetCijenaKarte/"+LinijaId+"/"+KartaId, nekiCallback);


    }


    private PregledKartiVM SveKarte;

    private void popuniPodatkeTask(){

        MyRunnableBaza<PregledKartiVM> myRunnableBaza= new MyRunnableBaza<PregledKartiVM>() {
            @Override
            public void Run(PregledKartiVM karta) {

                SveKarte=karta;
                popuniPodatke(karta);
            }
        };

        MyApiRequest.get(getActivity(),"Karta/GetAll",myRunnableBaza);
    }

    private void popuniPodatke(PregledKartiVM pregledKartiVM) {

        PregledKartiVM pregled=pregledKartiVM;

        ArrayAdapter<String> dataAdapter= new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getKarteString(pregled));//jedna stavka spinnera


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        karte.setAdapter(dataAdapter);


    }

    private List<String> getKarteString(PregledKartiVM karte){
        List<String> nazivi= new ArrayList<>();

        //nazivi.add("Odaberi kartu: ");
        for (PregledKartiVM.Rows o:karte.rows){
            nazivi.add(o.naziv);
        }

        return nazivi;
    }

}
