package com.akim77.hopkinshealth;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class ChromeCustomTabLauncherActivity extends AppCompatActivity {

    public static String CHROME_PACKAGE_NAME = "com.android.chrome";
    private Context mContext;
    String url = "https://www.fitbit.com/oauth2/authorize?response_type=token&client_id=22CMWS&redirect_uri=jhpro%3A%2F%2Ffinished&scope=activity%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight&expires_in=604800";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chrome_custom_tab_launcher);
        mContext = this;
    }

    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btnOpenChromeCustomTab:
                launchNativeChrome();
                break;
            default:
                return;
        }
    }

    private void launchChromeCustomTab() {
        Log.d("Clicked", "Login");
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(mContext, CustomTabReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0);

        CustomTabsIntent.Builder customTabsBuilder = new CustomTabsIntent.Builder();
        customTabsBuilder.addMenuItem("Close", pendingIntent);
        CustomTabsIntent customTabsIntent = customTabsBuilder.build();
        customTabsIntent.intent.setPackage(CHROME_PACKAGE_NAME);
        customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        customTabsIntent.launchUrl(mContext, uri);
    }

    private void launchNativeChrome() {
        Log.d("Clicked", "Login");
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }


}
