package com.akim77.hopkinshealth;

import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

/**
 * Created by Anthony Kim on 12/7/2017.
 */

public class SubmissionManager {
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
}
