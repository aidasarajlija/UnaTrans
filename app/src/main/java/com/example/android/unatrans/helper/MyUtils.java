package com.example.android.unatrans.helper;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class MyUtils {

    public static void dodajFragment(Activity activity, int id, Fragment fragment)
    {
        FragmentTransaction fragmentTransaction=activity.getFragmentManager().beginTransaction();
        fragmentTransaction.replace(id, fragment).commit();

    }


}
