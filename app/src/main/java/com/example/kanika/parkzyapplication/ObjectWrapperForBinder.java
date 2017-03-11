package com.example.kanika.parkzyapplication;

import android.os.Binder;

/**
 * Created by Kanika on 04-03-2017.
 */

public class ObjectWrapperForBinder extends Binder {

    private final Object mData;

    public ObjectWrapperForBinder(Object data) {
        mData = data;
    }

    public Object getData() {
        return mData;
    }
}
