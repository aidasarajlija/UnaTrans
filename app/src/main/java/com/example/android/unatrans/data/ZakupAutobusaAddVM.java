package com.example.android.unatrans.data;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

public class ZakupAutobusaAddVM implements Serializable {

    public int zakupGradId;
    public int zakupKorisnikId;

    public String imePrezimeFirma;
    public String jMBG;
    public String telefon;
    public String email;
    public String rutaPutovanja;
    public int brojPutnika;
    public String adresaPolaska;
    public String  datumPolaska;
    public String datumDolaska;
    public String vrijemePolaska;
    public String vrijemeDolaska;
}
