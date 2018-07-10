package com.akim77.hopkinshealth;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Anthony Kim on 12/7/2017.
 * Index is 1-based.
 */

public class SubmissionManager extends Application{
    public final int QUESTION_COUNT = 36;
    private int nextUp = 1;

    public static SubmissionManager instance = new SubmissionManager();

    private Map<Integer, Double> submissionMap = new HashMap<>();

    private RecyclerView rv;
    private RecyclerView.SmoothScroller scroller;
    private LinearLayoutManager llm;

    public void updateEntryAndScroll(int questionIndex, double answerIndex){
        submissionMap.put(questionIndex, answerIndex);
        //rv.smoothScrollToPosition(getNextUpQuestion());
        scrollToPosition(getNextUpQuestion());
    }

    public void updateEntry(int questionIndex, double answerIndex){
        submissionMap.put(questionIndex, answerIndex);
    }


    public void clearEntries(){
        submissionMap.clear();
        nextUp = 1;
        rv.getAdapter().notifyDataSetChanged();
    }

    public int getMapSize(){
        return submissionMap.size();
    }

    public boolean isFormComplete(){
        return (getNextUpQuestion() > QUESTION_COUNT);
    }

    public String getMapToString(){
        return submissionMap.toString();
    }

    public String prettyMapToString(){
        String result = "";
        Set<Integer> keySet = submissionMap.keySet();

        int[] keyArray = new int[keySet.size()];
        int count = 0;
        for(int i : keySet){
            keyArray[count++] = i;
        }

        Arrays.sort(keyArray);


        /*
        for(int i = 0; i < 10; i++){
            result = result + "Q." + (i + 1) + " / A." + (submissionMap.get(i) + 1) + '\n';
        }
        */
        for (int i : keyArray) {
            result = result + "Q." + (i) + " / A." + (submissionMap.get(i) + (i == -1 ? 0 : 1)) + '\n';
        }
        return result;
    }

    public String getSubmissionAnswerSequence(){
        String result = "";
        Set<Integer> keySet = submissionMap.keySet();

        int[] keyArray = new int[keySet.size()];
        int count = 0;
        for(int i : keySet){
            keyArray[count++] = i;
        }

        Arrays.sort(keyArray);


        for (int i : keyArray) {
            Double d = (submissionMap.get(i) + (i == -1 ? 0 : 1));
            result += d.intValue();
        }
        return result;
    }

    public Map<Integer, Double> getSubmissionMap() {
        return submissionMap;
    }

    public int getSelectedRadioButton(int position){
        if(submissionMap.containsKey(position)) return submissionMap.get(position).intValue();
        else return -1;
    }

    public int getNextUpQuestion(){
        //Log.d("entered nextup", prettyMapToString());

        while(nextUp <= QUESTION_COUNT && submissionMap.containsKey(nextUp)){
            nextUp++;
            Log.d("nextup", nextUp + "");
        }
        return nextUp;
    }

    public void scrollToPosition(int position){
        scroller.setTargetPosition(position);
        llm.startSmoothScroll(scroller);
    }

    public void setRecyclerView(RecyclerView rv){
        this.rv = rv;
    }

    public void setScroller(RecyclerView.SmoothScroller scroller) {
        this.scroller = scroller;
    }

    public void setLlm(LinearLayoutManager llm) {
        this.llm = llm;
    }

    //    protected void sendEmail() {
//        Log.i("Send email", "");
//
//        String[] TO = {"anthonysunghoonkim@gmail.com"};
//        String[] CC = {};
//        Intent emailIntent = new Intent(Intent.ACTION_SEND);
//        emailIntent.setData(Uri.parse("mailto:"));
//        emailIntent.setType("text/plain");
//
//
//        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
////        emailIntent.putExtra(Intent.EXTRA_CC, CC);
//        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Survey results");
//        emailIntent.putExtra(Intent.EXTRA_TEXT, SubmissionManager.instance.prettyMapToString());
//
//        try {
//            MyApplication.getContext().startActivity(Intent.createChooser(emailIntent, "Send mail..."));
//            Log.i("Finished sending email.", "");
//            Toast.makeText(MyApplication.getContext(), "Sent mail.", Toast.LENGTH_SHORT).show();
//        } catch (android.content.ActivityNotFoundException ex) {
//            Toast.makeText(MyApplication.getContext(),
//                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
//        }
//    }
}
