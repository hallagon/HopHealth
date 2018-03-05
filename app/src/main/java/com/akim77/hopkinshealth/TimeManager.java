package com.akim77.hopkinshealth;

import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * Created by anthony on 2/17/18.
 */

public class TimeManager {

    private static TimeManager instance = new TimeManager();

    private int alertInitiatedFlag = 0;

    private Calendar calendar = Calendar.getInstance();

    public void setupAlert(){

    }

    public TimeManager getInstance() {
        return instance;
    }

}
