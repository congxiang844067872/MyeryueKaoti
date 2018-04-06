package com.bwie.myeryuekaoti.zidingyi;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by admin on 2018/3/31.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
