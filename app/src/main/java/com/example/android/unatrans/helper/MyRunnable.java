package com.example.android.unatrans.helper;

import java.io.Serializable;

public interface MyRunnable<T> extends Serializable {

    void run(T t);
}
