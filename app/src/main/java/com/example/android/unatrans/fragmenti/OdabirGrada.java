package com.example.android.unatrans.fragmenti;


import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.android.unatrans.R;
import com.example.android.unatrans.data.PregledGradovaVM;
import com.example.android.unatrans.helper.MyApiRequest;
import com.example.android.unatrans.helper.MyRunnable;
import com.example.android.unatrans.helper.MyRunnableBaza;


public class OdabirGrada extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String CALLBACK = "callback";


    // TODO: Rename and change types of parameters
    private String mParam1;

    private SearchView pretraga;
    private ListView lista;
    private MyRunnable<PregledGradovaVM.Row> pregledGradovaVMMyRunnable;


    // TODO: Rename and change types and number of parameters
    public static OdabirGrada newInstance(MyRunnable<PregledGradovaVM.Row> callback) {
        OdabirGrada fragment = new OdabirGrada();
        Bundle args = new Bundle();
        args.putSerializable(CALLBACK, callback);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pregledGradovaVMMyRunnable =(MyRunnable<PregledGradovaVM.Row>) getArguments().getSerializable(CALLBACK);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_odabir_grada, container, false);

        pretraga = view.findViewById(R.id.unos);
        lista = view.findViewById(R.id.listaGradova);

        popuniListuTask();

        pretraga.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                popuniListuTask();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                popuniListuTask();
                return true;
            }
        });
        return view;
    }

    private void popuniListuTask() {

        MyRunnableBaza<PregledGradovaVM> myRunnableBaza=new MyRunnableBaza<PregledGradovaVM>() {
            @Override
            public void Run(PregledGradovaVM pregledGradovaVM) {
                popuniListu(pregledGradovaVM);
            }
        };

        MyApiRequest.get(getActivity(),"Grad/GetAll/"+pretraga.getQuery().toString(),myRunnableBaza);
    }

    private void popuniListu(final PregledGradovaVM gradovi) {

        lista.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return gradovi.rows.size();
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
                    convertView=inflater.inflate(R.layout.stavka_gradovi, parent, false);


                }


                TextView grad=convertView.findViewById(R.id.Grad);
                PregledGradovaVM.Row p=gradovi.rows.get(position);
                grad.setText(p.naziv);



                return convertView;
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PregledGradovaVM.Row p=gradovi.rows.get(position);
               getDialog().dismiss();

                pregledGradovaVMMyRunnable.run(p);

            }
        });


    }

}


