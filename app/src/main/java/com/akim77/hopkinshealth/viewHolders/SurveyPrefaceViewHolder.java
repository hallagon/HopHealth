package com.akim77.hopkinshealth.viewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akim77.hopkinshealth.R;

/**
 * Created by anthony on 1/10/18.
 */

public class SurveyPrefaceViewHolder extends RecyclerView.ViewHolder {

    private TextView surveyName, surveyTitle, surveyInstruction;

    public SurveyPrefaceViewHolder(View itemView) {
        super(itemView);

        surveyName = (TextView) itemView.findViewById(R.id.surveyName);
        surveyTitle = (TextView) itemView.findViewById(R.id.surveyTitle);
        surveyInstruction = (TextView) itemView.findViewById(R.id.surveyInstruction);

    }
}
