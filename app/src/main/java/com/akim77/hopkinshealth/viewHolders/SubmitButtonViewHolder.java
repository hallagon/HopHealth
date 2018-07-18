package com.akim77.hopkinshealth.viewHolders;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.akim77.hopkinshealth.OAuthHandler;
import com.akim77.hopkinshealth.R;
import com.akim77.hopkinshealth.SubmissionManager;
import com.akim77.hopkinshealth.WeightActivity;
import com.akim77.hopkinshealth.fragments.DynamicSurveyFragment;

/**
 * Created by anthony on 12/29/17.
 */

public class SubmitButtonViewHolder extends RecyclerView.ViewHolder {

    private Button submitButton;

    public SubmitButtonViewHolder(final View itemView) {
        super(itemView);

        submitButton = (Button) itemView.findViewById(R.id.surveySubmitButton);
        Drawable d = itemView.getContext().getResources().getDrawable(R.drawable.ripple_button);
        submitButton.setBackground(d);



        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();

                if(SubmissionManager.instance.isFormComplete()) {
                    SharedPreferences sharedPref = context.getSharedPreferences("submissions", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    //saves a key-value set consisting of current time and submission data
                    editor.putString(System.currentTimeMillis() + "", SubmissionManager.instance.prettyMapToString());
                    editor.apply();
//                    sendEmail(context);

                    SharedPreferences patientSharedPref = context.getSharedPreferences("patientInfo", Context.MODE_PRIVATE);
                    String patientID = patientSharedPref.getString("patientId", "-99");
                    final String dataUrl = buildSubmitUrl(patientID);
                    Log.d("Sending data as1", dataUrl);

                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try  {
                                Log.d("Sending data as2", dataUrl);
                                OAuthHandler.instance.makePlainHttpCall(dataUrl);
                                //Your code goes here
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    thread.start();

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                    builder1.setMessage("Survey has been sent. Thank you.");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                    SubmissionManager.instance.clearEntries();
                } else {
                    Toast.makeText(context, "Please complete question " + SubmissionManager.instance.getNextUpQuestion() + ".", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                    builder1.setMessage("Please complete question " + SubmissionManager.instance.getNextUpQuestion() + ".");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Okay",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                }

            }
        });
    }

    protected String buildSubmitUrl(String patientID){
        String url = "http://jhprohealth.herokuapp.com/polls/submit_survey_compact/";

        String surveyResults = SubmissionManager.instance.getSubmissionAnswerSequence();
        url += patientID + "/" + surveyResults;
        Log.d("Sending data as" , url);
        return url;
    }



    protected void sendEmail(Context context) {
        Log.i("Send email", "");

        String[] TO = {"anthonysunghoonkim@gmail.com"};
        String[] CC = {};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");


        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
//        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Survey results");
        emailIntent.putExtra(Intent.EXTRA_TEXT, SubmissionManager.instance.prettyMapToString());

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            Log.i("Finished sending email.", "");
            Toast.makeText(context, "Sent mail.", Toast.LENGTH_SHORT).show();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context,
                    "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }

}
