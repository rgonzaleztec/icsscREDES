package com.TecnologicoDeCostaRica.RedesestimoteIlb;

import android.app.Application;

import com.estimote.sdk.EstimoteSDK;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        EstimoteSDK.initialize(getApplicationContext(), "redesestimote-ilb", "63c23768a10e86b62d4cf87c2b47ca9b");

        // uncomment to enable debug-level logging
        // it's usually only a good idea when troubleshooting issues with the Estimote SDK
//        EstimoteSDK.enableDebugLogging(true);
    }
}
