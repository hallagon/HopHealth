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
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.akim77.hopkinshealth.CustomSnapHelper;
import com.akim77.hopkinshealth.R;
import com.akim77.hopkinshealth.SubmissionManager;
import com.akim77.hopkinshealth.SurveyAdapter;
import com.akim77.hopkinshealth.questionModels.HorizontalQuestion;
import com.akim77.hopkinshealth.questionModels.SubmitButton;
import com.akim77.hopkinshealth.questionModels.SurveyPreface;
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

        // Inflate the layout for this fragment
        /*
        LinearLayout mSeekLin = (LinearLayout) view.findViewById(R.id.SurveyLinearLayout);
        CustomSeekbar customSeekBar = new CustomSeekbar(view.getContext(), 5, Color.DKGRAY);
        customSeekBar.addSeekBar(mSeekLin);
        */
        recyclerView = (RecyclerView) view.findViewById(R.id.surveyRecyclerView);
        recyclerView.setAdapter(new SurveyAdapter(qfaList));

        LinearLayoutManager llm = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(llm);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.smoothScrollToPosition(SubmissionManager.instance.getNextUpQuestion());

        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(view.getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        SubmissionManager.instance.setRecyclerView(recyclerView);
        SubmissionManager.instance.setScroller(smoothScroller);
        SubmissionManager.instance.setLlm(llm);

//        CustomSnapHelper customSnapHelper = new CustomSnapHelper();
//        customSnapHelper.attachToRecyclerView(recyclerView);

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

        SurveyPreface preface = new SurveyPreface();

        VerticalQuestion q1 = new VerticalQuestion("1. In general, would you say your health is:", "Excellent", "Very good", "Good", "Fair", "Poor");
        VerticalQuestion q2 = new VerticalQuestion("2. <b>Compared to one year ago</b>, how would you rate your health in general <b>now</b>?", "Much better now than one year ago", "Somewhat better now than one year ago", "About the same", "Somewhat worse now than one year ago", "Much worse now than one year ago");
        HorizontalQuestion q3 = new HorizontalQuestion("The following items are about activities you might do during a typical day. Does <b>your health now limit you</b> in these activities? If so, how much?", "3. <b>Vigorous activities</b>, such as running, lifting heavy objects, participating in strenuous sports", "Yes, limited a lot", "Yes, limited a little", "No, not limited at all", true);
        HorizontalQuestion q4 = new HorizontalQuestion("", "4. <b>Moderate activities</b>, such as moving a table, pushing a vacuum cleaner, bowling, or playing golf", "", "", "", false);
        HorizontalQuestion q5 = new HorizontalQuestion("", "5. Lifting or carrying groceries", "", "", "", false);
        HorizontalQuestion q6 = new HorizontalQuestion("", "6. Climbing <b>several</b> flights of stairs", "", "", "", false);
        HorizontalQuestion q7 = new HorizontalQuestion("", "7. Climbing <b>one</b> flight of stairs", "", "", "", false);
        HorizontalQuestion q8 = new HorizontalQuestion("", "8. Bending, kneeling, or stooping", "", "", "", false);
        HorizontalQuestion q9 = new HorizontalQuestion("", "9. Walking <b>more than a mile</b>", "", "", "", false);
        HorizontalQuestion q10 = new HorizontalQuestion("", "10. Walking <b>several blocks</b>", "", "", "", false);
        HorizontalQuestion q11 = new HorizontalQuestion("", "11. Walking <b>one block</b>", "", "", "", false);
        HorizontalQuestion q12 = new HorizontalQuestion("", "12. Bathing or dressing yourself", "", "", "", false);
        HorizontalQuestion q13 = new HorizontalQuestion("During the <b>past 4 weeks</b>, have you had any of the following problems with your work or other regular daily activities <b>as a result of your physical health</b>?", "13. Cut down the <b>amount of time</b> you spent on work or other activities", "Yes", "No", true);
        HorizontalQuestion q14 = new HorizontalQuestion("", "14. <b>Accomplished less</b> than you would like", "", "", false);
        HorizontalQuestion q15 = new HorizontalQuestion("", "15. Were limited in the <b>kind</b> of work or other activities", "", "", false);
        HorizontalQuestion q16 = new HorizontalQuestion("", "16. Had <b>difficulty</b> performing the work or other activities (for example, it took extra effort)", "", "", false);

        HorizontalQuestion q17 = new HorizontalQuestion("During the <b>past 4 weeks</b>, have you had any of the following problems with your work or other regular daily activities <b>as a result of any emotional problems</b> (such as feeling depressed or anxious)?", "17. Cut down the <b>amount of time</b> you spent on work or other activities", "Yes", "No", true);
        HorizontalQuestion q18 = new HorizontalQuestion("", "18. <b>Accomplished less</b> than you would like", "Yes", "No", false);
        HorizontalQuestion q19 = new HorizontalQuestion("", "19. Didn't do work or other activities as <b>carefully</b> as usual", "Yes", "No", false);
        VerticalQuestion q20 = new VerticalQuestion("20. During the <b>past 4 weeks</b>, to what extent has your physical health or emotional " +
                "problems interfered with your normal social activities with family, friends, neighbors, or " +
                "groups?", "Not at all", "Slightly", "Moderately", "Quite a bit", "Extremely");
        VerticalQuestion q21 = new VerticalQuestion("21. How much <b>bodily</b> pain have you had during the <b>past 4 weeks</b>?", "1 - None", "2 - Very mild", "3 - Mild", "4 - Moderate", "5 - Severe", "6 - Very severe");
        VerticalQuestion q22 = new VerticalQuestion("22. During the <b>past 4 weeks</b>, how much did <b>pain</b> interfere with your normal work " +
                "(including both work outside the home and housework)?", "Not at all", "A little bit", "Moderately", "Quite a bit", "Extremely");

        HorizontalQuestion q23 = new HorizontalQuestion("These questions are about how you feel and how things have been with you during the <b>past 4 weeks</b>. For each question, please give the one answer that comes closest to the way you have been.<br><br>How much of the time during the <b>past 4 weeks...</b>", "23. Did you feel full of pep?", "All of the time", "Most of the time", "A good bit of the time", "Some of the time", "A little of the time", "None of the time", true);
        HorizontalQuestion q24 = new HorizontalQuestion("", "24. Have you been a very nervous person?", "", "", "", "", "", "", false);
        HorizontalQuestion q25 = new HorizontalQuestion("", "25. Have you felt so down in the dumps that nothing could cheer you up?", "", "", "", "", "", "", false);
        HorizontalQuestion q26 = new HorizontalQuestion("", "26. Have you felt calm and peaceful?", "", "", "", "", "", "", false);
        HorizontalQuestion q27 = new HorizontalQuestion("", "27. Did you have a lot of energy?", "", "", "", "", "", "", false);
        HorizontalQuestion q28 = new HorizontalQuestion("", "28. Have you felt downhearted and blue?", "", "", "", "", "", "", false);
        HorizontalQuestion q29 = new HorizontalQuestion("", "29. Did you feel worn out?", "", "", "", "", "", "", false);
        HorizontalQuestion q30 = new HorizontalQuestion("", "30. Have you been a happy person?", "", "", "", "", "", "", false);
        HorizontalQuestion q31 = new HorizontalQuestion("", "31. Did you feel tired?", "", "", "", "", "", "", false);

        VerticalQuestion q32 = new VerticalQuestion("32. During the <b>past 4 weeks</b>, how much of the time has <b>your physical health or emotional problems</b> interfered with your social activities (like visiting with friends, relatives, etc.)?", "All of the time", "Most of the time", "Some of the time", "A little of the time", "None of the time");

        HorizontalQuestion q33 = new HorizontalQuestion("How TRUE or FALSE is <b>each</b> of the following statements for you.", "33. I seem to get sick a little easier than other people", "Definitely true", "Mostly true", "Don't know", "Mostly false", "Definitely false", true);
        HorizontalQuestion q34 = new HorizontalQuestion("", "34. I am as healthy as anybody I know", "", "", "", "", "", false);
        HorizontalQuestion q35 = new HorizontalQuestion("", "35. I expect my health to get worse", "", "", "", "", "", false);
        HorizontalQuestion q36 = new HorizontalQuestion("", "36. My health is excellent", "", "", "", "", "", false);

        SubmitButton submitButton = new SubmitButton();

        list.add(preface);

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
