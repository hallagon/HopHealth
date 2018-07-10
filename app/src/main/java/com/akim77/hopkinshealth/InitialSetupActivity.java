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
        int intervalInDays = 1;

        int millisecondsInSecond = 1000;
        int secondsInMinute = 60;
        int minutesInHour = 60;
        int hoursInDay = 24;

        long interval = intervalInDays * (millisecondsInSecond * secondsInMinute * minutesInHour * hoursInDay);

        Log.d("entered", "setalarm*******");
        int count = 0;
        long curTimeMillis = new GregorianCalendar().getTimeInMillis();
        //1 month post
        Long alertTime1 = curTimeMillis + interval * 28;
        Long alertTime2 = curTimeMillis + interval * 29;
        Long alertTime3 = curTimeMillis + interval * 30;
        Long alertTime4 = curTimeMillis + interval * 31;
        Long alertTime5 = curTimeMillis + interval * 32;
        //2 months post
        Long alertTime6 = curTimeMillis + interval * 58;
        Long alertTime7 = curTimeMillis + interval * 59;
        Long alertTime8 = curTimeMillis + interval * 60;
        Long alertTime9 = curTimeMillis + interval * 61;
        Long alertTime10 = curTimeMillis + interval * 62;

        //3 months post
        Long alertTime11 = curTimeMillis + interval * 88;
        Long alertTime12 = curTimeMillis + interval * 89;
        Long alertTime13 = curTimeMillis + interval * 90;
        Long alertTime14 = curTimeMillis + interval * 91;
        Long alertTime15 = curTimeMillis + interval * 92;

        Intent intent1 = new Intent(this, AlertReceiver.class);
        intent1.putExtra("msg", "You have an upcoming survey in 3 days.");
        Intent intent2 = new Intent(this, AlertReceiver.class);
        intent2.putExtra("msg", "You have an upcoming survey tomorrow.");
        Intent intent3 = new Intent(this, AlertReceiver.class);
        intent3.putExtra("msg", "You have a survey to take today.");
        Intent intent4 = new Intent(this, AlertReceiver.class);
        intent4.putExtra("msg", "You have a missed survey due yesterday.");
        Intent intent5 = new Intent(this, AlertReceiver.class);
        intent5.putExtra("msg", "You have a missed survey 3 days ago.");


        Intent intent6 = new Intent(this, AlertReceiver.class);
        intent1.putExtra("msg", "You have an upcoming survey in 3 days.");
        Intent intent7 = new Intent(this, AlertReceiver.class);
        intent2.putExtra("msg", "You have an upcoming survey tomorrow.");
        Intent intent8 = new Intent(this, AlertReceiver.class);
        intent3.putExtra("msg", "You have a survey to take today.");
        Intent intent9 = new Intent(this, AlertReceiver.class);
        intent4.putExtra("msg", "You have a missed survey due yesterday.");
        Intent intent10 = new Intent(this, AlertReceiver.class);
        intent5.putExtra("msg", "You have a missed survey 3 days ago.");

        Intent intent11 = new Intent(this, AlertReceiver.class);
        intent1.putExtra("msg", "You have an upcoming survey in 3 days.");
        Intent intent12 = new Intent(this, AlertReceiver.class);
        intent2.putExtra("msg", "You have an upcoming survey tomorrow.");
        Intent intent13 = new Intent(this, AlertReceiver.class);
        intent3.putExtra("msg", "You have a survey to take today.");
        Intent intent14 = new Intent(this, AlertReceiver.class);
        intent4.putExtra("msg", "You have a missed survey due yesterday.");
        Intent intent15 = new Intent(this, AlertReceiver.class);
        intent5.putExtra("msg", "You have a missed survey 3 days ago.");


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
                PendingIntent.getBroadcast(this, 1, intent1, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime2,
                PendingIntent.getBroadcast(this, 2, intent2, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime3,
                PendingIntent.getBroadcast(this, 3, intent3, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime4,
                PendingIntent.getBroadcast(this, 4, intent4, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime5,
                PendingIntent.getBroadcast(this, 5, intent5, PendingIntent.FLAG_UPDATE_CURRENT));

        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime6,
                PendingIntent.getBroadcast(this, 6, intent6, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime7,
                PendingIntent.getBroadcast(this, 7, intent7, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime8,
                PendingIntent.getBroadcast(this, 8, intent8, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime9,
                PendingIntent.getBroadcast(this, 9, intent9, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime10,
                PendingIntent.getBroadcast(this, 10, intent10, PendingIntent.FLAG_UPDATE_CURRENT));

        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime11,
                PendingIntent.getBroadcast(this, 11, intent11, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime12,
                PendingIntent.getBroadcast(this, 12, intent12, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime13,
                PendingIntent.getBroadcast(this, 13, intent13, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime14,
                PendingIntent.getBroadcast(this, 14, intent14, PendingIntent.FLAG_UPDATE_CURRENT));
        alarmManager1.set(AlarmManager.RTC_WAKEUP, alertTime15,
                PendingIntent.getBroadcast(this, 15, intent15, PendingIntent.FLAG_UPDATE_CURRENT));

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
