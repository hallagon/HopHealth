package com.akim77.hopkinshealth;

/**
 * Created by Anthony Kim on 12/5/2017.
 */

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class CustomSeekbar {

    int maxCount, textColor;
    Context mContext;
    LinearLayout mSeekLin;
    SeekBar mSeekBar;

    public CustomSeekbar(Context context, int maxCount, int textColor) {
        this.mContext = context;
        this.maxCount = maxCount;
        this.textColor = textColor;
    }

    public void addSeekBar(LinearLayout parent) {

        if (parent instanceof LinearLayout) {

            parent.setOrientation(LinearLayout.VERTICAL);
            mSeekBar = new SeekBar(mContext);
            mSeekBar.setMax(maxCount - 1);

            // Add LinearLayout for labels below SeekBar
            mSeekLin = new LinearLayout(mContext);
            mSeekLin.setOrientation(LinearLayout.HORIZONTAL);
            mSeekLin.setPadding(10, 0, 10, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(35, 10, 35, 0);
            mSeekLin.setLayoutParams(params);

            addLabelsBelowSeekBar();
            int temp = 2;
            parent.addView(mSeekBar, 1);
            parent.addView(mSeekLin, 3);

        } else {

            Log.e("CustomSeekBar", " Parent is not a LinearLayout");

        }

    }

    private void addLabelsBelowSeekBar() {
        for (int count = 0; count < maxCount; count++) {
            TextView textView = new TextView(mContext);
            textView.setText(String.valueOf(count + 1));
            textView.setTextColor(textColor);
            textView.setGravity(Gravity.LEFT);
            mSeekLin.addView(textView);
            textView.setLayoutParams((count == maxCount - 1) ? getLayoutParams(0.0f) : getLayoutParams(1.0f));
        }
    }

    LinearLayout.LayoutParams getLayoutParams(float weight) {
        return new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, weight);
    }

}