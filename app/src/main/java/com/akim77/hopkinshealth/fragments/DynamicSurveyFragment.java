package com.akim77.hopkinshealth.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.akim77.hopkinshealth.R;
import com.akim77.hopkinshealth.SubmissionManager;
import com.akim77.hopkinshealth.SurveyAdapter;
import com.akim77.hopkinshealth.questionModels.HorizontalQuestion;
import com.akim77.hopkinshealth.questionModels.SubmitButton;
import com.akim77.hopkinshealth.questionModels.VerticalQuestion;

import java.util.ArrayList;
import java.util.List;


public class DynamicSurveyFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private List<Object> qfaList = new ArrayList<>();


    public DynamicSurveyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dynamic_survey, container, false);

        addQuestions(qfaList);

//
//        submitButton = (Button) view.findViewById(R.id.submitButton);
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(SubmissionManager.instance.isFormComplete()) {
//
//                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPref.edit();
//                    //saves a key-value set consisting of current time and submission data
//                    editor.putString(System.currentTimeMillis() + "", SubmissionManager.instance.prettyMapToString());
//                    editor.commit();
//
//                    sendEmail();
//
//                }
//                else Toast.makeText(view.getContext(), "Current form submission state: " + SubmissionManager.instance.getMapSize() + " / " + SubmissionManager.instance.QUESTION_COUNT, Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        qfaList.add(submitButton);


        // Inflate the layout for this fragment
        /*
        LinearLayout mSeekLin = (LinearLayout) view.findViewById(R.id.SurveyLinearLayout);
        CustomSeekbar customSeekBar = new CustomSeekbar(view.getContext(), 5, Color.DKGRAY);
        customSeekBar.addSeekBar(mSeekLin);
        */
        recyclerView = (RecyclerView) view.findViewById(R.id.surveyRecyclerView);
        recyclerView.setAdapter(new SurveyAdapter(qfaList));

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
//
//        SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);

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
        VerticalQuestion q1 = new VerticalQuestion("1. In general, would you say your health is:", "Excellent", "Very good", "Good", "Fair", "Poor");
        VerticalQuestion q2 = new VerticalQuestion("2. Compared to one year ago, how would you rate your health in general now?", "Much better now than one year ago", "Somewhat better now than one year ago", "About the same", "Somewhat worse now than one year ago", "Much worse now than one year ago");
        HorizontalQuestion q3 = new HorizontalQuestion("The following items are about activities you might do during a typical day. Does your health now limit you in these activities? If so, how much?", "3. Vigorous activities, such as running, lifting heavy objects, participating in strenuous sports", "Yes, limited a lot", "Yes, limited a little", "No, not limited at all", true);
        HorizontalQuestion q4 = new HorizontalQuestion("", "4. Moderate activities, such as moving a table, pushing a vacuum cleaner, bowling, or playing golf", "", "", "", false);
        HorizontalQuestion q5 = new HorizontalQuestion("", "5. Lifting or carrying groceries", "", "", "", false);
        HorizontalQuestion q6 = new HorizontalQuestion("", "6. Climbing several flights of stairs", "", "", "", false);
        HorizontalQuestion q7 = new HorizontalQuestion("", "7. Climbing one flight of stairs", "", "", "", false);
        HorizontalQuestion q8 = new HorizontalQuestion("", "8. Bending, kneeling, or stooping", "", "", "", false);
        HorizontalQuestion q9 = new HorizontalQuestion("", "9. Walking more than a mile", "", "", "", false);
        HorizontalQuestion q10 = new HorizontalQuestion("", "10. Walking several blocks", "", "", "", false);
        HorizontalQuestion q11 = new HorizontalQuestion("", "11. Walking one block", "", "", "", false);
        HorizontalQuestion q12 = new HorizontalQuestion("", "12. Bathing or dressing yourself", "", "", "", false);
        HorizontalQuestion q13 = new HorizontalQuestion("During the past 4 weeks, have you had any of the following problems with your work or other regular daily activities as a result of your physical health?", "13. Cut down the amount of time you spent on work or other activities", "Yes", "No", true);
        HorizontalQuestion q14 = new HorizontalQuestion("", "14. Accomplished less than you would like", "", "", false);
        HorizontalQuestion q15 = new HorizontalQuestion("", "15. Were limited in the kind of work or other activities", "", "", false);
        HorizontalQuestion q16 = new HorizontalQuestion("", "16. Had difficulty performing the work or other activities (for example, it took extra effort)", "", "", false);

        HorizontalQuestion q17 = new HorizontalQuestion("During the past 4 weeks, have you had any of the following problems with your work or other regular daily activities as a result of any emotional problems (such as feeling depressed or anxious)?", "17. Cut down the amount of time you spent on work or other activities", "Yes", "No", true);
        HorizontalQuestion q18 = new HorizontalQuestion("", "18. Accomplished less than you would like", "Yes", "No", false);
        HorizontalQuestion q19 = new HorizontalQuestion("", "19. Didn't do work or other activities as carefully as usual", "Yes", "No", false);
        VerticalQuestion q20 = new VerticalQuestion("20. During the past 4 weeks, to what extent has your physical health or emotional " +
                "problems interfered with your normal social activities with family, friends, neighbors, or " +
                "groups?", "Not at all", "Slightly", "Moderately", "Quite a bit", "Extremely");
        VerticalQuestion q21 = new VerticalQuestion("21. How much bodily pain have you had during the past 4 weeks?", "1 - None", "2 - Very mild", "3 - Mild", "4 - Moderate", "5 - Severe", "6 - Very severe");
        VerticalQuestion q22 = new VerticalQuestion("22. During the past 4 weeks, how much did pain interfere with your normal work " +
                "(including both work outside the home and housework)?", "Not at all", "A little bit", "Moderately", "Quite a bit", "Extremely");

        HorizontalQuestion q23 = new HorizontalQuestion("These questions are about how you feel and how things have been with you during the past 4 weeks. For each question, please give the one answer that comes closest to the way you have been feeling.\n\nHow much of the time during the past 4 weeks...", "23. Did you feel full of pep?", "All of the time", "Most of the time", "A good bit of the time", "Some of the time", "A little of the time", "None of the time", true);
        HorizontalQuestion q24 = new HorizontalQuestion("", "24. Have you been a very nervous person?", "", "", "", "", "", "", false);
        HorizontalQuestion q25 = new HorizontalQuestion("", "25. Have you felt so down in the dumps that nothing could cheer you up?", "", "", "", "", "", "", false);
        HorizontalQuestion q26 = new HorizontalQuestion("", "26. Have you felt calm and peaceful?", "", "", "", "", "", "", false);
        HorizontalQuestion q27 = new HorizontalQuestion("", "27. Did you have a lot of energy?", "", "", "", "", "", "", false);
        HorizontalQuestion q28 = new HorizontalQuestion("", "28. Have you felt downhearted and blue?", "", "", "", "", "", "", false);
        HorizontalQuestion q29 = new HorizontalQuestion("", "29. Did you feel worn out?", "", "", "", "", "", "", false);
        HorizontalQuestion q30 = new HorizontalQuestion("", "30. Have you been a happy person?", "", "", "", "", "", "", false);
        HorizontalQuestion q31 = new HorizontalQuestion("", "31. Did you feel tired?", "", "", "", "", "", "", false);

        VerticalQuestion q32 = new VerticalQuestion("32. During the past 4 weeks, how much of the time has your physical health or emotional " +
                "problems interfered with your social activities (like visiting with friends, relatives, etc.)?", "All of the time", "Most of the time", "Some of the time", "A little of the time", "None of the time");

        HorizontalQuestion q33 = new HorizontalQuestion("How TRUE or FALSE is each of the following statements for you.", "33. I seem to get sick a little easier than other people", "Definitely true", "Mostly true", "Don't know", "Mostly false", "Definitely false", true);
        HorizontalQuestion q34 = new HorizontalQuestion("", "34. I am as healthy as anybody I know", "", "", "", "", "", false);
        HorizontalQuestion q35 = new HorizontalQuestion("", "35. I expect my health to get worse", "", "", "", "", "", false);
        HorizontalQuestion q36 = new HorizontalQuestion("", "36. My health is excellent", "", "", "", "", "", false);

        SubmitButton submitButton = new SubmitButton();

        list.add(q1);
        list.add(q2);
        list.add(q3);
        list.add(q4);
        list.add(q5);
        list.add(q6);
        list.add(q7);
        list.add(q8);
        list.add(q9);
        list.add(q10);
        list.add(q11);
        list.add(q12);

        list.add(q13);
        list.add(q14);
        list.add(q15);
        list.add(q16);

        list.add(q17);
        list.add(q18);
        list.add(q19);
        list.add(q20);
        list.add(q21);
        list.add(q22);

        list.add(q23);
        list.add(q24);
        list.add(q25);
        list.add(q26);

        list.add(q27);
        list.add(q28);
        list.add(q29);
        list.add(q30);
        list.add(q31);

        list.add(q32);
        list.add(q33);
        list.add(q34);
        list.add(q35);
        list.add(q36);

        list.add(submitButton);


    }


}
