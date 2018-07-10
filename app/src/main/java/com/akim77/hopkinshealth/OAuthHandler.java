package com.akim77.hopkinshealth;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by anthony on 1/29/18.
 */

public class OAuthHandler {

    public static OAuthHandler instance = new OAuthHandler();
    public boolean hasTriggered = false;

    public String inboundUri = "#default";
    public String token = "";
    public String userId = "";
    public long expireTimeInMillis = 0L;

    public void setupFromInboundURI(String uri){
        inboundUri = uri;
        String access_token = "access_token=";
        String user_id = "&user_id=";
        String expires_in = "&expires_in=";
        String scope = "&scope=";

        if(inboundUri.charAt(0) != '#') {
            token = uri.substring(inboundUri.indexOf(access_token) + access_token.length(), inboundUri.indexOf(user_id));
            userId = uri.substring(inboundUri.indexOf(user_id) + user_id.length(), inboundUri.indexOf(scope));
            expireTimeInMillis = System.currentTimeMillis() + Long.parseLong(uri.substring(inboundUri.indexOf(expires_in) + expires_in.length()));
        }
    }

    public boolean needLogin(){
        return (System.currentTimeMillis() >= expireTimeInMillis);
    }

    public void refreshToken(){


    }

    public String getToken() {
        if(needLogin()){


        }


        if(!inboundUri.equals("default")) {
            String access_token = "access_token=";
            String user_id = "&user_id";
            token = inboundUri.substring(inboundUri.indexOf(access_token) + access_token.length(), inboundUri.indexOf(user_id));
        }
        return token;
    }


    public String getUserName() {
        String inputLine = "";
        try {

            URL obj = new URL("https://api.fitbit.com/1/user/-/profile.json");

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + getToken());


            System.out.println("Response Code : " + con.getResponseCode());
            System.out.println("Response Message : " + con.getResponseMessage());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            Log.d("response", response.toString());

            System.out.println(response.toString());
            inputLine = response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return inputLine;
    }

    public String getWeightLog(){
        return makeGetCall("https://api.fitbit.com/1/user/-/body/log/weight/date/2018-03-11/1m.json");
    }

    public String getActivities(){
        return makeGetCall("https://api.fitbit.com/1/user/-/activities/calories/date/2018-03-11/1m.json");
    }

    public String getHeartRates(){
        return makeGetCall("https://api.fitbit.com/1/user/-/activities/heart/date/2018-03-11/1m.json");
    }

    public String makePlainHttpCall(String uri){
        String inputLine = "";
        try {
            URL obj = new URL(uri);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
//            con.setRequestProperty("Authorization", "Bearer " + getToken());

            System.out.println("Response Code : " + con.getResponseCode());
            System.out.println("Response Message : " + con.getResponseMessage());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            Log.d("response", response.toString());

            System.out.println(response.toString());
            inputLine = response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return inputLine;
    }

    public String makeGetCall(String uri){
        String inputLine = "";
        try {
            URL obj = new URL(uri);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + getToken());

            System.out.println("Response Code : " + con.getResponseCode());
            System.out.println("Response Message : " + con.getResponseMessage());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String line;
            StringBuffer response = new StringBuffer();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();

            Log.d("response", response.toString());

            System.out.println(response.toString());
            inputLine = response.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return inputLine;
    }

//
//    @Override
//    protected String doInBackground(String... params) {
//        Log.d("longop", "fei");
//
//
//        String
//
//        try {
//            String data = makePlainHttpCall("http://jhprohealth.herokuapp.com/polls/submit_survey/2/123123123123123123123123123123123555");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Thread.interrupted();
//        }
//        return "Executed";
//    }

}
