package com.akim77.hopkinshealth;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Anthony Kim on 12/7/2017.
 */

public class SubmissionManager extends Application{
    public final int QUESTION_COUNT = 36;

    public static SubmissionManager instance = new SubmissionManager();

    private Map<Integer, Integer> submissionMap = new HashMap<>();

    public void updateEntry(int questionIndex, int answerIndex){
        submissionMap.put(questionIndex, answerIndex);
    }

    public int getMapSize(){
        return submissionMap.size();
    }

    public boolean isFormComplete(){
        return (submissionMap.size() == QUESTION_COUNT);
    }

//    public void submitSurvey() {
//        if(instance.isFormComplete()) {
//            SharedPreferences sharedPref = MyApplication.getContext().getSharedPreferences("submissions", Context.MODE_PRIVATE);
//            SharedPreferences.Editor editor = sharedPref.edit();
//            //saves a key-value set consisting of current time and submission data
//            editor.putString(System.currentTimeMillis() + "", SubmissionManager.instance.prettyMapToString());
//            editor.commit();
//
//            sendEmail();
//        } else {
//            Toast.makeText(MyApplication.getContext(), "Current form submission state: " + instance.getMapSize() + " / " + instance.QUESTION_COUNT, Toast.LENGTH_SHORT).show();
//        }
//    }

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
            result = result + "Q." + (i + 1) + " / A." + (submissionMap.get(i) + 1) + '\n';
        }
        return result;
    }

    public Map<Integer, Integer> getSubmissionMap() {
        return submissionMap;
    }

    public int getSelectedRadioButton(int position){
        if(submissionMap.containsKey(position)) return submissionMap.get(position);
        else return -1;
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
