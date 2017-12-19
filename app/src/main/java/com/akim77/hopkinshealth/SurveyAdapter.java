package com.akim77.hopkinshealth;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.akim77.hopkinshealth.questionModels.HorizontalQuestion;
import com.akim77.hopkinshealth.questionModels.VerticalQuestion;
import com.akim77.hopkinshealth.viewHolders.HorizontalQuestionViewHolder;
import com.akim77.hopkinshealth.viewHolders.VerticalQuestionViewHolder;

import java.util.List;

import static android.view.View.GONE;

/**
 * Created by Anthony Kim on 12/7/2017.
 */

public class SurveyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // The items to display in your RecyclerView
    private List<Object> items;

    private final int VERTICAL_QUESTION = 0, HORIZONTAL_QUESTION = 1;

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
        if (items.get(position) instanceof VerticalQuestion) {
            return VERTICAL_QUESTION;
        } else if (items.get(position) instanceof HorizontalQuestion) {
            return HORIZONTAL_QUESTION;
        }
        return -1;    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (viewType) {
            case VERTICAL_QUESTION:
                View v1 = inflater.inflate(R.layout.question_vertical, viewGroup, false);
                viewHolder = new VerticalQuestionViewHolder(v1);
                break;

            case HORIZONTAL_QUESTION:
                View v2 = inflater.inflate(R.layout.question_horizontal, viewGroup, false);
                viewHolder = new HorizontalQuestionViewHolder(v2);
                break;
            default:
                //View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
                //viewHolder = new RecyclerViewSimpleTextViewHolder(v);
                View v = inflater.inflate(R.layout.question_vertical, viewGroup, false);
                viewHolder = new VerticalQuestionViewHolder(v);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        viewHolder.setIsRecyclable(false);
        switch (viewHolder.getItemViewType()) {
            case VERTICAL_QUESTION:
                VerticalQuestionViewHolder vh1 = (VerticalQuestionViewHolder) viewHolder;
                VerticalQuestion vQuestion = (VerticalQuestion) items.get(position);
                vh1.getRadioGroup().setTag(position);
                if(vQuestion.getMultipleChoiceType() == 5) configureViewHolderForVerticalFiveAnswerQuestion(vh1, position);
                else if(vQuestion.getMultipleChoiceType() == 6) configureViewHolderForVerticalSixAnswerQuestion(vh1, position);
                break;

            case HORIZONTAL_QUESTION:
                HorizontalQuestionViewHolder vh2 = (HorizontalQuestionViewHolder) viewHolder;
                HorizontalQuestion hQuestion = (HorizontalQuestion) items.get(position);
                vh2.getRadioGroup().setTag(position);
                switch(hQuestion.getMultipleChoiceType()){
                    case 2: configureViewHolderForHorizontalTwoAnswerQuestion(vh2, position, hQuestion.getMultipleChoiceType()); break;
                    case 3: configureViewHolderForHorizontalTwoAnswerQuestion(vh2, position, hQuestion.getMultipleChoiceType()); break;
                    case 4: configureViewHolderForHorizontalTwoAnswerQuestion(vh2, position, hQuestion.getMultipleChoiceType()); break;
                    case 5: configureViewHolderForHorizontalTwoAnswerQuestion(vh2, position, hQuestion.getMultipleChoiceType()); break;
                    case 6: configureViewHolderForHorizontalTwoAnswerQuestion(vh2, position, hQuestion.getMultipleChoiceType()); break;
                    default: break;
                }
                break;

            default:


                break;
        }
    }

    private void configureViewHolderForVerticalFiveAnswerQuestion(VerticalQuestionViewHolder vh1, int position) {
        Log.d("ITEMS", "items size" + items.size() + " and items position " + position);
        VerticalQuestion question = (VerticalQuestion) items.get(position);
        if (question != null) {
            vh1.getQuestion().setText(question.getQuestion());
            vh1.getRb1().setText(question.getOptionOne());
            vh1.getRb2().setText(question.getOptionTwo());
            vh1.getRb3().setText(question.getOptionThree());
            vh1.getRb4().setText(question.getOptionFour());
            vh1.getRb5().setText(question.getOptionFive());
            vh1.getRb6().setVisibility(GONE);
        }
    }

    private void configureViewHolderForVerticalSixAnswerQuestion(VerticalQuestionViewHolder vh1, int position) {
        Log.d("ITEMS", "items size" + items.size() + " and items position " + position);
        VerticalQuestion question = (VerticalQuestion) items.get(position);
        if (question != null) {
            vh1.getQuestion().setText(question.getQuestion());
            vh1.getRb1().setText(question.getOptionOne());
            vh1.getRb2().setText(question.getOptionTwo());
            vh1.getRb3().setText(question.getOptionThree());
            vh1.getRb4().setText(question.getOptionFour());
            vh1.getRb5().setText(question.getOptionFive());
            vh1.getRb6().setText(question.getOptionSix());
        }
    }

    private void configureViewHolderForHorizontalTwoAnswerQuestion(HorizontalQuestionViewHolder vh, int position, int choiceNumber) {
        Log.d("ITEMS", "items size" + items.size() + " and items position " + position);

        HorizontalQuestion question = (HorizontalQuestion) items.get(position);
        if(question.isContextSettingQuestion()){
            vh.getQuestionContext().setText(question.getQuestionContext());
            vh.getChoiceText1().setText(question.getOptionOne());
            vh.getChoiceText2().setText(question.getOptionTwo());
        } else {
            vh.getQuestionContext().setVisibility(GONE);
            vh.getChoiceText1().setVisibility(GONE);
            vh.getChoiceText2().setVisibility(GONE);
        }
        vh.getQuestion().setText(question.getQuestion());


        if(choiceNumber > 2){
            vh.getChoiceText3().setText(question.getOptionThree());
        } else {
            vh.getChoiceText3().setVisibility(GONE);
            vh.getRb3().setVisibility(GONE);
        }

        if(choiceNumber > 3){
            vh.getChoiceText4().setText(question.getOptionFour());
        } else {
            vh.getChoiceText4().setVisibility(GONE);
            vh.getRb4().setVisibility(GONE);
        }

        if(choiceNumber > 4){
            vh.getChoiceText5().setText(question.getOptionFive());
        } else {
            vh.getChoiceText5().setVisibility(GONE);
            vh.getRb5().setVisibility(GONE);
        }

        if(choiceNumber > 5){
            vh.getChoiceText6().setText(question.getOptionSix());
        } else {
            vh.getChoiceText6().setVisibility(GONE);
            vh.getRb6().setVisibility(GONE);
        }
    }
}