package com.akim77.hopkinshealth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.akim77.hopkinshealth.fragments.DynamicSurveyFragment;
import com.akim77.hopkinshealth.fragments.FeedbackFragment;
import com.akim77.hopkinshealth.fragments.FitbitDataFragment;
import com.akim77.hopkinshealth.fragments.FitbitWebViewFragment;

public class InterventionGroupActivity extends AppCompatActivity {

    private int currentFragment = 0; //0 if uninitialized, 1 if survey, 2 if feedback, etc
    private Context context;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_survey:
                    if(currentFragment != 1) {
                        fragmentTransaction.replace(R.id.rootLayout, new DynamicSurveyFragment());
                        fragmentTransaction.commit();
                        currentFragment = 1;
                    }
                    else Toast.makeText(InterventionGroupActivity.this, "already in it!", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_feedback:

                    if(currentFragment != 2) {
/*
                        if(!OAuthHandler.instance.needLogin()) {
                            fragmentTransaction.replace(R.id.rootLayout, new FitbitWebViewFragment());
                            currentFragment = 2;
                        } else {
                            fragmentTransaction.replace(R.id.rootLayout, new FitbitDataFragment());
                            currentFragment = 3;
                        }
*/
                        fragmentTransaction.replace(R.id.rootLayout, new FeedbackFragment());
                        currentFragment = 2;
                        fragmentTransaction.commit();
                    }
                    else Toast.makeText(InterventionGroupActivity.this, "already in it!", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intervention);

        context = this;

/*
        Uri inboundUri = getIntent().getData();
        String stringUri = "";
        if(inboundUri == null){
            stringUri = "#not logged in yet";
        } else {
            stringUri = inboundUri.toString();
            currentFragment = 3;
        }


        OAuthHandler.instance.setupFromInboundURI(stringUri);
        Toast.makeText(this, stringUri, Toast.LENGTH_SHORT).show();
*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        /*
        Log.d("needlogin", OAuthHandler.instance.needLogin() + "");
        Log.d("" + OAuthHandler.instance.expireTimeInMillis, System.currentTimeMillis() + "");
        Log.d("needlogin", OAuthHandler.instance.needLogin() + "");
        */
/*
        if(currentFragment == 3 && !OAuthHandler.instance.needLogin()){
            fragmentTransaction.replace(R.id.rootLayout, new FitbitDataFragment());
            currentFragment = 3;
        } else {
            fragmentTransaction.replace(R.id.rootLayout, new DynamicSurveyFragment());
            currentFragment = 1;
        }
*/
        if(currentFragment == 0){
            fragmentTransaction.replace(R.id.rootLayout, new DynamicSurveyFragment());
            currentFragment = 1;
        } else {
            fragmentTransaction.replace(R.id.rootLayout, new FeedbackFragment());
            currentFragment = 2;
        }

        fragmentTransaction.commit();



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        Intent i = new Intent(this, UserListActivity.class);
//        startActivity(i);

    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, SubmissionManager.instance.prettyMapToString(), Toast.LENGTH_SHORT).show();
    }




}
