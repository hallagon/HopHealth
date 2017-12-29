package com.akim77.hopkinshealth;

import android.app.Application;
import android.content.Context;

/**
 * Created by anthony on 12/29/17.
 */
public class MyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}