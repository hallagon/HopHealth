package com.akim77.hopkinshealth;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 * Created by anthony on 2/18/18.
 */

public class RebootReceiver extends BroadcastReceiver {
    private final int INTERVAL = 5000;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            cancelPreviousAlarm(context);
            setAlarm(context);
        }
    }

    public void setAlarm(Context context){
        Log.d("entered", "setalarm*******");
        int count = 0;
        Long alertTime1 = new GregorianCalendar().getTimeInMillis() + INTERVAL * count++;
        Long alertTime2 = new GregorianCalendar().getTimeInMillis() + INTERVAL * count++;
        Long alertTime3 = new GregorianCalendar().getTimeInMillis() + INTERVAL * count++;
        Long alertTime4 = new GregorianCalendar().getTimeInMillis() + INTERVAL * count++;
        Long alertTime5 = new GregorianCalendar().getTimeInMillis() + INTERVAL * count++;

        Intent intent = new Intent(context, AlertReceiver.class);

        AlarmManager alarmManager1 = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime1,
                PendingIntent.getBroadcast(context, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime2,
                PendingIntent.getBroadcast(context, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime3,
                PendingIntent.getBroadcast(context, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime4,
                PendingIntent.getBroadcast(context, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime5,
                PendingIntent.getBroadcast(context, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, 3000, PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public void cancelPreviousAlarm(Context context){
        Log.d("entered", "cancelalarm*******");

        Intent intent = new Intent(context, AlertReceiver.class);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        for(int i = 1; i < 6; i++) {
            alarmManager.cancel(PendingIntent.getBroadcast(context, i, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        }

    }
}
