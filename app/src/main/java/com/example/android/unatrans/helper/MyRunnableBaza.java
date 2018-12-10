package com.example.android.unatrans.helper;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * Created by mrmai on 11/7/2018.
 */

public abstract class MyRunnableBaza<T> implements Serializable {

    public abstract void Run(T t);

    public Class <T>getGenericType(){
        Class<T>persistentClass=(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];

        return persistentClass;
    }
}
