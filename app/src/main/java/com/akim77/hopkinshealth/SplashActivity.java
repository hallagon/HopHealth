package com.akim77.hopkinshealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("patientInfo", Context.MODE_PRIVATE);
        String patientGroup = sharedPreferences.getString("group", "");

        Intent i;
        if(patientGroup.equals("control")){
            i = new Intent(this, WeightActivity.class);
        } else if(patientGroup.equals("intervention")){
            i = new Intent(this, InterventionGroupActivity.class);
        } else {
            i = new Intent(this, InitialSetupActivity.class);
        }
        startActivity(i);
        finish();



    }
}
