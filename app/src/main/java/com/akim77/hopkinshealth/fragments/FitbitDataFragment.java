package com.akim77.hopkinshealth.fragments;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.akim77.hopkinshealth.OAuthHandler;
import com.akim77.hopkinshealth.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FitbitDataFragment extends Fragment {

    private View view;

    public FitbitDataFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(OAuthHandler.instance.needLogin() || true) {
            view = inflater.inflate(R.layout.fragment_fitbit_data, container, false);
        } else {
            view = inflater.inflate(R.layout.fragment_fitbit_data, container, false);
        }
        TextView txt2 = (TextView) view.findViewById(R.id.fitbitDataTextview);
        txt2.setText("Loading...");

        new LongOperation().execute("");

        return view;
    }

    private void setupLogin(){


        Uri inboundUri = getActivity().getIntent().getData();


        String stringUri = (inboundUri == null) ? "not logged in yet" : inboundUri.toString();

        OAuthHandler.instance.inboundUri = stringUri;

        Toast.makeText(getActivity(), stringUri, Toast.LENGTH_SHORT).show();

    }

    private class LongOperation extends AsyncTask<String, Void, String> {

        String bigDataChunk = "";

        String userData = "";
        String userWeightData = "";
        String userActivityData = "";
        String userHeartRateData = "";
        JSONObject userJsonObject;
        Map<String, String> myMap = new HashMap<>();

        @Override
        protected String doInBackground(String... params) {
            Log.d("longop", "fei");


            try {
                userData = OAuthHandler.instance.getUserName();
                userJsonObject = new JSONObject(userData);
                myMap.put("age", userJsonObject.getJSONObject("user").getString("age"));
                myMap.put("firstName", userJsonObject.getJSONObject("user").getString("firstName"));
                myMap.put("lastName", userJsonObject.getJSONObject("user").getString("lastName"));

                userWeightData = OAuthHandler.instance.getWeightLog();
                userActivityData = OAuthHandler.instance.getActivities();
                userHeartRateData = OAuthHandler.instance.getHeartRates();

            } catch (Exception e) {
                e.printStackTrace();
                Thread.interrupted();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
//            TextView txt = (TextView) view.findViewById(R.id.fitbitDataTextview);
//            txt.setText(myMap.toString());

            bigDataChunk = "USER DATA: \n\n" + myMap.toString() + "\n\n\n" + "WEIGHT LOG: \n\n" + userWeightData + "\n\n\n" + "ACTIVITIES: \n\n" + userActivityData + "\n\n\n" + "HEART RATES: \n\n" + userHeartRateData;
            TextView txt2 = (TextView) view.findViewById(R.id.fitbitDataTextview);
            txt2.setText(bigDataChunk);

        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }











}
