package com.eo.cars.utils;

import android.app.Application;

public class AppController extends Application {

    private static AppController mInstance;


    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FontsOverride.setDefaultFont(this, "SERIF", Constants.font_ProductSans);

        mInstance = this;
    }
}
