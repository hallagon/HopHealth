package com.akim77.hopkinshealth.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akim77.hopkinshealth.CombinedActiveHoursChartActivity;
import com.akim77.hopkinshealth.CombinedStepsChartActivity;
import com.akim77.hopkinshealth.CombinedWeightChartActivity;
import com.akim77.hopkinshealth.InterventionGroupActivity;
import com.akim77.hopkinshealth.OAuthHandler;
import com.akim77.hopkinshealth.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FeedbackFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {

    private SharedPreferences patientSharedPref;
    private String patientID;
    private Button stepsButton, activityButton, weightButton;

    TextView txtJson;
    ProgressDialog pd;
    ArrayList<WeeklyFeedbackData> feedbackList = new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FeedbackFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        patientID = patientSharedPref.getString("patientId", "-99");
        final String dataUrl = "http://jhprohealth.herokuapp.com/polls/checkin/" + patientID + "/1/";
        Log.d("Sending data as1", dataUrl);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try  {
                    Log.d("Sending data as2", dataUrl);
                    OAuthHandler.instance.makePlainHttpCall(dataUrl);
                    //Your code goes here
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        final String feedbackDataURL = "http://jhprohealth.herokuapp.com/polls/get_feedback/" + patientID + "/";
        new JsonTask().execute("");



        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        patientSharedPref = view.getContext().getSharedPreferences("patientInfo", Context.MODE_PRIVATE);

        txtJson = (TextView) view.findViewById(R.id.feedbacktest);

        stepsButton = (Button) view.findViewById(R.id.stepsButton);
        stepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CombinedStepsChartActivity.class);
                setFeedbackExtra(i);
                startActivity(i);
            }
        });
        activityButton = (Button) view.findViewById(R.id.activityButton);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CombinedActiveHoursChartActivity.class);
                setFeedbackExtra(i);
                startActivity(i);
            }
        });
        weightButton = (Button) view.findViewById(R.id.weightButton);
        weightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), CombinedWeightChartActivity.class);
                setFeedbackExtra(i);
                startActivity(i);
            }
        });

        return view;
    }

    private void setFeedbackExtra(Intent intent){
        intent.putExtra("feedbackList", feedbackList);
        Log.d("fromfeedback_weights", feedbackList.toString());
    }

    public class WeeklyFeedbackData implements Serializable{
        public int id, user_id, week, total_active_min;
        public float avg_weight, avg_steps, height;

        public WeeklyFeedbackData(int id, int user_id, int week, int total_active_min, float avg_weight, float avg_steps, float height) {
            this.id = id;
            this.user_id = user_id;
            this.week = week;
            this.total_active_min = total_active_min;
            this.avg_weight = avg_weight;
            this.avg_steps = avg_steps;
            this.height = height;
        }

        @Override
        public String toString() {
            return "WeeklyFeedbackData{" +
                    "user_id=" + user_id +
                    ", week=" + week +
                    ", total_active_min=" + total_active_min +
                    ", avg_weight=" + avg_weight +
                    ", avg_steps=" + avg_steps +
                    ", height=" + height +
                    '}';
        }
    }

    private void feedbackJsonToList(String json){
        Log.d("feedbackjson", json);
        json = json.substring(json.indexOf("["), json.lastIndexOf("]") + 1);
        Log.d("feedbackjson", json);

        Gson g = new Gson();

//        WeeklyFeedbackData data = g.fromJson(json, WeeklyFeedbackData.class);
//        feedbackList.add(data);
        WeeklyFeedbackData[] mcArray = g.fromJson(json, WeeklyFeedbackData[].class);
        feedbackList = new ArrayList<>(Arrays.asList(mcArray));


        Log.d("feedbackjson", feedbackList.toString());
    }

    private class JsonTask extends AsyncTask<String, String, String> {
        final String feedbackDataURL = "http://jhprohealth.herokuapp.com/polls/get_feedback/" + patientID + "/";

        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(getContext());
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(feedbackDataURL);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }
                feedbackJsonToList(buffer.toString());
                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }
//            TODO: remove settext for prod
//            txtJson.setText(result);
        }
    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            Toast.makeText(context, "Feedback", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
