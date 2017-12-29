package com.akim77.hopkinshealth.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.akim77.hopkinshealth.R;
import com.akim77.hopkinshealth.SubmissionManager;

/**
 * Created by Anthony Kim on 12/17/2017.
 */

public class HorizontalQuestionViewHolder extends RecyclerView.ViewHolder {

    private TextView questionContext;
    private TextView question;
    private TextView choiceText1, choiceText2, choiceText3, choiceText4, choiceText5, choiceText6;
    private RadioGroup radioGroup;
    private RadioButton rb1, rb2, rb3, rb4, rb5, rb6;
    private LinearLayout choiceLabels;

    public HorizontalQuestionViewHolder(View itemView) {
        super(itemView);

        questionContext = (TextView) itemView.findViewById(R.id.horizontalQuestionContext);
        question = (TextView) itemView.findViewById(R.id.horizontalChoicesQuestion);
        choiceText1 = (TextView) itemView.findViewById(R.id.horizontalChoicesTextOne);
        choiceText2 = (TextView) itemView.findViewById(R.id.horizontalChoicesTextTwo);
        choiceText3 = (TextView) itemView.findViewById(R.id.horizontalChoicesTextThree);
        choiceText4 = (TextView) itemView.findViewById(R.id.horizontalChoicesTextFour);
        choiceText5 = (TextView) itemView.findViewById(R.id.horizontalChoicesTextFive);
        choiceText6 = (TextView) itemView.findViewById(R.id.horizontalChoicesTextSix);
        radioGroup = (RadioGroup) itemView.findViewById(R.id.horizontalQuestionRadioGroup);
        rb1 = (RadioButton) itemView.findViewById(R.id.horizontalQuestionOptionOne);
        rb2 = (RadioButton) itemView.findViewById(R.id.horizontalQuestionOptionTwo);
        rb3 = (RadioButton) itemView.findViewById(R.id.horizontalQuestionOptionThree);
        rb4 = (RadioButton) itemView.findViewById(R.id.horizontalQuestionOptionFour);
        rb5 = (RadioButton) itemView.findViewById(R.id.horizontalQuestionOptionFive);
        rb6 = (RadioButton) itemView.findViewById(R.id.horizontalQuestionOptionSix);
        choiceLabels = (LinearLayout) itemView.findViewById(R.id.horizontalChoicesTextLayout);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionManager.instance.updateEntry((Integer) radioGroup.getTag(), 0);
            }
        });

        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionManager.instance.updateEntry((Integer) radioGroup.getTag(), 1);
            }
        });

        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionManager.instance.updateEntry((Integer) radioGroup.getTag(), 2);
            }
        });

        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionManager.instance.updateEntry((Integer) radioGroup.getTag(), 3);
            }
        });

        rb5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionManager.instance.updateEntry((Integer) radioGroup.getTag(), 4);
            }
        });

        rb6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubmissionManager.instance.updateEntry((Integer) radioGroup.getTag(), 5);
            }
        });

    }


    public TextView getQuestion() {
        return question;
    }

    public void setQuestion(TextView question) {
        this.question = question;
    }

    public RadioGroup getRadioGroup() {
        return radioGroup;
    }

    public void setRadioGroup(RadioGroup radioGroup) {
        this.radioGroup = radioGroup;
    }

    public RadioButton getRb1() {
        return rb1;
    }

    public void setRb1(RadioButton rb1) {
        this.rb1 = rb1;
    }

    public RadioButton getRb2() {
        return rb2;
    }

    public void setRb2(RadioButton rb2) {
        this.rb2 = rb2;
    }

    public RadioButton getRb3() {
        return rb3;
    }

    public void setRb3(RadioButton rb3) {
        this.rb3 = rb3;
    }

    public RadioButton getRb4() {
        return rb4;
    }

    public void setRb4(RadioButton rb4) {
        this.rb4 = rb4;
    }

    public RadioButton getRb5() {
        return rb5;
    }

    public void setRb5(RadioButton rb5) {
        this.rb5 = rb5;
    }

    public RadioButton getRb6() {
        return rb6;
    }

    public void setRb6(RadioButton rb6) {
        this.rb6 = rb6;
    }

    public TextView getQuestionContext() {
        return questionContext;
    }

    public void setQuestionContext(TextView questionContext) {
        this.questionContext = questionContext;
    }

    public TextView getChoiceText1() {
        return choiceText1;
    }

    public void setChoiceText1(TextView choiceText1) {
        this.choiceText1 = choiceText1;
    }

    public TextView getChoiceText2() {
        return choiceText2;
    }

    public void setChoiceText2(TextView choiceText2) {
        this.choiceText2 = choiceText2;
    }

    public TextView getChoiceText3() {
        return choiceText3;
    }

    public void setChoiceText3(TextView choiceText3) {
        this.choiceText3 = choiceText3;
    }

    public TextView getChoiceText4() {
        return choiceText4;
    }

    public void setChoiceText4(TextView choiceText4) {
        this.choiceText4 = choiceText4;
    }

    public TextView getChoiceText5() {
        return choiceText5;
    }

    public void setChoiceText5(TextView choiceText5) {
        this.choiceText5 = choiceText5;
    }

    public TextView getChoiceText6() {
        return choiceText6;
    }

    public void setChoiceText6(TextView choiceText6) {
        this.choiceText6 = choiceText6;
    }

    public LinearLayout getChoiceLabels() {
        return choiceLabels;
    }

    public void setChoiceLabels(LinearLayout choiceLabels) {
        this.choiceLabels = choiceLabels;
    }
}
