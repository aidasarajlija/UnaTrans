package com.example.android.unatrans.fragmenti;



import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.unatrans.R;
import com.example.android.unatrans.data.PotrebniPodaci;

import com.example.android.unatrans.data.PregledGradovaVM;
import com.example.android.unatrans.data.PregledLinijaVM;
import com.example.android.unatrans.helper.MyApiRequest;
import com.example.android.unatrans.helper.MyRunnableBaza;
import com.example.android.unatrans.helper.MyUtils;

import java.util.Date;


public class Lista_Polazci extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";


    // TODO: Rename and change types of parameters
    private int mParam1;
    private int mParam2;
    private String mParam3;
    private ListView listaPolazci;

    String polaziste;
    String odrediste;
    private TextView ispis;

    // TODO: Rename and change types and number of parameters
    public static Lista_Polazci newInstance(int param1, int param2, String param3, String polazisteString, String odredisteString) {
        Lista_Polazci fragment = new Lista_Polazci();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString("polaz", polazisteString);
        args.putString("odred", odredisteString);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
            mParam3= getArguments().getString(ARG_PARAM3);
            polaziste=getArguments().getString("polaz");
            odrediste=getArguments().getString("odred");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_lista__polazci, container, false);
        listaPolazci = view.findViewById(R.id.listaLinija);
        ispis = view.findViewById(R.id.ispis);
        ImageButton nazad=view.findViewById(R.id.btn_Nazad2);



        nazad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PotrebniPodaci podaci= new PotrebniPodaci();
                podaci.datum=mParam3;
                podaci.odredisteId=mParam2;
                podaci.polazisteId=mParam1;
                podaci.polaziste=polaziste;
                podaci.odrediste=odrediste;
                Bundle args= new Bundle();
                args.putSerializable("potrebniPodaci",podaci);
                Pretrazi_Fragment fragment= new Pretrazi_Fragment();
                fragment.setArguments(args);

                MyUtils.dodajFragment(getActivity(), R.id.mjesto, fragment);
            }
        });
        popuniListuTask();

        return view;
    }

    private void popuniListuTask() {

        MyRunnableBaza<PregledLinijaVM> myRunnableBaza=new MyRunnableBaza<PregledLinijaVM>() {
            @Override
            public void Run(PregledLinijaVM pregledLinijaVM) {
                if(pregledLinijaVM.rows.size()==0){
                   ispis.setText("Nema dostupnih linija!");
                }else{
                popuniListu(pregledLinijaVM);}
            }
        };

        MyApiRequest.get(getActivity(),"Rezervacija/GetAll/"+mParam1+"/"+mParam2+"/"+mParam3,myRunnableBaza);
    }

    private void popuniListu(final PregledLinijaVM pregledLinijaVM) {

        listaPolazci.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return pregledLinijaVM.rows.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if(convertView==null)
                {
                    LayoutInflater inflater=(LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView=inflater.inflate(R.layout.stavka_polazak, parent, false);


                }


                TextView vrijeme=convertView.findViewById(R.id.lista);
                PregledLinijaVM.Rows p=pregledLinijaVM.rows.get(position);


                vrijeme.setText(p.vrijemeDolaska + " - " + p.vrijemePolaska);



                return convertView;
            }
        });
        listaPolazci.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PregledLinijaVM.Rows p=pregledLinijaVM.rows.get(position);
                PotrebniPodaci podaci= new PotrebniPodaci();
                podaci.datum=mParam3;
                podaci.odredisteId=mParam2;
                podaci.polazisteId=mParam1;
                podaci.polaziste=polaziste;
                podaci.odrediste=odrediste;

                MyUtils.dodajFragment(getActivity(), R.id.mjesto, Izvrsi_Rezervaciju.newInstance(p,podaci));


            }
        });

    }

}
