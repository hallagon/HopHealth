package com.akim77.hopkinshealth.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.akim77.hopkinshealth.R;
import com.akim77.hopkinshealth.RecyclerAdapter;
import com.akim77.hopkinshealth.SubmissionManager;
import com.akim77.hopkinshealth.SurveyAdapter;
import com.akim77.hopkinshealth.questionModels.QuestionWithFiveAnswers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SurveyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class SurveyFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private FloatingActionButton mailFab;

    private List<Object> qfaList = new ArrayList<>();


    public SurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_survey, container, false);

        addQuestions(qfaList);
        mailFab = (FloatingActionButton) view.findViewById(R.id.mailFab);
        mailFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SubmissionManager.instance.isFormComplete()) {

                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //saves a key-value set consisting of current time and submission data
                    editor.putString(System.currentTimeMillis() + "", SubmissionManager.instance.prettyMapToString());
                    editor.commit();

                    sendEmail();

                }
                else Toast.makeText(view.getContext(), "Current form submission state: " + SubmissionManager.instance.getMapSize() + "/ 5", Toast.LENGTH_SHORT).show();
            }
        });


        // Inflate the layout for this fragment
        /*
        LinearLayout mSeekLin = (LinearLayout) view.findViewById(R.id.SurveyLinearLayout);
        CustomSeekbar customSeekBar = new CustomSeekbar(view.getContext(), 5, Color.DKGRAY);
        customSeekBar.addSeekBar(mSeekLin);
        */
        recyclerView = (RecyclerView) view.findViewById(R.id.surveyRecyclerView);
        //recyclerView.setAdapter(RecyclerAdapter.newInstance(view.getContext()));
        recyclerView.setAdapter(new SurveyAdapter(qfaList));

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
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
            Toast.makeText(context, "Survey", Toast.LENGTH_SHORT).show();
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

    private void addQuestions(List<Object> list){
        QuestionWithFiveAnswers q1 = new QuestionWithFiveAnswers("1. In general, would you say your health is:", "Excellent", "Very good", "Good", "Fair", "Poor");
        QuestionWithFiveAnswers q2 = new QuestionWithFiveAnswers("2. Compared to one year ago, how would you rate your health in general now?", "Much better now than one year ago", "Somewhat better now than one year ago", "About the same", "Somewhat worse now than one year ago", "Much worse now than one year ago");
        QuestionWithFiveAnswers q20 = new QuestionWithFiveAnswers("20. During the past 4 weeks, to what extent has your physical health or emotional " +
                "problems interfered with your normal social activities with family, friends, neighbors, or " +
                "groups?", "Not at all", "Slightly", "Moderately", "Quite a bit", "Extremely");
        QuestionWithFiveAnswers q22 = new QuestionWithFiveAnswers("22. During the past 4 weeks, how much did pain interfere with your normal work\n" +
                "(including both work outside the home and housework)?", "Not at all", "A little bit", "Moderately", "Quite a bit", "Extremely");
        QuestionWithFiveAnswers q32 = new QuestionWithFiveAnswers("32. During the past 4 weeks, how much of the time has your physical health or emotional\n" +
                "problems interfered with your social activities (like visiting with friends, relatives, etc.)?", "All of the time", "Most of the time", "Some of the time", "A little of the time", "None of the time");
        list.add(q1);
        list.add(q2);
        list.add(q20);
        list.add(q22);
        list.add(q32);


    }

    protected void sendEmail() {
        Log.i("Send email", "");

        String[] TO = {"anthonysunghoonkim@gmail.com"};
        String[] CC = {};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Survey results");
        emailIntent.putExtra(Intent.EXTRA_TEXT, SubmissionManager.instance.prettyMapToString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            getActivity().finish();
            Log.i("Finished sending email.", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getActivity(),
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }


}
