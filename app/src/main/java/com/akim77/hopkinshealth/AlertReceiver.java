package com.akim77.hopkinshealth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by anthony on 2/18/18.
 */

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("******", "onReceive");
        createNotification(context, "Hopkins ProHealth ", intent.getStringExtra("msg"), "Alert");
    }

    private void createNotification(Context context, String msgTitle, String msgText, String msgAlert) {
        Log.d("******", "createNOTI");

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, SplashActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.jhmc_noti_icon)
                .setContentTitle(msgTitle)
                .setTicker(msgAlert)
                .setContentText(msgText);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);

        mBuilder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(1, mBuilder.build());
    }


}
