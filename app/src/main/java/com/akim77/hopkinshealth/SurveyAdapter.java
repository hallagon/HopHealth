package com.akim77.hopkinshealth;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akim77.hopkinshealth.questionModels.QuestionWithFiveAnswers;
import com.akim77.hopkinshealth.viewHolders.FiveQuestionViewHolder;

import java.util.List;

/**
 * Created by Anthony Kim on 12/7/2017.
 */

public class SurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;

    private final int QUESTION_WITH_FIVE_ANSWERS = 0, IMAGE = 1;

    // Provide a suitable constructor (depends on the kind of dataset)
    public SurveyAdapter(List<Object> items) {
        this.items = items;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return this.items.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof QuestionWithFiveAnswers) {
            return QUESTION_WITH_FIVE_ANSWERS;
        } else if (items.get(position) instanceof String) {
            return IMAGE;
        }
        return -1;    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case QUESTION_WITH_FIVE_ANSWERS:
                View v1 = inflater.inflate(R.layout.question_five_radiobuttons, viewGroup, false);
                viewHolder = new FiveQuestionViewHolder(v1);
                break;
            default:
                //View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                //viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                View v = inflater.inflate(R.layout.question_five_radiobuttons, viewGroup, false);
                viewHolder = new FiveQuestionViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case QUESTION_WITH_FIVE_ANSWERS:
                FiveQuestionViewHolder vh1 = (FiveQuestionViewHolder) viewHolder;
                vh1.getRadioGroup().setTag(position);
                configureViewHolderForQFA(vh1, position);
                break;
            default:
                FiveQuestionViewHolder vhd = (FiveQuestionViewHolder) viewHolder;
                configureViewHolderForQFA(vhd, position);
                break;
        }
    }

    private void configureViewHolderForQFA(FiveQuestionViewHolder vh1, int position) {
        Log.d("ITEMS", "items size" + items.size() + " and items position " + position);
        QuestionWithFiveAnswers qfa = (QuestionWithFiveAnswers) items.get(position);
        if (qfa != null) {
            vh1.getQuestion().setText(qfa.getQuestion());
            vh1.getRb1().setText(qfa.getOptionOne());
            vh1.getRb2().setText(qfa.getOptionTwo());
            vh1.getRb3().setText(qfa.getOptionThree());
            vh1.getRb4().setText(qfa.getOptionFour());
            vh1.getRb5().setText(qfa.getOptionFive());
        }
    }
}