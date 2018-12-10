package com.example.android.unatrans.helper;

public class MyApiResult
{
    public String errorMessage="";
    public String value;
    public int resultCode=0;
    public boolean isException=false;


    private MyApiResult(){

    }

    public static MyApiResult Error(int exceptionCode, String exceptionMessage){
        MyApiResult x= new MyApiResult();
        x.isException=true;
        x.resultCode=exceptionCode;
        x.errorMessage=exceptionMessage;
        return x;
    }

    public static MyApiResult OK(String value){
        MyApiResult x= new MyApiResult();
        x.value=value;
        x.isException=false;
        x.resultCode=200;
        x.errorMessage="";
        return x;
    }




}

