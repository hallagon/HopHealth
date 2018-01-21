package com.akim77.hopkinshealth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

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

        setupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patientId = patientIdEditText.getText().toString();
                if(TextUtils.isEmpty(patientId)){
                    patientIdEditText.setError("Patient ID cannot be empty");
                } else {
                    if (interventionRb.isChecked()) {
                        SharedPreferences sharedPref = InitialSetupActivity.this.getSharedPreferences("patientInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("patientId", patientId);
                        editor.putString("group", "intervention");
                        editor.apply();

                        Intent i = new Intent(InitialSetupActivity.this, MainActivity.class);
                        startActivity(i);
                    } else if (controlRb.isChecked()) {
                        SharedPreferences sharedPref = InitialSetupActivity.this.getSharedPreferences("patientInfo", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("patientId", patientId);
                        editor.putString("group", "intervention");
                        editor.apply();

                        Intent i = new Intent(InitialSetupActivity.this, MainActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(InitialSetupActivity.this, "Please select patient group.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
