package com.akim77.hopkinshealth;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class InitialSetupActivity extends AppCompatActivity {

    EditText patientIdEditText;
    RadioButton interventionRb, controlRb;
    Button setupButton;
    String patientId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial_setup);

        patientIdEditText = (EditText) findViewById(R.id.edittext_patient_id);
        interventionRb = (RadioButton) findViewById(R.id.radio_button_intervention_group);
        controlRb = (RadioButton) findViewById(R.id.radio_button_control_group);
        setupButton = (Button) findViewById(R.id.button_finish_setup);

//        Intent myIntent = new Intent(this , .class);
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        PendingIntent pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);

        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientId = patientIdEditText.getText().toString();
                if(TextUtils.isEmpty(patientId)){
                    patientIdEditText.setError("Patient ID cannot be empty");
                } else {
                    if(!interventionRb.isChecked() && !controlRb.isChecked()){
                        Toast.makeText(InitialSetupActivity.this, "Please select patient group.", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sharedPref = InitialSetupActivity.this.getSharedPreferences("patientInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("patientId", patientId);
                        Calendar calendar = Calendar.getInstance();
                        editor.putString("createdYear", calendar.get(Calendar.YEAR) + "");
                        editor.putString("createdMonth", calendar.get(Calendar.MONTH) + "");
                        editor.putString("createdDay", calendar.get(Calendar.DAY_OF_MONTH) + "");

                        Intent i;
                        if (interventionRb.isChecked()) {
                            editor.putString("group", "intervention");
                            i = new Intent(InitialSetupActivity.this, InterventionGroupActivity.class);
                        } else {
                            editor.putString("group", "control");
                            i = new Intent(InitialSetupActivity.this, WeightActivity.class);
                        }
                        setAlarm();
                        setupRebootPersistence();

                        editor.apply();
                        startActivity(i);
                        finish();
                    }
                }
            }
        });
    }

//    public void getNotification(View view){
//        Intent i = new Intent(this, SplashActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), i, 0);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//
//        Notification notification = new Notification.Builder(this)
//                .setSmallIcon(R.drawable.jhu_measurement_corps_logo)
//                .setContentTitle("Take our survey!")
//                .setContentText("It's a reminder" + System.currentTimeMillis())
//                .setContentIntent(pendingIntent)
//                .setAutoCancel(true)
//                .build();
//
//        notificationManager.notify(0, notification);
//    }

    public void setupNotification(){
        Log.d("enterend NOIOFJEOIIJ", "**************");
        Intent i = new Intent(this, NotifyService.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), i, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 , pendingIntent);  //set repeating every 24 hours

    }

    public void setAlarm(){
        int interval = 10000;

        Log.d("entered", "setalarm*******");
        int count = 0;
        Long alertTime1 = new GregorianCalendar().getTimeInMillis() + interval * count++;
        Long alertTime2 = new GregorianCalendar().getTimeInMillis() + interval * count++;
        Long alertTime3 = new GregorianCalendar().getTimeInMillis() + interval * count++;
        Long alertTime4 = new GregorianCalendar().getTimeInMillis() + interval * count++;
        Long alertTime5 = new GregorianCalendar().getTimeInMillis() + interval * count++;

        Intent intent = new Intent(this, AlertReceiver.class);

        AlarmManager alarmManager1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        AlarmManager alarmManager2 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//        AlarmManager alarmManager3 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

//        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime1,
//                PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
//        alarmManager2.set(AlarmManager.RTC_WAKEUP, alertTime2,
//                PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
//        alarmManager3.set(AlarmManager.RTC_WAKEUP, alertTime3,
//                PendingIntent.getBroadcast(this, (int)System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));

        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime1,
                PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime2,
                PendingIntent.getBroadcast(this, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime3,
                PendingIntent.getBroadcast(this, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime4,
                PendingIntent.getBroadcast(this, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime5,
                PendingIntent.getBroadcast(this, 5, intent, PendingIntent.FLAG_UPDATE_CURRENT));

//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTime, 3000, PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public void cancelAlarm(){
        Log.d("entered", "cancelalarm*******");

        Intent intent = new Intent(this, AlertReceiver.class);

        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

//        alarmManager.set(AlarmManager.RTC_WAKEUP, alertTime,
//                PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager.cancel(PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT));

    }

    public void setupRebootPersistence(){
        ComponentName receiver = new ComponentName(this, RebootReceiver.class);
        PackageManager pm = this.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

    }
}
