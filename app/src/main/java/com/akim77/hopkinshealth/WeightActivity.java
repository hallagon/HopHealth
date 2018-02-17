package com.akim77.hopkinshealth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class WeightActivity extends AppCompatActivity {

    final double KG_TO_LBS = 2.20462;

    EditText weightEditText;
    Button submitButton;
    String weightString;
    double weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        weightEditText = (EditText) findViewById(R.id.weight_enter_edittext);
        submitButton = (Button) findViewById(R.id.button_submit_weight);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weightString = weightEditText.getText().toString();
                if(TextUtils.isEmpty(weightString)) {
                    weightEditText.setError("Patient ID cannot be empty");
                } else {
                    weight = Double.parseDouble(weightString);
                    SubmissionManager.instance.updateEntry(-1, weight);
                    Intent i = new Intent(WeightActivity.this, ControlGroupActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private double changeKgToLbs(double kgs){
        return kgs * KG_TO_LBS;
    }
}
