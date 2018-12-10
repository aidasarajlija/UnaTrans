package com.example.android.unatrans.data;

import java.io.Serializable;
import java.util.List;

public class PregledLinijaVM implements Serializable
{

    public class Rows implements Serializable
    {
        public int id;
        public String vrijemePolaska;
        public String vrijemeDolaska;
        public String datumDolaska;
        public String polaziste;
        public String odrediste;


    }

    public List<Rows> rows;

}