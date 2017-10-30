package com.mjurinic.argus.argus;


import android.app.Application;

import timber.log.Timber;

public class ArgusApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
