package com.akim77.hopkinshealth;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.akim77.hopkinshealth.examples.UserListActivity;
import com.akim77.hopkinshealth.examples.UserListsActivity;
import com.akim77.hopkinshealth.examples.UsersFragment;
import com.akim77.hopkinshealth.fragments.DynamicSurveyFragment;
import com.akim77.hopkinshealth.fragments.FeedbackFragment;
import com.akim77.hopkinshealth.fragments.SurveyFragment;

public class MainActivity extends AppCompatActivity {

    private int currentFragment = 0; //0 if uninitialized, 1 if survey, 2 if feedback, etc

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (item.getItemId()) {
                case R.id.navigation_survey:
                    if(currentFragment != 1) {
                        fragmentTransaction.replace(android.R.id.content, new DynamicSurveyFragment());
                        fragmentTransaction.commit();
                        currentFragment = 1;
                    }
                    else Toast.makeText(MainActivity.this, "already in it!", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_feedback:
                    if(currentFragment != 2) {
                        fragmentTransaction.replace(android.R.id.content, new FeedbackFragment());
                        fragmentTransaction.commit();
                        currentFragment = 2;
                    }
                    else Toast.makeText(MainActivity.this, "already in it!", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        if(currentFragment != 1) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            DynamicSurveyFragment dynamicSurveyFragment = new DynamicSurveyFragment();

            fragmentTransaction.replace(android.R.id.content, dynamicSurveyFragment);
            fragmentTransaction.commit();
            currentFragment = 1;
        }



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
