package com.akim77.hopkinshealth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by anthony on 1/10/18.
 */

public class CustomSnapHelper extends LinearSnapHelper {

    @Override
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int velocityX, int velocityY) {
        Log.d("snapping", SubmissionManager.instance.getNextUpQuestion() + "");
        return SubmissionManager.instance.getNextUpQuestion();
    }
}
